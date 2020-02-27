package com.athena.sqlsession;

import com.athena.pojo.Configuration;

import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:会话对象
 * @Date: Create in 5:56 PM 2020/2/23
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId, Object... args) throws Exception;

    <T> T selectOne(String statementId, Object... args) throws Exception;

    Integer insert(String statementId, Object... args) throws Exception;

    Integer delete(String statementId, Object... args) throws Exception;

    Integer update(String statementId, Object... args) throws Exception;

    <T> T getMapper(Class<?> mapperClass);

    Configuration getConfiguration();
}
