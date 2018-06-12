package com.github.faster.framework.spring.boot.autoconfigure.cache;

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
    private boolean enabled = true;
    /**
     * 是否为本地模式
     */
    private boolean local = true;
}
