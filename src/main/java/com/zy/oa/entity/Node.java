package com.zy.oa.entity;

public class Node {
    private Long nodeId;  //节点编号
    private Integer nodeType;  //节点类型 1-模块 2-功能
    private String nodeName ; //节点名称
    private String url ;      //功能地址
    private Integer nodeCode; //节点编码，用于排序
    private Long parentId;   //上节点编号

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeTyp) {
        this.nodeType = nodeTyp;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(Integer nodeCode) {
        this.nodeCode = nodeCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


}
