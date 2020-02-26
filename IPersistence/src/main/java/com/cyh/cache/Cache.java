package com.cyh.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Chenyuhua
 * @date 2020/2/26 18:05
 */
public interface Cache {

    /**
     * @return The identifier of this cache 标识
     */
    String getId();


    void putObject(Object key, Object value);


    Object getObject(Object key);


    Object removeObject(Object key);


    void clear();


    int getSize();

    @Deprecated
    ReadWriteLock getReadWriteLock();

}
