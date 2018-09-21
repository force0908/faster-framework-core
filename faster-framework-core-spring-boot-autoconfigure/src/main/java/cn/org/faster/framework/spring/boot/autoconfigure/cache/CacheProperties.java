package cn.org.faster.framework.spring.boot.autoconfigure.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen
 */
@ConfigurationProperties(prefix = "faster.cache")
@Data
public class CacheProperties {
    /**
     * 是否开启缓存
     */
    private boolean enabled;
    /**
     * Redis缓存配置
     */
    private RedisProperties redis = new RedisProperties();

    @ConfigurationProperties(prefix = "faster.cache.redis")
    @Data
    public static class RedisProperties {
        /**
         * 是否开启redis缓存
         */
        private boolean enabled;
    }
}
