package com.athena.proxy;

import com.athena.enums.TypeEnum;
import com.athena.pojo.Configuration;
import com.athena.sqlsession.SqlSession;

import java.lang.reflect.*;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:方法的代理对象
 * @Date: Create in 9:50 AM 2020/2/27
 */
public class MapperProxy implements InvocationHandler {

    //代理对象
    private Class<?> target;

    private SqlSession sqlSession;

    private Configuration configuration;

    public MapperProxy(Class<?> target, SqlSession sqlSession) {
        this.target = target;
        this.sqlSession = sqlSession;
        this.configuration = sqlSession.getConfiguration();
    }

    public Object newInstance() {
        return Proxy.newProxyInstance(MapperProxy.class.getClassLoader(), new Class[]{target}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //通过当前传入的类名以及要执行的方法名来拼接statementId
        String methodName = method.getName();
        //获得传入参数类的全限定名
        String className = method.getDeclaringClass().getName();
        String statementId = className + "." + methodName;
        //根据statementId来获取对应的mapper对象
        TypeEnum methodEnum = configuration.getMappedStatementMap().get(statementId).getMapperMethod().getMethodEnum();
        if (TypeEnum.INSERT.getValue().equals(methodEnum.getValue())) {
            return sqlSession.insert(statementId, args);
        } else if (TypeEnum.DELETE.getValue().equals(methodEnum.getValue())) {
            return sqlSession.delete(statementId, args);
        } else if (TypeEnum.UPDATE.getValue().equals(methodEnum.getValue())) {
            return sqlSession.update(statementId, args);
        } else if (TypeEnum.SELECT.getValue().equals(methodEnum.getValue())) {
            //根据返回值类型来判断当前需要执行的方法
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                //如果返回结果为泛型类，那么就说明需要调用查询所有的方法
                List<Object> objects = sqlSession.selectList(statementId, args);
                return objects;
            }
            return sqlSession.selectOne(statementId, args);
        } else {
            throw new RuntimeException("SQL标签不匹配，请检查" + className + ".xml是否配置正确");
        }
    }
}
