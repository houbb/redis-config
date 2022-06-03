package com.github.houbb.redis.config.core.jedis;

import com.github.houbb.heaven.util.lang.StringUtil;
import redis.clients.jedis.Jedis;

/**
 * 简单实现
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class SimpleJedisService extends AbstractJedisService {

    public SimpleJedisService(String host, int port, String password) {
        super(host, port, password);
    }

    public SimpleJedisService(String host, int port) {
        super(host, port);
    }

    @Override
    public Jedis getJedis() {
        Jedis jedis = new Jedis(host, port);
        if(StringUtil.isNotEmpty(password)) {
            jedis.auth(password);
        }

        return jedis;
    }

}
