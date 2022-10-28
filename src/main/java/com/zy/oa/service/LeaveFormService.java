package com.zy.oa.service;

import com.zy.oa.entity.Employee;
import com.zy.oa.entity.LeaveForm;
import com.zy.oa.entity.ProcessFlow;
import com.zy.oa.mapper.EmployeeMapper;
import com.zy.oa.mapper.LeaveFormMapper;
import com.zy.oa.mapper.ProcessFlowMapper;
import com.zy.oa.service.exception.LeaveFormException;
import com.zy.oa.utils.MyBatisUtils;

import java.nio.channels.FileLockInterruptionException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaveFormService {

    private EmployeeService employeeService = new EmployeeService();
    /**
     * 创建请假单
     *
     * @param form 从前台传入的请假单数据
     * @return 持久化后的请假单数据
     */
    public LeaveForm createLeaveForm(LeaveForm form) {
        LeaveForm f= (LeaveForm) MyBatisUtils.executeUpdate(sqlSession ->{
//        1.持久化form表单数据，8级以下提交表单状态为processing，8级-总经理状态为approved
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectById(form.getEmployeeId());
            if (employee.getLevel() == 8){
                form.setState("approved");
            }else {
                form.setState("processing");
            }
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            leaveFormMapper.insert(form);
//        2.增加第一条流程数据，说明表单已经提交，状态为complete
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow flow1 = new ProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0);
            processFlowMapper.insert(flow1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");
//        3.分情况创建其余数据流程
//        3.1 7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需要生成总经理审批任务
            if (employee.getLevel() < 7 ){
                Employee dmanager = employeeService.selectLeader(employee.getEmployeeId());
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(dmanager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                long diff = form.getEndTime().getTime() - form.getStartTime().getTime();
                float hours = diff/(1000 * 60 * 60) * 1f;
                if (hours >= 72){
                    flow2.setIsLast(0);
                    processFlowMapper.insert(flow2);
                    Employee manager = employeeService.selectLeader(dmanager.getEmployeeId());
                    ProcessFlow flow3 = new ProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(manager.getEmployeeId());
                    flow3.setAction("audit");
                    flow3.setCreateTime(new Date());
                    flow3.setOrderNo(3);
                    flow3.setState("ready");
                    flow3.setIsLast(1);
                    processFlowMapper.insert(flow3);
                }else {
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);

                }


            }else if (employee.getLevel() == 7){
                //        3.2 7级员工，仅生成总经理审批任务
                Employee manager = employeeService.selectLeader(employee.getEmployeeId());
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(manager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                flow2.setIsLast(1);
                processFlowMapper.insert(flow2);
            }else if(employee.getLevel() == 8){
                //        3.3 8级员工，生成总经理审批任务，系统自动通过
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(employee.getEmployeeId());
                flow2.setAction("audit");
                flow2.setResult("approved");
                flow2.setReason("自动通过");
                flow2.setCreateTime(new Date());
                flow2.setAuditTime(new Date());
                flow2.setState("complete");
                flow2.setOrderNo(2);
                flow2.setIsLast(1);
                processFlowMapper.insert(flow2);
            }
            return form;
        });
        return f;
    }

    /**
     * 获取指定任务状态及指定人的请假单列表
     * @param pfState ProcessFlow任务状态
     * @param operatorId 经办人编号
     * @return 请假单及相关参数列表
     */
    public List<Map> getLeaveFormList(String pfState ,Long operatorId){
        return (List<Map>) MyBatisUtils.executeQuery(sqlSession -> {
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            List<Map> maps = leaveFormMapper.selectByParams(pfState, operatorId);
            return maps;
        });
    }

    public void audit(Long formId,Long operatorId,String result,String reason){
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> flowList = processFlowMapper.selectByFormId(formId);
            if (flowList.size() == 0){
                throw  new LeaveFormException("无效的审批流程");

            }
            //获取当前任务Process Flow对象
            List<ProcessFlow> processList = flowList.stream().filter(p -> p.getOperatorId() == operatorId && p.getState().equals("process")).collect(Collectors.toList());
            ProcessFlow process = null;
            if(processList.size() == 0){
                throw new LeaveFormException("未找到待处理任务节点");
            }else{
                process = processList.get(0);
                process.setState("complete");
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowMapper.update(process);
            }
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            LeaveForm form = leaveFormMapper.selectById(formId);
            //如果当前任务是最后一个节点，代表流程结束，更新请假单状态为对应的approved/refused
            if (process.getIsLast() == 1){
                form.setState(result); //approved refused
                leaveFormMapper.update(form);
            }else{
                ////readyList包含所有后续任务节点
                List<ProcessFlow> readyList = flowList.stream().filter(p -> p.getState().equals("ready")).collect(Collectors.toList());
                //如果当前任务不是最后一个节点且审批通过,那下一个节点的状态从ready变为process
                if(result.equals("approved")){
                    ProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowMapper.update(readyProcess);
                }else if(result.equals("refused")){
                    //如果当前任务不是最后一个节点且审批驳回,则后续所有任务状态变为cancel,请假单状态变为refused
                    for(ProcessFlow p:readyList){
                        p.setState("cancel");
                        processFlowMapper.update(p);
                    }
                    form.setState("refused");
                    leaveFormMapper.update(form);
                }
            }
            return null;
        });
    }
}
