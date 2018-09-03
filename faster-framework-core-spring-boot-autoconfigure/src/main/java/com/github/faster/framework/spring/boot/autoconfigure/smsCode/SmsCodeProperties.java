package com.github.faster.framework.spring.boot.autoconfigure.smsCode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen
 * @since 2018/8/27
 */
@Data
@ConfigurationProperties(prefix = "faster.sms.code")
public class SmsCodeProperties {
    //是否为调试环境
    private boolean debug;
    //超时时间，默认15分钟
    private long expire = 60 * 15;
    //是否开启
    private boolean enabled = true;
}
