package cn.faster.framework.core.cache.context;


import cn.faster.framework.core.cache.entity.CacheEntity;
import cn.faster.framework.core.cache.service.ICacheService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

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
    public static void set(String key, Object value, long exp) {
        CacheEntity cacheBean = new CacheEntity();
        cacheBean.setDefaultType(value.getClass());
        cacheBean.setJsonValue(JSON.toJSONString(value));
        cacheService.set(key, JSON.toJSONString(cacheBean), exp);
    }

    /**
     * 删除缓存数据
     *
     * @param key
     */
    public static void delete(String key) {
        cacheService.delete(key);
    }

    /**
     * 从缓存获得对象
     *
     * @param key
     * @return
     */
    private static CacheEntity getByCache(String key) {
        Object value = cacheService.get(key);
        if (value == null) {
            return null;
        }
        return JSON.parseObject(value.toString(), CacheEntity.class);
    }


    /**
     * 获取缓存对象,解析为默认的class对象
     *
     * @param key
     * @return
     */
    public static <T> T getObject(String key) {
        CacheEntity cacheBean = getByCache(key);
        if (cacheBean == null) {
            return null;
        }
        return (T) JSON.parseObject(cacheBean.getJsonValue(), cacheBean.getDefaultType());
    }

    /**
     * 获取缓存对象,解析为默认的class对象
     *
     * @param key
     * @return
     */
    public static <T> T getObject(String key, TypeReference<T> typeReference) {
        CacheEntity cacheBean = getByCache(key);
        if (cacheBean == null) {
            return null;
        }
        return JSON.parseObject(cacheBean.getJsonValue(), typeReference);
    }

    /**
     * 获取缓存数据,如果关键字不存在返回null
     *
     * @param key
     * @return
     */
    public static <T> List<T> getList(String key, Class type) {
        CacheEntity cacheBean = getByCache(key);
        if (cacheBean == null) {
            return null;
        }
        return JSON.parseArray(cacheBean.getJsonValue(), type);
    }

    public static CacheFacade initCache(ICacheService cacheService, boolean local) {
        CacheFacade.cacheService = cacheService;
        CacheFacade.local = local;
        return new CacheFacade();
    }
}
