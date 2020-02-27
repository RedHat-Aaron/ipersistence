package com.athena.pojo;

import com.athena.proxy.MapperMethod;

/**
 * @Author: xiaoxiang.zhang
 * @Description:sqlMapper的映射对象
 * @Date: Create in 4:55 PM 2020/2/23
 */
public class MappedStatement {

    private String id;

    private String resultType;

    private String parameterType;

    private String sql;

    private MapperMethod mapperMethod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public MapperMethod getMapperMethod() {
        return mapperMethod;
    }

    public void setMapperMethod(MapperMethod mapperMethod) {
        this.mapperMethod = mapperMethod;
    }
}
