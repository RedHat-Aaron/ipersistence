package com.athena.config;

import com.athena.io.Resources;
import com.athena.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Author: xiaoxiang.zhang
 * @Description:配置文件解析器
 * @Date: Create in 5:12 PM 2020/2/23
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {
        //1.解析配置文件
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(in);
        Element rootElement = read.getRootElement();
        //使用jaxen包来搜索配置文件中的所有property
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element ele : list) {
            String name = ele.attributeValue("name");
            String value = ele.attributeValue("value");
            properties.setProperty(name, value);
        }
        //2.创建数据源
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(dataSource);
        //3.解析mapper.xml
        //3.1先拿到配置信息的路径
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        mapperList.forEach((ele)->{
            InputStream resource = Resources.getResourceAsStream(ele.attributeValue("resource"));
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            try {
                xmlMapperBuilder.parse(resource);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        return configuration;
    }
}
