package cn.faster.framework.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen 2018/6/12 14:26
 */
@ConfigurationProperties(prefix = "faster")
@Data
public class ProjectProperties {
    private String base64Secret = "ZmFzdGVyLWZyYW1ld29yaw==";
}
