package com.cyh.cache;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Chenyuhua
 * @date 2020/2/26 18:03
 */
@Data
public class PerpetualCache implements Cache {
    /**
     * 标识
     */
    private final String id;
    /**
     * 缓存容器
     */
    private Map<Object, Object> cache = new HashMap<>();

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
