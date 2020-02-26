package com.cyh.test.dao;

import com.cyh.test.pojo.User;

import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/23 17:06
 */
public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;

    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;

    //根据条件进行用户查询
    public int insertUser(User user) throws Exception;

    //根据条件进行用户查询
    public int updateUser(User user) throws Exception;

    //根据条件进行用户查询
    public int deleteUser(User user) throws Exception;
}
