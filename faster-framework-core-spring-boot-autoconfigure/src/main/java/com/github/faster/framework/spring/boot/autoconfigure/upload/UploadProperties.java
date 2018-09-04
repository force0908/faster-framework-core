package com.github.faster.framework.spring.boot.autoconfigure.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author zhangbowen
 */
@Data
@ConfigurationProperties(prefix = "faster.upload")
public class UploadProperties {
    /**
     * 是否开启
     */
    private boolean enabled = true;
    /**
     * 本地模式
     */
    private LocalUploadProperties local = new LocalUploadProperties();


    @ConfigurationProperties(prefix = "faster.upload.local")
    @Data
    public static class LocalUploadProperties {
        /**
         * 是否开启
         */
        private boolean enabled;
        /**
         * 文件的存储目录
         */
        private String fileDir;
        /**
         * 请求图片时的网址前缀
         */
        private String urlPrefix;
    }
}
