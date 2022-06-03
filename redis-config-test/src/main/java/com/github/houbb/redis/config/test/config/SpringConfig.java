package com.github.houbb.redis.config.test.config;


import com.github.houbb.redis.config.spring.annotation.EnableRedisConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
@Configurable
@ComponentScan(basePackages = "com.github.houbb.redis.config.test.service")
@EnableRedisConfig
public class SpringConfig {
}
