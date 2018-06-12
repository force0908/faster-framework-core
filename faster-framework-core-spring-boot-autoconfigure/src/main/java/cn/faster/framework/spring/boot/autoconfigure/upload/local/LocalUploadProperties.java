package cn.faster.framework.spring.boot.autoconfigure.upload.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangbowen 2018/6/12 14:30
 */
@ConfigurationProperties(prefix = "faster.upload.local")
@Data
public class LocalUploadProperties {
    private String fileDir;
    private String urlPrefix;
}
