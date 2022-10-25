package com.zy.oa.mapper;

import com.zy.oa.entity.Employee;

public interface EmployeeMapper {
    public Employee selectById(Long employeeId);
}
