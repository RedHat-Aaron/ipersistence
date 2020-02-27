package com.athena.test;

import com.athena.dao.IUserDao;
import com.athena.io.Resources;
import com.athena.pojo.User;
import com.athena.sqlsession.SqlSession;
import com.athena.sqlsession.SqlSessionFactory;
import com.athena.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:持久层框架测试类
 * @Date: Create in 4:41 PM 2020/2/23
 */
public class IPersistenceTest {

    @Test
    public void testSelect() throws Exception {
        InputStream resourcesAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourcesAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User param = new User();
        param.setId(1);
        param.setUsername("小红");

        /*User user = sqlSession.selectOne("user.selectOne", param);
        System.out.println(user);*/

        /*List<User> users = sqlSession.selectList("user.selectList");
        System.out.println(users);*/

        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        /*List<User> allUser = iUserDao.findAll();
        System.out.println(allUser);*/

        User allUser = iUserDao.findByCondition(param);
        System.out.println(allUser);
    }

    @Test
    public void testInsert() throws Exception {
        InputStream resourcesAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourcesAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User param = new User();
        param.setId(4);
        param.setUsername("王大锤");


        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        Integer res = iUserDao.insertUser(param);
        System.out.println(res);
    }

    @Test
    public void testUpdate() throws Exception {
        InputStream resourcesAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourcesAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User param = new User();
        param.setId(4);
        param.setUsername("王铁锤");


        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        Integer res = iUserDao.updateUser(param);
        System.out.println(res);
    }

    @Test
    public void testDelete() throws Exception {
        InputStream resourcesAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourcesAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User param = new User();
        param.setId(4);


        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        Integer res = iUserDao.deleteUser(param);
        System.out.println(res);
    }
}
