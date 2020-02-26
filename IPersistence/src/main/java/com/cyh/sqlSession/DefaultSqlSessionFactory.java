package com.cyh.sqlSession;

import com.cyh.pojo.Configuration;

/**
 * @author Chenyuhua
 * @date 2020/2/22 19:25
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
