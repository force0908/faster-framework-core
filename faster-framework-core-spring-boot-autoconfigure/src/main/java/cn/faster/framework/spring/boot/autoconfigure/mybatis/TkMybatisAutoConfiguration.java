package cn.faster.framework.spring.boot.autoconfigure.mybatis;

import cn.faster.framework.core.mybatis.mapper.BaseMapper;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhangbowen
 */
@MapperScan(markerInterface = BaseMapper.class, basePackages = "**.mapper")
@Configuration
public class TkMybatisAutoConfiguration {
}
