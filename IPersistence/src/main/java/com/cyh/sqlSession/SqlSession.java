package com.cyh.sqlSession;

import java.util.List;
import java.util.Objects;

/**
 * @author Chenyuhua
 * @date 2020/2/22 19:28
 */
public interface SqlSession {

    /**
     * 查询多个
     * @param statementId
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> Object selectList(String statementId, Object... params) throws Exception;

    /**
     * 查询单个
     * @param statementId
     * @param params
     * @param <T>
     * @return
     * @throws Exception
     */
    public  <T> T selectOne(String statementId, Object... params) throws Exception;

    /**
     * 插入
     * @param statementId
     * @param params
     * @return
     * @throws Exception
     */
    int insert(String statementId, Object... params) throws Exception;

    /**
     * 更新
     * @param statementId
     * @param params
     * @return
     * @throws Exception
     */
    int update(String statementId, Object... params) throws Exception;

    /**
     * 删除
     * @param statementId
     * @param params
     * @return
     * @throws Exception
     */
    int delete(String statementId, Object... params) throws Exception;

    /**
     * 通过反射获取Mapper
     * @param mapperClass
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<?> mapperClass);
}
