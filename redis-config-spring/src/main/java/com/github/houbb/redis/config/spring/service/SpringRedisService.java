package com.github.houbb.redis.config.spring.service;

import com.github.houbb.redis.config.core.utils.TimeoutUtils;
import com.github.houbb.redis.config.spring.config.RetryRedisTemplate;
import com.github.houbb.redis.config.core.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
@Service
public class SpringRedisService implements IRedisService {

    @Autowired
    private RetryRedisTemplate retryRedisTemplate;

    @Override
    public void set(String key, String value) {
        retryRedisTemplate.opsForValueSet(key, value);
    }

    @Override
    public void set(String key, String value, long expireMills) {
        int seconds = (int) TimeoutUtils.toSeconds(expireMills, TimeUnit.MILLISECONDS);
        retryRedisTemplate.opsForValueSet(key, value, seconds);
    }

    @Override
    public String get(String key) {
        return retryRedisTemplate.opsForValueGet(key);
    }

    @Override
    public boolean contains(String key) {
        String value = retryRedisTemplate.opsForValueGet(key);
        return value != null;
    }

    @Override
    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        retryRedisTemplate.expire(key, expireTime, timeUnit);
    }

    @Override
    public void remove(String key) {
        retryRedisTemplate.opsForValueDelete(key);
    }

    @Override
    public long ttl(String key) {
        Long expireTime = retryRedisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        if(expireTime == null) {
            return -2;
        }

        return expireTime;
    }

    @Override
    public void expireAt(String key, long unixTime) {
        retryRedisTemplate.expire(key, unixTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public long expireAt(String key) {
        long expireTime = this.ttl(key);
        if(expireTime < 0) {
            return expireTime;
        }

        long time = System.currentTimeMillis();
        return time + expireTime;
    }

}
