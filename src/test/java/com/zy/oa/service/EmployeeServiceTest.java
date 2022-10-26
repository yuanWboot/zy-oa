package com.zy.oa.service;

import com.zy.oa.entity.Employee;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeServiceTest {

    @Test
    public void selectLeader() {
        Employee employee = null;
       EmployeeService employeeService = new EmployeeService();
        employee = employeeService.selectLeader(8l);
        System.out.println(employee);

    }
}