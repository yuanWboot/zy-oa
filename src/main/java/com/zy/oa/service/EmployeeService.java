package com.zy.oa.service;


import com.zy.oa.entity.Employee;
import com.zy.oa.mapper.EmployeeMapper;
import com.zy.oa.utils.MyBatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    public Employee selectById(Long employeeId){
        Employee employee = (Employee)MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            return mapper.selectById(employeeId);
        });
        return employee;
    }
    public Employee selectLeader(Long employeeId){
        Employee l  = (Employee)MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectById(employeeId);
            Map params = new HashMap<>();
            Employee leader = null;
            if (employee.getLevel()<7){
                //查询部门经理
                params.put("level",7);
                params.put("department",employee.getDepartmentId());
                List<Employee> employees = employeeMapper.selectByParams(params);
                leader = employees.get(0);
            }else if(employee.getLevel() == 7){
                //查询总经理
                params.put("level",8);
                List<Employee> employees = employeeMapper.selectByParams(params);
                leader = employees.get(0);
            }else if (employee.getLevel() == 8){
                //返回自己
                leader = employee;
            }
            return leader;
        });
        return l;
    }
}
