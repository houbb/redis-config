package com.github.houbb.redis.config.core.service;

import java.util.concurrent.TimeUnit;

/**
 * redis 服务接口
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IRedisService {

    /**
     * 设置
     * @param key key
     * @param value 值
     * @since 1.0.0
     */
    void set(String key, String value);

    /**
     * 获取对应的值
     * @param key key
     * @return 结果
     */
    String get(String key);

    /**
     * 过期
     * @param key key
     * @param expireTime 过期时间
     * @param timeUnit 时间单位
     * @since 1.0.0
     */
    void expire(String key, long expireTime, TimeUnit timeUnit);

    /**
     * 删除
     * @param key 键
     * @since 1.0.0
     */
    void delete(String key);

}
