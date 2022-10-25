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
}
