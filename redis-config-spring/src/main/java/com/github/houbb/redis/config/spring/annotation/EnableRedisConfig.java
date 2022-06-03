package com.github.houbb.redis.config.spring.annotation;

import com.github.houbb.redis.config.spring.config.RedisConfigConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用自动 redis 配置
 * @author binbin.hou
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisConfigConfiguration.class)
public @interface EnableRedisConfig {
}
