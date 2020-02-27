package com.athena.enums;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 9:16 AM 2020/2/27
 */
public enum TypeEnum {

    //查询方法
    SELECT("select"),

    //增加方法
    INSERT("insert"),


    //修改方法
    UPDATE("update"),

    //删除方法
    DELETE("delete");


    private String value;

    TypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
