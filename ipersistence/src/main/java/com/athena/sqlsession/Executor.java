package com.athena.sqlsession;

import com.athena.pojo.Configuration;
import com.athena.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 6:45 PM 2020/2/23
 */
public interface Executor {

   <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception;

   Integer insert(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception;

   Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception;

   Integer update(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception;
}
