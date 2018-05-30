package cn.faster.framework.spring.boot.autoconfigure.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen
 */
@ConfigurationProperties(prefix = "faster.auth")
@Data
public class AuthProperties {
    /**
     * 是否开启
     */
    private boolean enabled = true;
    /**
     * 模式，默认为app模式
     */
    private String mode = "app";

    /**
     * token是否支持多终端，同时受缓存影响
     */
    private boolean multipartTerminal = false;

    private JwtProperties jwt = new JwtProperties();

    @Data
    public static class JwtProperties {
        /**
         * base64秘钥
         */
        private String base64Secret = "ZmFzdGVyLWZyYW1ld29yaw==";

    }
}
