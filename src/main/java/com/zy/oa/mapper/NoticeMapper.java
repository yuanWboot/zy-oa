package com.zy.oa.mapper;


import com.zy.oa.entity.Notice;

import java.util.List;

public interface NoticeMapper {
    public void insert(Notice notice);
    public List<Notice> selectByReviverId(Long receiverId);
}
