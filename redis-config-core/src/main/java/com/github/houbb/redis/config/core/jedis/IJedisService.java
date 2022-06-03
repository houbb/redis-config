package com.github.houbb.redis.config.core.jedis;

import redis.clients.jedis.Jedis;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IJedisService {

    /**
     * 获取 redis
     * @return 获取
     * @since 1.0.0
     */
    Jedis getJedis();

    /**
     * 释放资源
     * @param jedis 资源
     * @since 1.0.0
     */
    void returnJedis(final Jedis jedis);

}
