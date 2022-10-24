package com.zy.oa.mapper;

import com.zy.oa.entity.User;
import com.zy.oa.utils.MyBatisUtils;

public class UserMapper {
    public User selectByUsername(String username){
        User user = (User)MyBatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("usermapper.selectByUsername",username));
        return user;
    }
}
