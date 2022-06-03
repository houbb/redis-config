package com.github.houbb.redis.config.springboot.starter.config;

import com.github.houbb.redis.config.spring.annotation.EnableRedisConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * 防止重复提交自动配置
 * @author binbin.hou
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(EnableRedisConfig.class)
@EnableRedisConfig
public class RedisConfigAutoConfig {
}
