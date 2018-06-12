package cn.faster.framework.spring.boot.autoconfigure.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author zhangbowen 2018/6/12 14:24
 */
@Data
@ConfigurationProperties(prefix = "faster.upload")
public class UploadProperties {
    /**
     * 是否开启
     */
    private boolean enabled = true;
    /**
     * 上传模式（local本地）
     */
    private String mode = "local";
}
