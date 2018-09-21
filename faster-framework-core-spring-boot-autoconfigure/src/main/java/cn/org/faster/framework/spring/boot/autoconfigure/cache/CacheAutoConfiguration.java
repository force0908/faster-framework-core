package cn.org.faster.framework.spring.boot.autoconfigure.cache;

import cn.org.faster.framework.core.cache.context.CacheFacade;
import cn.org.faster.framework.core.cache.service.ICacheService;
import cn.org.faster.framework.core.cache.service.impl.LocalCacheService;
import cn.org.faster.framework.core.cache.service.impl.RedisCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbowen
 */
@Configuration
@ConditionalOnProperty(prefix = "faster.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({CacheProperties.class})
public class CacheAutoConfiguration {
    /**
     *
     * @return redis缓存
     */
    @ConditionalOnMissingBean(ICacheService.class)
    @ConditionalOnProperty(prefix = "faster.cache.redis", name = "enabled", havingValue = "true")
    @Bean
    public ICacheService redisCache() {
        ICacheService cacheService = new RedisCacheService();
        initCache(cacheService, false);
        return cacheService;
    }

    /**
     *
     * @return 本地缓存
     */
    @ConditionalOnMissingBean(ICacheService.class)
    @Bean
    public ICacheService localCacheService() {
        ICacheService cacheService = new LocalCacheService();
        initCache(cacheService, true);
        return cacheService;
    }

    private void initCache(ICacheService cacheService, boolean isLocal) {
        CacheFacade.initCache(cacheService, isLocal);
    }
}
