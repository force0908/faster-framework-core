package com.github.faster.framework.spring.boot.autoconfigure.upload;

import com.github.faster.framework.core.upload.service.IUploadService;
import com.github.faster.framework.core.upload.service.local.LocalUploadService;
import com.github.faster.framework.spring.boot.autoconfigure.ProjectProperties;
import com.github.faster.framework.spring.boot.autoconfigure.upload.local.LocalUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbowen
 */
@Configuration
@ConditionalOnProperty(prefix = "faster.upload", name = "enabled", havingValue = "true", matchIfMissing = true)
public class UploadAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = "faster.upload", name = "mode", havingValue = "local")
    @EnableConfigurationProperties({ProjectProperties.class, LocalUploadProperties.class})
    @ConditionalOnMissingBean(IUploadService.class)
    public static class LocalUploadConfiguration {
        @Autowired
        private ProjectProperties projectProperties;
        @Autowired
        private LocalUploadProperties localUploadProperties;

        @Bean
        public IUploadService localUpload() {
            return new LocalUploadService(localUploadProperties.getFileDir(), localUploadProperties.getUrlPrefix(), projectProperties.getBase64Secret());
        }
    }
}
