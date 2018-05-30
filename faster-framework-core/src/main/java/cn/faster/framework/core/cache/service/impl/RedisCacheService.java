package cn.faster.framework.core.cache.service.impl;

import cn.faster.framework.core.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Autor: zhangbowen
 * @Time: 19:12
 * @Description:
 */
public class RedisCacheService implements ICacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value, long exp) {
        redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
