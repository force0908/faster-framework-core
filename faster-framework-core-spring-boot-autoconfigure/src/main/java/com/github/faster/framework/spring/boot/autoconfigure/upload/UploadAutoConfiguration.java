package com.github.faster.framework.spring.boot.autoconfigure.upload;

import com.github.faster.framework.core.upload.service.IUploadService;
import com.github.faster.framework.core.upload.service.local.LocalUploadService;
import com.github.faster.framework.spring.boot.autoconfigure.ProjectProperties;
import com.github.faster.framework.spring.boot.autoconfigure.upload.local.LocalUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbowen 2018/6/12 14:30
 */
@Configuration
@ConditionalOnProperty(prefix = "faster.upload", name = "enabled", havingValue = "true", matchIfMissing = true)
public class UploadAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = "faster.upload", name = "mode", havingValue = "local", matchIfMissing = true)
    @EnableConfigurationProperties({ProjectProperties.class, LocalUploadProperties.class, UploadProperties.class})
    public static class AppAuthConfiguration {
        @Autowired
        private ProjectProperties projectProperties;
        @Autowired
        private LocalUploadProperties localUploadProperties;
        @Autowired
        private UploadProperties uploadProperties;

        @Bean
        public IUploadService localUpload() {
            return new LocalUploadService(uploadProperties.getMode(), localUploadProperties.getFileDir(), localUploadProperties.getUrlPrefix(), projectProperties.getBase64Secret());
        }
    }
}
