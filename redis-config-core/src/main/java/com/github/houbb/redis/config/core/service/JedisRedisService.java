package com.github.houbb.redis.config.core.service;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.redis.config.core.constant.JedisConst;
import com.github.houbb.redis.config.core.exception.RedisConfigException;
import com.github.houbb.redis.config.core.jedis.IJedisService;
import com.github.houbb.redis.config.core.utils.TimeoutUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * jedis 简单实现
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class JedisRedisService extends AbstractRedisService {

    private final IJedisService jedisService;

    public JedisRedisService(IJedisService jedisService) {
        ArgUtil.notNull(jedisService, "jedisService");

        this.jedisService = jedisService;
    }

    @Override
    public void set(String key, String value) {
        String result = getJedis().set(key, value);
        checkResult(result);
    }

    @Override
    public void set(String key, String value, long expireMills) {
        this.set(key, value);
        this.expire(key, expireMills, TimeUnit.MILLISECONDS);
    }

    @Override
    public String set(String s, String s1, String s2, String s3, int i) {
        return getJedis().set(s, s1, s2, s3, i);
    }

    @Override
    public String get(String key) {
        return getJedis().get(key);
    }

    @Override
    public boolean contains(String key) {
        return getJedis().exists(key);
    }

    @Override
    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        int seconds = (int) TimeoutUtils.toSeconds(expireTime, timeUnit);

        getJedis().expire(key, seconds);
    }

    @Override
    public void remove(String key) {
        getJedis().del(key);
    }

    @Override
    public long ttl(String key) {
        Long result =  getJedis().ttl(key);
        if(result == null) {
            return -2;
        }

        return result * 1000;
    }

    @Override
    public void expireAt(String key, long unixTime) {
        getJedis().expireAt(key, unixTime);
    }

    @Override
    public long expireAt(String key) {
        long ttl = ttl(key);
        if(ttl <= 0) {
            return ttl;
        }

        // 获取时间
        long time = System.currentTimeMillis();
        return time + ttl;
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        return getJedis().eval(script, keyCount, params);
    }

    private void checkResult(String result) {
        if(!JedisConst.OK.equalsIgnoreCase(result)) {
            throw new RedisConfigException("operate failed!");
        }
    }

    private Jedis getJedis() {
        return jedisService.getJedis();
    }

}
