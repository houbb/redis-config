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
public class JedisRedisService implements IRedisService {

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

    private void checkResult(String result) {
        if(!JedisConst.OK.equalsIgnoreCase(result)) {
            throw new RedisConfigException("operate failed!");
        }
    }

    private Jedis getJedis() {
        return jedisService.getJedis();
    }

}
