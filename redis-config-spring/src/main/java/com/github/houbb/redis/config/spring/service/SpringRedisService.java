package com.github.houbb.redis.config.spring.service;

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
    public String get(String key) {
        return retryRedisTemplate.opsForValueGet(key);
    }

    @Override
    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        retryRedisTemplate.expire(key, expireTime, timeUnit);
    }

    @Override
    public void delete(String key) {
        retryRedisTemplate.opsForValueDelete(key);
    }

}
