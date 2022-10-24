package com.zy.oa.service;

import com.zy.oa.entity.Node;
import com.zy.oa.mapper.RbacMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RbacServiceTest {
    private RbacService rbacService = new RbacService();

    @Test
    public void selectNodeByUserId() {
        List<Node> nodes = rbacService.selectNodeByUserId(1l);
        for (Node node : nodes) {
            System.out.println(node.getNodeName());
        }
    }
}