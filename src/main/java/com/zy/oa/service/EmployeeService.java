package com.zy.oa.service;


import com.zy.oa.entity.Employee;
import com.zy.oa.mapper.EmployeeMapper;
import com.zy.oa.utils.MyBatisUtils;

public class EmployeeService {
    public Employee selectById(Long employeeId){
        Employee employee = (Employee)MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            return mapper.selectById(employeeId);
        });
        return employee;
    }
    public Employee selectLeader(Long employeeId){
        MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectById(employeeId);
            if (employee.getLevel()>7){
                //查询部门经理
            }else if(employee.getLevel() == 7){
                //查询总经理
            }else if (employee.getLevel() == 8){
                //返回自己
            }
            return null;
        });
        return null;
    }
}
