package com.github.houbb.redis.config.core.factory;

import com.github.houbb.common.cache.api.service.ICommonCacheService;
import com.github.houbb.redis.config.core.jedis.IJedisService;
import com.github.houbb.redis.config.core.jedis.PooledJedisService;
import com.github.houbb.redis.config.core.jedis.SimpleJedisService;
import com.github.houbb.redis.config.core.service.IRedisService;
import com.github.houbb.redis.config.core.service.JedisRedisService;

/**
 * @author binbin.hou
 * @since 1.1.0
 */
public final class JedisRedisServiceFactory {

    private JedisRedisServiceFactory(){}

    /**
     * 简单实现
     * @param host 地址
     * @param port 端口
     * @param password 密码
     * @return 结果
     */
    public static IRedisService simple(String host, int port, String password) {
        IJedisService jedisService = new SimpleJedisService(host, port, password);
        return new JedisRedisService(jedisService);
    }

    /**
     * 简单实现
     * @param host 地址
     * @param port 端口
     * @return 结果
     */
    public static IRedisService simple(String host, int port) {
        IJedisService jedisService = new SimpleJedisService(host, port);
        return new JedisRedisService(jedisService);
    }

    /**
     * 池化实现
     * @param host 地址
     * @param port 端口
     * @param password 密码
     * @return 结果
     */
    public static IRedisService pooled(String host, int port, String password) {
        IJedisService jedisService = new PooledJedisService(host, port, password);
        return new JedisRedisService(jedisService);
    }

    /**
     * 池化实现
     * @param host 地址
     * @param port 端口
     * @return 结果
     */
    public static IRedisService pooled(String host, int port) {
        IJedisService jedisService = new PooledJedisService(host, port);
        return new JedisRedisService(jedisService);
    }

}
