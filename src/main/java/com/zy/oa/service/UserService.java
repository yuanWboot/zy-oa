package com.zy.oa.service;

import com.zy.oa.entity.User;
import com.zy.oa.mapper.UserMapper;

import javax.security.auth.login.LoginException;


public class UserService {
    private UserMapper userMapper = new UserMapper();

    /**
     * 根据前台输入进行验证
     * @param username 前台传入的用户名
     * @param password 密码
     * @return校验通过后，包含对应用户数据的User实体类
     * @throws LoginException 用户登录异常
     */
    public User checkLogin(String username,String password) throws LoginException {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new LoginException("用户名不存在");
        }if ( !password.equals(user.getPassword())){
            throw new LoginException("密码错误");
        }
            return user;
    }
}
