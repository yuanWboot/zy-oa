package com.zy.oa.controller;

import com.zy.oa.entity.LeaveForm;
import com.zy.oa.service.LeaveFormService;
import com.zy.oa.utils.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/api/leave/**")
public class LeaveFormServlet extends HttpServlet {
    private LeaveFormService leaveFormService = new LeaveFormService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        //    http://localhost/api/leave/create

        String uri = request.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        if (methodName.equals("create")){
            this.creat(request,response);
        }else if (methodName.equals("list")){

        }else if (methodName.equals("audit")){

        }

    }

    public void creat(HttpServletRequest request,HttpServletResponse response){

        String eid = request.getParameter("eid");
        String formType = request.getParameter("formType");
        //从1970年获取到现在的毫秒数
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(Long.parseLong(eid));
        form.setStartTime(new Date(Long.parseLong(startTime)));
        form.setEndTime(new Date(Long.parseLong(endTime)));
        form.setReason(reason);
        form.setCreateTime(new Date());
        ResponseUtils resp = null;
        try {
            leaveFormService.createLeaveForm(form);
            resp = new ResponseUtils();
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }

    }
}

