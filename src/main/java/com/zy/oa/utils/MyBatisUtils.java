package com.zy.oa.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class MyBatisUtils {
    //利用static静态属于类,不属于对象,保证全局唯一
    private  static SqlSessionFactory sqlSessionFactory = null;
    //利用静态块在初始化时实例化sqlSessionFactory
    static {
        Reader reader = null;
        try {
            //读取mybatis-config.xml
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            //构建sqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            //初始化错误时,通过抛出异常ExceptionInInitializerError通知调用者
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 执行select查询sql
     * @param func 要执行查询语句的代码块
     * @return 查询结果
     */
  public static Object executeQuery(Function<SqlSession,Object> func){
        SqlSession sqlSession = sqlSessionFactory.openSession();
       try {
           Object obj = func.apply(sqlSession);
           return obj;
       }finally {
           sqlSession.close();
       }
  }
}



