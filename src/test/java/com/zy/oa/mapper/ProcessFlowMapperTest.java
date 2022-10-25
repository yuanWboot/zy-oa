package com.zy.oa.mapper;

import com.zy.oa.entity.ProcessFlow;
import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ProcessFlowMapperTest {

    @Test
    public void insert() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper mapper =sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow processFlow = new ProcessFlow();
            processFlow.setFormId(3l);
            processFlow.setOperatorId(2l);
            processFlow.setAction("apply");
            processFlow.setResult("approved");
            processFlow.setReason("同意");
            processFlow.setCreateTime(new Date());
            processFlow.setAuditTime(new Date());
            processFlow.setOrderNo(2);
            processFlow.setState("process");
            processFlow.setIsLast(0);
            mapper.insert(processFlow);
            return null;
        });
    }
}