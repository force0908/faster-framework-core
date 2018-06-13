package com.github.faster.framework.spring.boot.autoconfigure.upload.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen
 */
@ConfigurationProperties(prefix = "faster.upload.local")
@Data
public class LocalUploadProperties {
    private String fileDir;
    private String urlPrefix;
}
