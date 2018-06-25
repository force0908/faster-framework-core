package com.github.faster.framework.spring.boot.autoconfigure.upload.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen
 */
@ConfigurationProperties(prefix = "faster.upload.local")
@Data
public class LocalUploadProperties {
    /**
     * 文件的存储目录
     */
    private String fileDir;
    /**
     * 请求图片时的网址前缀
     */
    private String urlPrefix;
}
