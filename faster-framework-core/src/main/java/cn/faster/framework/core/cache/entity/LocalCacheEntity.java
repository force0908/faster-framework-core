package cn.faster.framework.core.cache.entity;

import lombok.Data;

/**
 * Created by zhangbowen on 2016/12/5.
 */
@Data
public class LocalCacheEntity<V> {
    //对象
    private V value;
    //缓存时间
    private long exp;
    //存入时间
    private long saveTime;
}
