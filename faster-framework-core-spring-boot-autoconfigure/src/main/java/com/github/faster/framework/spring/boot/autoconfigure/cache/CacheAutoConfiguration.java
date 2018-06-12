package com.github.faster.framework.spring.boot.autoconfigure.cache;

import com.github.faster.framework.core.cache.context.CacheFacade;
import com.github.faster.framework.core.cache.service.ICacheService;
import com.github.faster.framework.core.cache.service.impl.LocalCacheService;
import com.github.faster.framework.core.cache.service.impl.RedisCacheService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangbowen on 2017/5/5.
 */
@Configuration
@ConditionalOnProperty(prefix = "faster.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({CacheProperties.class})
public class CacheAutoConfiguration {

    @Bean("cacheService")
    @ConditionalOnMissingBean(ICacheService.class)
    @ConditionalOnProperty(prefix = "faster.cache", name = "local", havingValue = "true", matchIfMissing = true)
    public ICacheService localCacheService() {
        return new LocalCacheService();
    }

    @Bean("cacheService")
    @ConditionalOnMissingBean(ICacheService.class)
    @ConditionalOnProperty(prefix = "faster.cache", name = "local", havingValue = "false")
    public ICacheService redisCache() {
        return new RedisCacheService();
    }


    @Bean
    public CacheFacade initCache(@Qualifier("cacheService") ICacheService cacheService, CacheProperties cacheProperties) {
        return CacheFacade.initCache(cacheService, cacheProperties.isLocal());
    }
}
