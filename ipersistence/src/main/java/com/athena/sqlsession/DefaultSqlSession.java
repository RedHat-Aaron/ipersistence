package com.athena.sqlsession;

import com.athena.pojo.Configuration;
import com.athena.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:会话的默认实现
 * @Date: Create in 5:57 PM 2020/2/23
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor();
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... args) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> query = executor.query(configuration, mappedStatement, args);
        return (List<E>) query;
    }

    @Override
    public <T> T selectOne(String statementId, Object... args) throws Exception {
        List<Object> objects = selectList(statementId, args);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或查询结果条数过多！");
        }
    }

    //先完成对应的增删改操作
    @Override
    public Integer insert(String statementId, Object... args) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return executor.insert(configuration, mappedStatement, args);
    }

    @Override
    public Integer delete(String statementId, Object... args) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return executor.delete(configuration, mappedStatement, args);
    }

    @Override
    public Integer update(String statementId, Object... args) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return executor.update(configuration, mappedStatement, args);
    }


    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        return configuration.getMapper(mapperClass,this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

}
