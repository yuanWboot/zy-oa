package com.zy.oa.service;

import com.zy.oa.entity.Employee;
import com.zy.oa.entity.LeaveForm;
import com.zy.oa.entity.ProcessFlow;
import com.zy.oa.mapper.EmployeeMapper;
import com.zy.oa.mapper.LeaveFormMapper;
import com.zy.oa.mapper.ProcessFlowMapper;
import com.zy.oa.utils.MyBatisUtils;

import java.util.Date;

public class LeaveFormService {
    /**
     * 创建请假单
     *
     * @param form 从前台传入的请假单数据
     * @return 持久化后的请假单数据
     */
    public LeaveForm createLeaveForm(LeaveForm form) {
        MyBatisUtils.executeUpdate(sqlSession ->{
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
//        3.分情况创建其余数据流程
//        3.1 7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需要生成总经理审批任务
//        3.2 7级员工，仅生成总经理审批任务
//        3.3 8级员工，生成总经理审批任务，系统自动通过
            return null;
        });
        return null;
    }
}
