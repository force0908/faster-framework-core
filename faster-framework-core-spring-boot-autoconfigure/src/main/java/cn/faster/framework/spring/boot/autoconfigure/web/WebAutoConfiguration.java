package cn.faster.framework.spring.boot.autoconfigure.web;

import cn.faster.framework.spring.boot.autoconfigure.auth.AuthAutoConfiguration;
import cn.faster.framework.core.web.config.WebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangbowen
 */
@Configuration
@Import({WebConfiguration.class, AuthAutoConfiguration.class})
public class WebAutoConfiguration {

}
