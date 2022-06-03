package com.github.houbb.redis.config.core.jedis;

import redis.clients.jedis.Jedis;

/**
 * 线程池
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public abstract class AbstractJedisService implements IJedisService {

    /**
     * 地址
     * @since 1.0.0
     */
    protected String host = "127.0.0.1";

    /**
     * 端口
     */
    protected int port = 6379;

    /**
     * 密码
     */
    protected String password = null;

    public AbstractJedisService(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public AbstractJedisService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void returnJedis(Jedis jedis) {

    }

}
