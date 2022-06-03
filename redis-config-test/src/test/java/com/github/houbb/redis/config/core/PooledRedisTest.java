package com.github.houbb.redis.config.core;

import com.github.houbb.redis.config.core.jedis.IJedisService;
import com.github.houbb.redis.config.core.jedis.PooledJedisService;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class PooledRedisTest {

    @Test
    public void pooledRedisTest() {
        IJedisService simpleRedis = new PooledJedisService("127.0.0.1", 6379);
        Jedis jedis = simpleRedis.getJedis();

        //1. 设置
        final String key = "key";
        final String value = "123456";
        jedis.set(key, value);
        Assert.assertEquals("123456", jedis.get(key));
    }

}
