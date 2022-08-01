package com.github.houbb.redis.config.core;

import com.github.houbb.redis.config.core.factory.JedisRedisServiceFactory;
import com.github.houbb.redis.config.core.service.IRedisService;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class JedisRedisServiceTest {

    @Test
    public void simpleRedisTest() {
        IRedisService redisService = JedisRedisServiceFactory.simple("127.0.0.1", 6379);

        //1. 设置
        final String key = "key";
        final String value = "123456";
        redisService.set(key, value);
        //2. 获取
        Assert.assertEquals("123456", redisService.get(key));
        //3. 过期
        redisService.expire(key, 100, TimeUnit.SECONDS);
        //4. 删除
        redisService.remove(key);
        Assert.assertNull(redisService.get(key));
    }

    @Test
    public void pooledRedisTest() {
        IRedisService redisService = JedisRedisServiceFactory.pooled("127.0.0.1", 6379);

        //1. 设置
        final String key = "key";
        final String value = "123456";
        redisService.set(key, value);
        //2. 获取
        Assert.assertEquals("123456", redisService.get(key));
        //3. 过期
        redisService.expire(key, 100, TimeUnit.SECONDS);
        //4. 删除
        redisService.remove(key);
        Assert.assertNull(redisService.get(key));
    }

}
