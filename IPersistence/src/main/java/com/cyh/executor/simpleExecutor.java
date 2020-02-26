package com.cyh.executor;

import com.cyh.cache.PerpetualCache;
import com.cyh.pojo.Configuration;
import com.cyh.pojo.MappedStatement;
import com.cyh.sqlSession.BoundSql;
import com.cyh.utils.GenericTokenParser;
import com.cyh.utils.ParameterMapping;
import com.cyh.utils.ParamterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/23 1:22
 */
public class simpleExecutor implements Executor {
    protected PerpetualCache localCache;

    @Override
    public int update(Configuration configuration, MappedStatement ms, Object... params) throws Exception {
        PreparedStatement preparedStatement = preparedStatementBuild(configuration, ms, params);
        preparedStatement.execute();
        return preparedStatement.getUpdateCount();
    }

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = preparedStatementBuild(configuration, mappedStatement, params);

        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeCLass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();

        //6.封装返回结果集
        while (resultSet.next()) {
            Object o = resultTypeCLass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeCLass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    public PreparedStatement preparedStatementBuild(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //1.注册驱动，获取链接
        Connection connection = configuration.getDataSource().getConnection();

        //2.获取sql语句
        //转换sql语句 转换的过程中，还需要对#{}的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //3.获取预处理对象 preparedStatment
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对#{}的解析工作 1.将#{}使用？代替 2.解析出#{}里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类，配置标记解析器来完成对占位符的解析处理工作
        ParamterMappingTokenHandler paramterMappingTokenHandler = new ParamterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", paramterMappingTokenHandler);

        String parseSql = genericTokenParser.pares(sql);

        List<ParameterMapping> parameterMappings = paramterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
