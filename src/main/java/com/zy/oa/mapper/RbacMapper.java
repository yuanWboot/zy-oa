package com.zy.oa.mapper;

import com.zy.oa.entity.Node;
import com.zy.oa.utils.MyBatisUtils;

import java.util.List;

public class RbacMapper {
    public List<Node> selectNodeByUserId(Long userId){
        List list = (List)MyBatisUtils.executeQuery(sqlSession -> sqlSession.selectList("rbacmapper.selectNodeByUserId",userId));
    return list;
    }
}
