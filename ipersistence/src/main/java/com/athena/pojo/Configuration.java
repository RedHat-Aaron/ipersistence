package com.athena.pojo;

import com.athena.proxy.MapperProxy;
import com.athena.sqlsession.SqlSession;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaoxiang.zhang
 * @Description:配置对象信息
 * @Date: Create in 4:56 PM 2020/2/23
 */
public class Configuration {

    private DataSource dataSource;

    //这里的String 用来存储 statementId:nameSpace+id

    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public <T> T getMapper(Class<?> mapperClass, SqlSession sqlSession) {
        MapperProxy mapperProxy = new MapperProxy(mapperClass, sqlSession);
        return (T) mapperProxy.newInstance();
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
