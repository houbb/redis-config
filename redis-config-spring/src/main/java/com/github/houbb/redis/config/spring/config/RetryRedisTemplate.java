package com.github.houbb.redis.config.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 重试 redis 模板
 * @since 1.0.0
 * @author binbin.hou
 */
@Component("retryRedisTemplate")
public class RetryRedisTemplate {

    @Autowired
    @Qualifier("stringAliyunRedisTemplate")
    private StringRedisTemplate template;

    /**
     * 重试次数
     * @since 1.0.0
     */
    @Value("${redis.retryTimes:3}")
    private int redisRetryTimes;

    public void opsForValueSet(String key, String value) {
        opsForValueSetRetry(key, value, 0);
    }

    public void opsForValueSetRetry(String key, String value, int i) {
        try {
            template.opsForValue().set(key, value);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForValueSet(key, value, i);
        }
    }

    public String opsForValueGet(String key) {
        return opsForValueGet(key, 0);
    }

    public String opsForValueGet(String key, int i) {
        try {
            return template.opsForValue().get(key);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForValueGet(key, i);
        }
    }

    public List<String> opsForValueMGet(final String... keys) {
        return opsForValueMGet(0, keys);
    }

    public List<String> opsForValueMGet(int i, final String... keys) {
        try {
            List<String> list = new ArrayList<String>();
            for (String key : keys) {
                String v = template.opsForValue().get(key);
                list.add(v);
            }
            return list;
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForValueMGet(i, keys);
        }
    }

    public void opsForValueDelete(String key) {
        opsForValueDelete(key, 0);
    }

    public void opsForValueDelete(String key, int i) {
        try {
            template.delete(key);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForValueDelete(key, i);
        }
    }

    public void opsForValueSet(String key, String value, int expire) {
        opsForValueSet(key, value, expire, 0);
    }

    public void opsForValueSet(String key, String value, int expire, int i) {
        try {
            template.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForValueSet(key, value, expire, i);
        }
    }

    public void opsForValueIncrement(String key, String value, int expire) {
        opsForValueIncrement(key, value, expire, 0);
    }

    public void opsForValueIncrement(String key, String value, int expire, int i) {
        try {
            template.opsForValue().increment(key, Long.parseLong(value));
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForValueIncrement(key, value, expire, i);
        }
    }

    public Long opsForValueIncrement(String key, long value) {
        return opsForValueIncrement(key, value, 0);
    }

    public Long opsForValueIncrement(String key, long value, int i) {
        try {
            return template.opsForValue().increment(key, value);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForValueIncrement(key, value, i);
        }
    }


    public String opsForHashGet(String key, String hashKey) {
        return opsForHashGet(key, hashKey, 0);
    }

    public String opsForHashGet(String key, String hashKey, int i) {
        try {
            return (String) template.opsForHash().get(key, hashKey);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForHashGet(key, hashKey, i);
        }
    }

    public void opsForHashPut(String key, String field, String value) {
        opsForHashPut(key, field, value, 0);
    }

    public void opsForHashPut(String key, String field, String value, int i) {
        try {
            template.opsForHash().put(key, field, value);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForHashPut(key, field, value, i);
        }
    }

    public void opsForHashPutAll(String key, final Map<String, String> value) {
        opsForHashPutAll(key, value, 0);
    }

    public void opsForHashPutAll(String key, final Map<String, String> value, int i) {
        try {
            template.opsForHash().putAll(key, value);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForHashPutAll(key, value, i);
        }
    }

    public Long opsForHashRemove(String key, String hashKey) {
        return opsForHashRemove(key, hashKey, 0);
    }

    public Long opsForHashRemove(String key, String hashKey, int i) {
        try {
            return template.opsForHash().delete(key, hashKey);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForHashRemove(key, hashKey, i);
        }
    }

    public Map<String, String> opsForHashEntries(String key) {
        return opsForHashEntries(key, 0);
    }

    public Map<String, String> opsForHashEntries(String key, int i) {
        try {
            return template.<String, String>opsForHash().entries(key);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForHashEntries(key, i);
        }
    }

    public Long opsForHashSize(String key) {
        return opsForHashSize(key, 0);
    }

    public Long opsForHashSize(String key, int i) {
        try {
            return template.<String, String>opsForHash().size(key);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.opsForHashSize(key, i);
        }
    }

    public void expire(String key, long expire, TimeUnit timeUnit) {
        expire(key, expire, timeUnit, 0);
    }

    public void expire(String key, long expire, TimeUnit timeUnit, int i) {
        try {
            template.expire(key, expire, timeUnit);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.expire(key, expire, timeUnit);
        }
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        return getExpire(key, timeUnit, 0);
    }

    public Long getExpire(String key, TimeUnit timeUnit, int i) {
        try {
            return template.getExpire(key, timeUnit);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            return this.getExpire(key, timeUnit);
        }
    }

    public void opsForSetMembers(String key) {
        opsForSetMembers(key, 0);
    }

    public void opsForSetMembers(String key, int i) {
        try {
            template.opsForSet().members(key);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForSetMembers(key, i);
        }
    }

    public void opsForSetRemove(String key, String field) {
        opsForSetRemove(key, field, 0);
    }

    public void opsForSetRemove(String key, String field, int i) {
        try {
            template.opsForSet().remove(key, field);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForSetRemove(key, field, i);
        }
    }

    public void opsForSetAdd(String key, String value) {
        opsForSetAdd(key, value, 0);
    }

    public void opsForSetAdd(String key, String value, int i) {
        try {
            template.opsForSet().add(key, value);
        } catch (Exception ex) {
            if (i == redisRetryTimes) {
                throw new RuntimeException(ex);
            }
            i++;
            this.opsForSetAdd(key, value, i);
        }
    }
}
