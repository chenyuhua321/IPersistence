package com.cyh.executor;

import com.cyh.cache.PerpetualCache;
import com.cyh.pojo.Configuration;
import com.cyh.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/23 1:21
 */
public interface Executor {


    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    // 更新 or 插入 or 删除，由传入的 MappedStatement 的 SQL 所决定
    int update(Configuration configuration,MappedStatement ms, Object... params) throws Exception;
}
