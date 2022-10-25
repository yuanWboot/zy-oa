package com.zy.oa.mapper;

import com.zy.oa.entity.Employee;
import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeMapperTest {

    @Test
    public void selectById() {
       Employee emp =  (Employee)MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectById(4l);
            System.out.println(employee);
            return employee;
        });
    }
}