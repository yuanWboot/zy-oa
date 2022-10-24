package com.zy.oa.service;

import com.zy.oa.entity.User;
import org.junit.Test;

import javax.security.auth.login.LoginException;

import static org.junit.Assert.*;

public class UserServiceTest {
     private UserService userService = new UserService();
    @Test
    public void checkLogin1() throws LoginException {
        User user = userService.checkLogin("test", "test");
        System.out.println(user);
    }
    @Test
    public void checkLogin2() throws LoginException {
        User user = userService.checkLogin("test1", "test");
        System.out.println(user);
    }

    @Test
    public void checkLogin3() throws LoginException {
        User user = userService.checkLogin("test", "test1");
        System.out.println(user);
    }
}