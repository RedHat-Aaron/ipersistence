package com.athena.sqlsession;

import com.athena.pojo.Configuration;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 5:55 PM 2020/2/23
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
