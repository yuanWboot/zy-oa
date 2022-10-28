package com.zy.oa.mapper;

import com.zy.oa.entity.LeaveForm;
import com.zy.oa.utils.MyBatisUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LeaveFormMapperTest {

    @Test
    public void insert() {
        MyBatisUtils.executeUpdate(sqlSession -> {
          LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
          LeaveForm form = new LeaveForm();
          form.setEmployeeId(4l); //员工编号
          form.setFormType(1); //事假
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null; //开始时间
            Date endTime = null; //结束时间
            try {
                startTime = sdf.parse("2021-10-01 08:02:00");
                endTime = sdf.parse("2021-10-11 18:02:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            form.setStartTime(startTime);
            form.setEndTime(endTime);
            form.setReason("回家收租");
            form.setCreateTime(new Date());
            form.setState("processing");
            mapper.insert(form);
            return null;
        });
    }

    @Test
    public void selectByParams() {
        MyBatisUtils.executeQuery(sqlSession -> {
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            List<Map> list = leaveFormMapper.selectByParams("process", 2l);
            System.out.println(list);
            return list;
        });
    }
}