package cn.faster.framework.core.cache.context;


import cn.faster.framework.core.cache.service.ICacheService;

import java.util.Collection;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Autor: zhangbowen
 * @Time: 18:56
 * @Description:
 */
@SuppressWarnings("unchecked")
public class CacheFacade {
    private static ICacheService cacheService;
    public static boolean local = true;

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param exp   失效时间(秒)
     */
    public static <V> void set(String key, V value, long exp) {
        cacheService.set(key, value, exp);
    }

    /**
     * 删除缓存数据
     *
     * @param key
     */
    public static <V> V delete(String key) {
        return (V) cacheService.delete(key);
    }

    /**
     * 获取缓存对象,解析为默认的class对象
     *
     * @param key
     * @return
     */
    public static <V> V get(String key) {
        return (V) cacheService.get(key);
    }

    public static CacheFacade initCache(ICacheService cacheService, boolean local) {
        CacheFacade.cacheService = cacheService;
        CacheFacade.local = local;
        return new CacheFacade();
    }

    /**
     * 清空以cachePrefix开头的缓存
     */
    public static void clear(String cachePrefix) {
        cacheService.clear(cachePrefix);
    }

    /**
     * 获取以cachePrefix开头的缓存数量
     *
     * @param cachePrefix
     */
    public static int size(String cachePrefix) {
        return cacheService.size(cachePrefix);
    }

    /**
     * 获取以cachePrefix开头的缓存键列表
     * @param cachePrefix
     * @param <K>
     * @return
     */
    public static <K> Set<K> keys(String cachePrefix) {
        return cacheService.keys(cachePrefix);
    }

    /**
     * 获取以cachePrefix开头的缓存值
     * @param cachePrefix
     * @param <V>
     * @return
     */
    public static <V> Collection<V> values(String cachePrefix) {
        return cacheService.values(cachePrefix);
    }
}
