package com.athena.config;

import com.athena.enums.TypeEnum;
import com.athena.pojo.Configuration;
import com.athena.pojo.MappedStatement;
import com.athena.proxy.MapperMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:Mapper配置文件解析类
 * @Date: Create in 5:41 PM 2020/2/23
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException {
        //1.获取到根元素
        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        String nameSpace = rootElement.attributeValue("namespace");
        List<Element> select = rootElement.selectNodes("//select");
        List<Element> insert = rootElement.selectNodes("//insert");
        List<Element> update = rootElement.selectNodes("//update");
        List<Element> delete = rootElement.selectNodes("//delete");
        List<Element> allElement = new ArrayList<>();
        allElement.addAll(select);
        allElement.addAll(insert);
        allElement.addAll(update);
        allElement.addAll(delete);
        allElement.forEach((ele) -> {
            String id = ele.attributeValue("id");
            String resultType = ele.attributeValue("resultType");
            String parameterType = ele.attributeValue("parameterType");
            String sql = ele.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            //先用空格截取开头
            String[] s = sql.split(" ");
            //获取第一个字符串转换为小写
            String type = s[0].toLowerCase();
            //开始匹配塞值
            MapperMethod mapperMethod = new MapperMethod();
            if (TypeEnum.SELECT.getValue().equals(type)) {
                mapperMethod.setMethodEnum(TypeEnum.SELECT);
                mappedStatement.setMapperMethod(mapperMethod);
            } else if (TypeEnum.INSERT.getValue().equals(type)) {
                mapperMethod.setMethodEnum(TypeEnum.INSERT);
                mappedStatement.setMapperMethod(mapperMethod);
            } else if (TypeEnum.UPDATE.getValue().equals(type)) {
                mapperMethod.setMethodEnum(TypeEnum.UPDATE);
                mappedStatement.setMapperMethod(mapperMethod);
            } else if (TypeEnum.DELETE.getValue().equals(type)) {
                mapperMethod.setMethodEnum(TypeEnum.DELETE);
                mappedStatement.setMapperMethod(mapperMethod);
            }else{
                throw new RuntimeException("该条SQL语句不符合规范！");
            }
            String key = nameSpace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        });

    }

}
