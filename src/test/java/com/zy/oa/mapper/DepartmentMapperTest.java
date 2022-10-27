package com.zy.oa.mapper;

import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentMapperTest {

    @Test
    public void selectById() {
        MyBatisUtils.executeQuery(sqlSession ->
                sqlSession.getMapper(DepartmentMapper.class).selectById(2l));
    }
}