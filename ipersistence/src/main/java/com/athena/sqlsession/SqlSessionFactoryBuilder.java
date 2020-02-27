package com.athena.sqlsession;

import com.athena.config.XMLConfigBuilder;
import com.athena.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @Author: xiaoxiang.zhang
 * @Description:SqlSessoin工厂创建类
 * @Date: Create in 5:09 PM 2020/2/23
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws PropertyVetoException, DocumentException {
        //1.读取配置信息生成Configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //2.创建SqlSessionFactory对象：生产SqlSession会话对象
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }

}
