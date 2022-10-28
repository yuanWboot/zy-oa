package com.zy.oa.service;

import com.zy.oa.entity.Notice;
import com.zy.oa.mapper.NoticeMapper;
import com.zy.oa.utils.MyBatisUtils;

import java.util.List;

public class NoticeService {
    public List<Notice> getNoticeList(Long recieverId){
        return (List) MyBatisUtils.executeQuery(sqlSession -> {
            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            return noticeMapper.selectByReviverId(recieverId);
        });
    }
}
