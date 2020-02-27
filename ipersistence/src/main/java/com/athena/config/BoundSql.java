package com.athena.config;

import com.athena.utils.ParameterMapping;

import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 7:10 PM 2020/2/23
 */
public class BoundSql {

    private String paserSql;

    private List<ParameterMapping> parameter;

    public BoundSql(String paserSql, List<ParameterMapping> parameter) {
        this.paserSql = paserSql;
        this.parameter = parameter;
    }

    public String getPaserSql() {
        return paserSql;
    }

    public List<ParameterMapping> getParameter() {
        return parameter;
    }
}
