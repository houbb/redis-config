package com.github.houbb.redis.config.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

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

    /**
     * 执行回调
     * @param redisCallback 回调
     * @return 结果
     * @param <T> 反省
     * @since 1.5.0
     */
    public <T> T execute(RedisCallback<T> redisCallback) {
        return template.execute(redisCallback);
    }

    /**
     * 不存在，才设置
     * @param key 键
     * @param value 值
     */
    public void setIfAbsent(String key, String value) {
        template.opsForValue().setIfAbsent(key, value);
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


    /**
     * 执行脚本
     * @param script 脚本
     * @param keyCount 总数
     * @param params 参数
     * @return 结果
     * @since 1.3.0
     */
    public Object eval(final String script, final int keyCount, final String... params) {
        // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        RedisCallback<Object> callback = new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return ((JedisCluster) nativeConnection).eval(script, keyCount, params);
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return ((Jedis) nativeConnection).eval(script, keyCount, params);
                }
                return null;
            }
        };

        return template.execute(callback);
    }

}
