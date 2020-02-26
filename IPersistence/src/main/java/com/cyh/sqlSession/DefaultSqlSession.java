package com.cyh.sqlSession;

import com.cyh.executor.simpleExecutor;
import com.cyh.pojo.Configuration;
import com.cyh.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/22 19:30
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        return update(statementId, params);
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExecutor.update(configuration, mappedStatement, params);
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        return update(statementId, params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (1 == objects.size()) {
            return (T) objects.get(0);
        }
        throw new RuntimeException("查询结果为空或者返回结果过多");
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理来为DAO接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //地不执行JDBC代码
                //准备参数 1.statementId namespace.id = 接口全限定名，方法名
                String methdName = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + methdName;
                MappedStatement ms = resolveMappedStatement(statementId);
                Object result;
                //根据sql类型执行对应的方法
                switch (ms.getSqlCommandType()) {
                    case SELECT: {
                        //准备参数2 param args
                        //获取被调用方法的返回值类型
                        Type genericReturnType = method.getGenericReturnType();
                        //判断是否进行了泛型类型参数化
                        if (genericReturnType instanceof ParameterizedType) {
                            result = selectList(statementId, args);
                        }else {
                            result = selectOne(statementId, args);
                        }
                        break;
                    }
                    case INSERT: {
                        result = insert(statementId, args);
                        break;
                    }
                    case UPDATE: {
                        result = update(statementId, args);
                        break;
                    }
                    case DELETE:
                        result = delete(statementId, args);
                        break;
                    default:
                        throw new Exception("Unknown execution method for: " + ms.getSqlCommandType());
                }
                return result;
            }
        });
        return (T) proxyInstance;
    }

    private MappedStatement resolveMappedStatement(String statementId) {

        // 如果有，获得 MappedStatement 对象，并返回
        if (configuration.getMappedStatementMap().containsKey(statementId)) {
            return configuration.getMappedStatementMap().get(statementId);
        }
        return null;
    }
}
