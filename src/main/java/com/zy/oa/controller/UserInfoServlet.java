package com.zy.oa.controller;

import com.zy.oa.entity.Employee;
import com.zy.oa.entity.Node;
import com.zy.oa.service.EmployeeService;
import com.zy.oa.service.RbacService;
import com.zy.oa.utils.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/user_info")
public class UserInfoServlet extends HttpServlet {
    private RbacService rbacService = new RbacService();
    private EmployeeService employeeService = new EmployeeService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //从前端获取到uid
        String uid = request.getParameter("uid");
        String eid = request.getParameter("eid");
        //根据uid查询到所有功能模块
        List<Node> nodes = rbacService.selectNodeByUserId(Long.parseLong(uid));
        //功能模块树形结构
        List<Map> treeList = new ArrayList<>();
        //一个空的来接收模块
        Map module = null;
        for (Node node : nodes) {
            if (node.getNodeType()==1){
                //实例化module
                module = new LinkedHashMap();
                //将type为1的模块（node）放进module
                module.put("node",node);
                //并创建一个children
                module.put("children",new ArrayList());
                //将module添加到treeList
                treeList.add(module);
            }else if (node.getNodeType()==2){
                //从module中的children拿出来放到另一个children中
                List children = (List)module.get("children");
                //将type为2的模块（node）放进children
                children.add(node);
            }
        }

        //转换成json数据结构
        Employee employee = employeeService.selectById(Long.parseLong(eid));
        String json = new ResponseUtils().put("nodeList", treeList).put("employee",employee).toJsonString();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
