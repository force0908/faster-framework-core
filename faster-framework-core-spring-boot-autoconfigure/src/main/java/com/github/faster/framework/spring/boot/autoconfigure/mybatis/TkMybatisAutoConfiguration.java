package com.github.faster.framework.spring.boot.autoconfigure.mybatis;

import com.github.faster.framework.core.mybatis.mapper.BaseMapper;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhangbowen
 */
@MapperScan(markerInterface = BaseMapper.class, basePackages = "**.mapper")
@Configuration
public class TkMybatisAutoConfiguration {
}
