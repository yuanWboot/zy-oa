package com.zy.oa.service;

import com.zy.oa.entity.Department;
import com.zy.oa.mapper.DepartmentMapper;
import com.zy.oa.utils.MyBatisUtils;

public class DepartmentService {
    public Department selectById(Long departmemtId){
        return (Department)MyBatisUtils.executeQuery(sqlSession -> sqlSession.getMapper(DepartmentMapper.class).selectById(departmemtId));
    }
}
