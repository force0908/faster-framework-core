package com.github.faster.framework.spring.boot.autoconfigure.web;

import com.github.faster.framework.spring.boot.autoconfigure.auth.AuthAutoConfiguration;
import com.github.faster.framework.core.web.config.WebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangbowen
 */
@Configuration
@Import({WebConfiguration.class, AuthAutoConfiguration.class})
public class WebAutoConfiguration {

}
