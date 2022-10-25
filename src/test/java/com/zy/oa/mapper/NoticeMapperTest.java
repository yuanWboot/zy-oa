package com.zy.oa.mapper;

import com.zy.oa.entity.Notice;
import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NoticeMapperTest {
    @Test
    public void insert() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            mapper.insert(new Notice(3l,"测试内容"));
            return null;
        });
    }
}