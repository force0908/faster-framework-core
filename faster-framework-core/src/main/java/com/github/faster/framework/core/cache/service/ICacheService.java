package com.github.faster.framework.core.cache.service;

import java.util.Collection;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Autor: zhangbowen
 * @Time: 18:56
 * @Description:
 */
public interface ICacheService<V> {
    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param exp   失效时间(秒)
     */
    void set(String key, V value, long exp);

    /**
     * 删除缓存数据
     *
     * @param key
     */
    V delete(String key);

    /**
     * 获取缓存数据,如果关键字不存在返回null
     *
     * @param key
     * @return
     */
    V get(String key);

    /**
     * 清空以cacheService开头的缓存
     * @param cachePrefix
     */
    void clear(String cachePrefix);

    /**
     * 查询以cachePrefix开头的cache数量
     * @param cachePrefix
     */
    int size(String cachePrefix);

    /**
     * 查询以cachePrefix开头的keys
     * @param cachePrefix
     */
    Set<String> keys(String cachePrefix);

    /**
     * 查询以cachePrefix开头的值列表
     * @param cachePrefix
     * @return
     */
    Collection<V> values(String cachePrefix);
}
