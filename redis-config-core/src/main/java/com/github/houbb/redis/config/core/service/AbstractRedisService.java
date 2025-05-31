package com.github.houbb.redis.config.core.service;

import com.github.houbb.common.cache.api.service.AbstractCommonCacheService;
import com.github.houbb.redis.config.core.constant.JedisConst;

import java.util.Collections;

/**
 * @since 1.5.0
 */
public abstract class AbstractRedisService extends AbstractCommonCacheService implements IRedisService {

    @Override
    public boolean removeEx(String key, Object o) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        String value = String.valueOf(o);
        Object result = this.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        return JedisConst.RELEASE_SUCCESS.equals(result);
    }

}
