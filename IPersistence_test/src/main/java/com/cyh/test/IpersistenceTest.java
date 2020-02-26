package com.cyh.test;

import com.cyh.io.Resources;
import com.cyh.sqlSession.SqlSession;
import com.cyh.sqlSession.SqlSessionFactory;
import com.cyh.sqlSession.SqlSessionFactoryBuilder;
import com.cyh.test.dao.IUserDao;
import com.cyh.test.pojo.User;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/22 15:55
 */
public class IpersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resouceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resouceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(2L);
        user.setUsername("zhangsan");


        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> all = userDao.findAll();
        for (User u : all){
            System.out.println(u);
        }
    }

    @Test
    public void insertTest() throws Exception {
        InputStream resouceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resouceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(3L);
        user.setUsername("lisi");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        int i = userDao.insertUser(user);
        System.out.println(i);
    }

    @Test
    public void updateTest() throws Exception {
        InputStream resouceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resouceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(3L);
        user.setUsername("aaa");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        int i = userDao.updateUser(user);
        System.out.println(i);
    }

    @Test
    public void deleteTest() throws Exception {
        InputStream resouceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resouceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        int i = userDao.deleteUser(3L);
        System.out.println(i);
    }
}
