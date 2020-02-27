package com.athena.dao;

import com.athena.pojo.User;

import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:User的数据访问层
 * @Date: Create in 6:50 PM 2020/2/25
 */
public interface IUserDao {


    List<User> findAll();


    User findByCondition(User user);

    Integer insertUser(User user);

    Integer deleteUser(User user);

    Integer updateUser(User user);

}
