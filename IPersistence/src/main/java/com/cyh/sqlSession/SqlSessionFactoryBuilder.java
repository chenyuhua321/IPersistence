package com.cyh.sqlSession;

import com.cyh.config.XMLConfigBuilder;
import com.cyh.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author Chenyuhua
 * @date 2020/2/22 17:42
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException {
        //第一: 使用dom4j解析配置文件 将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBUilder = new XMLConfigBuilder();
        Configuration configuration  = xmlConfigBUilder.parseConfig(inputStream);

        //创建SqlSessionFactory对象

        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
