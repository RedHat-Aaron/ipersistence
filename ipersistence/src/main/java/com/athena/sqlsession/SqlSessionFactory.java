package com.athena.sqlsession;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 5:10 PM 2020/2/23
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
