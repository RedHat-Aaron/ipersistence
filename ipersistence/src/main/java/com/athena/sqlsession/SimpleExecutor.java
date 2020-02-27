package com.athena.sqlsession;

import com.athena.config.BoundSql;
import com.athena.pojo.Configuration;
import com.athena.pojo.MappedStatement;
import com.athena.utils.GenericTokenParser;
import com.athena.utils.ParameterMapping;
import com.athena.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 6:45 PM 2020/2/23
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception {
        PreparedStatement ps = getPreparedStatement(configuration, mappedStatement, args[0]);
        //5.执行sql
        ResultSet resultSet = ps.executeQuery();
        //拿到返回结果集的Class类
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        //6.封装并返回结果集
        //拿到结果集元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<Object> returnList = new ArrayList<>();
        while (resultSet.next()) {
            //创建需要封装的对象
            Object o = resultTypeClass.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                //创建一个反射包中的属性解析器，可以用来调用字节码文件的读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, resultSet.getObject(columnName));
            }
            returnList.add(o);

        }
        return (List<E>)returnList;
    }


    @Override
    public Integer insert(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception {
        //这里实现对应的jdbc代码
        //1.获取预编译对象
        PreparedStatement ps = getPreparedStatement(configuration, mappedStatement, args[0]);
        //5.执行sql
        Integer result = ps.executeUpdate();
        return result;
    }

    @Override
    public Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception {
        //这里实现对应的jdbc代码
        //1.获取链接对象
        PreparedStatement ps = getPreparedStatement(configuration, mappedStatement, args[0]);
        //5.执行sql
        Integer result = ps.executeUpdate();
        return result;
    }

    @Override
    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... args) throws Exception {
        //这里实现对应的jdbc代码
        //1.获取链接对象
        PreparedStatement ps = getPreparedStatement(configuration, mappedStatement, args[0]);
        //5.执行sql
        Integer result = ps.executeUpdate();
        return result;
    }

    /**
     * @return void
     * @Author xiangxz
     * @Description 返回对象的字节码文件
     * @Date 7:41 PM 2020/2/23
     * @Param [parameterType]
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (null != parameterType && !"".equals(parameterType)) {
            return Class.forName(parameterType);
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String parseSql = parser.parse(sql);
        List<ParameterMapping> parameterMappings = handler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }

    private PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement, Object arg) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //这里实现对应的jdbc代码
        //1.获取链接对象
        Connection connection = configuration.getDataSource().getConnection();
        //2.处理sql，将sql中的#{}或者${}替换为?,并将其中的参数存储起来用以设置参数
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.准备prepareStatement
        PreparedStatement ps = connection.prepareStatement(boundSql.getPaserSql());
        //4.设置参数
        //获取参数对象全路径
        String parameterType = mappedStatement.getParameterType();
        Class<?> classType = getClassType(parameterType);
        List<ParameterMapping> parameter = boundSql.getParameter();
        for (int i = 0; i < parameter.size(); i++) {
            ParameterMapping parameterMapping = parameter.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = classType.getDeclaredField(content);
            //设置暴力访问
            declaredField.setAccessible(true);
            //这个是获取当前对象上由这个field表示的字段
            Object o = declaredField.get(arg);
            ps.setObject(i + 1, o);
        }
        return ps;
    }
}
