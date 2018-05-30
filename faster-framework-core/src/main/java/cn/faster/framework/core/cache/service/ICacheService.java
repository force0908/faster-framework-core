package cn.faster.framework.core.cache.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Autor: zhangbowen
 * @Time: 18:56
 * @Description:
 */
public interface ICacheService {
    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param exp   失效时间(秒)
     */
    default void set(String key, String value, long exp) {

    }

    /**
     * 删除缓存数据
     *
     * @param key
     */
    default void delete(String key) {

    }

    /**
     * 获取缓存数据,如果关键字不存在返回null
     *
     * @param key
     * @return
     */
    default Object get(String key) {
        return null;
    }

}
