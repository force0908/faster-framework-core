package com.github.faster.framework.spring.boot.autoconfigure.smsCode;

import com.github.faster.framework.core.smsCode.service.ISmsCodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbowen
 */
@Configuration
@ConditionalOnProperty(prefix = "faster.sms.code", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({SmsCodeProperties.class})
public class SmsCodeAutoConfiguration {

    /**
     * debug环境下的短信发送
     *
     * @param smsCodeProperties 短信配置
     * @return ISmsCodeService
     */
    @Bean
    @ConditionalOnMissingBean(ISmsCodeService.class)
    @ConditionalOnProperty(prefix = "faster.sms.code", name = "debug", havingValue = "true", matchIfMissing = true)
    public ISmsCodeService debugSmsCode(SmsCodeProperties smsCodeProperties) {
        return new ISmsCodeService(smsCodeProperties.isDebug(), smsCodeProperties.getExpire()) {
            @Override
            protected boolean send(String phone, String code) {
                return true;
            }
        };
    }
}
