package com.github.houbb.redis.config.core.jedis;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.redis.config.core.exception.RedisConfigException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 线程池
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class PooledJedisService extends AbstractJedisService {

    public PooledJedisService(String host, int port, String password) {
        super(host, port, password);
    }

    public PooledJedisService(String host, int port) {
        super(host, port);
    }

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    protected int maxTotal = 512;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    protected int maxIdle = 100;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    protected int timeout = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    protected boolean testOnBorrow = true;

    /**
     * jedis 线程池
     * @since 1.0.0
     */
    protected JedisPool jedisPool = null;

    public void setMaxTotal(int maxTotal) {
        ArgUtil.positive(maxTotal, "maxTotal");

        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        ArgUtil.positive(maxIdle, "maxIdle");

        this.maxIdle = maxIdle;
    }

    public void setTimeout(int timeout) {
        ArgUtil.positive(timeout, "timeout");

        this.timeout = timeout;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * 初始化
     * @since 1.0.0
     */
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(timeout);
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(testOnBorrow);

        jedisPool = new JedisPool(config, host, port, timeout, password);
    }

    @Override
    public Jedis getJedis() {
        checkStatus();

        return jedisPool.getResource();
    }

    @Override
    public void returnJedis(Jedis jedis) {
        ArgUtil.notNull(jedis, "jedis");

        checkStatus();

        jedisPool.returnResource(jedis);
    }

    private void checkStatus() {
        if(jedisPool == null) {
            this.init();
        }
    }

}
