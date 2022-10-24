package com.zy.oa.controller;

import com.zy.oa.entity.User;
import com.zy.oa.service.UserService;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("api/login")
public class LoginServlet extends HttpServlet {
    private UserService userService  = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        //接收用户输入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用业务逻辑
        Map result = new LinkedHashMap<>();
        try {
            User user = userService.checkLogin(username, password);
            //处理结果编码，0代表成功，非零处理代表失败
            result.put("code",0);
            result.put("message","success");
        } catch (LoginException e) {
            e.printStackTrace();
            //处理失败返回异常类名和错误提示信息
            result.put("code",e.getClass().getSimpleName());
            result.put("message",e.getMessage());
        }
        //返回json结果
    }
}
