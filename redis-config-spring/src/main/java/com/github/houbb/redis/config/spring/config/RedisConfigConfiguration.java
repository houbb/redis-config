package com.github.houbb.redis.config.spring.config;

import com.github.houbb.redis.config.spring.constant.RedisConfigConst;
import com.github.houbb.redis.config.spring.enums.RedisConnectionTypeEnum;
import com.github.houbb.heaven.util.lang.StringUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * redis 配置
 * <p>
 * https://blog.csdn.net/u013905744/article/details/91364736
 *
 * @author binbin.hou
 * @since 1.0.0
 */
@Configuration
@ComponentScan(basePackages = "com.github.houbb.redis.config")
public class RedisConfigConfiguration {

    @Value("${redis.address:127.0.0.1}")
    private String redisAddress;

    @Value("${redis.port:6379}")
    private int redisPort;

    @Value("${redis.password:}")
    private String redisPassword;

    /**
     * type:auto,cluster,sentinel,jedispool
     * auto select aliyun.com to jedispool
     */
    @Value("${redis.connection.type:auto}")
    private String redisConnectionType;

    @Value("${redis.sentinel.master:}")
    private String redisSentinelMaster;

    @Value("${redis.timeout:5000}")
    private int redisTimeout;

    @Value("${redis.maxRedirects:5}")
    private int redisMaxRedirects;

    @Value("${redis.minIdle:20}")
    private int redisMinIdle;

    @Value("${redis.maxIdle:100}")
    private int redisMaxIdle;

    @Value("${redis.maxTotal:200}")
    private int redisMaxTotal;

    @Value("${redis.maxWaitMillis:100000}")
    private int redisMaxWaitMillis;

    @Value("${redis.ssl:false}")
    private boolean redisSsl;

    @Bean(value = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean(value = "stringAliyunRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(value = "redisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory;
        if (RedisConnectionTypeEnum.AUTO.getCode().equalsIgnoreCase(redisConnectionType)) {
            if (redisAddress.contains(RedisConfigConst.ALIYUN_ADDRESS)
                    && StringUtil.isNotBlank(String.valueOf(redisPort))) {
                jedisConnectionFactory = new JedisConnectionFactory();
                jedisConnectionFactory.setHostName(redisAddress);
                jedisConnectionFactory.setPort(redisPort);
            } else if (StringUtil.isNotBlank(redisSentinelMaster)
                    && redisAddress.contains(RedisConfigConst.COMMA)) {
                jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration());
            } else if (redisAddress.contains(RedisConfigConst.COMMA)
                    && StringUtil.isBlank(redisSentinelMaster)) {
                jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration());
            } else {
                jedisConnectionFactory = new JedisConnectionFactory();
                jedisConnectionFactory.setHostName(redisAddress);
                jedisConnectionFactory.setPort(redisPort);
            }
        } else if (RedisConnectionTypeEnum.CLUSTER.getCode().equalsIgnoreCase(redisConnectionType)) {
            jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration());
        } else if (RedisConnectionTypeEnum.SENTINEL.getCode().equalsIgnoreCase(redisConnectionType)) {
            jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration());
        } else if (RedisConnectionTypeEnum.JEDISPOOL.getCode().equalsIgnoreCase(redisConnectionType)) {
            jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(redisAddress);
            jedisConnectionFactory.setPort(redisPort);
        } else {
            jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(redisAddress);
            jedisConnectionFactory.setPort(redisPort);
        }

        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setTimeout(redisTimeout);
        jedisConnectionFactory.setPassword(redisPassword);
        if (redisSsl) {
            jedisConnectionFactory.setUseSsl(true);
        }

        return jedisConnectionFactory;
    }

    private RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(getRedisTemplateAddress(redisAddress));
        redisClusterConfiguration.setMaxRedirects(redisMaxRedirects);
        return redisClusterConfiguration;
    }

    private RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.master(redisSentinelMaster);
        config.setSentinels(getRedisTemplateAddress(redisAddress));
        return config;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisMaxTotal);
        jedisPoolConfig.setMinIdle(redisMinIdle);
        jedisPoolConfig.setMaxIdle(redisMaxIdle);
        jedisPoolConfig.setMaxWaitMillis(redisMaxWaitMillis);
        return jedisPoolConfig;
    }

    private Set<RedisNode> getRedisTemplateAddress(String s) {
        Set<RedisNode> nodes = new HashSet<>();
        for (String host : s.split("(?:\\s|,)+")) {
            if ("".equals(host)) {
                continue;
            }

            int finalColon = host.lastIndexOf(':');
            if (finalColon < 1) {
                throw new IllegalArgumentException("Invalid server ``" + host
                        + "'' in list:  " + s);
            }
            String hostPart = host.substring(0, finalColon);
            String portNum = host.substring(finalColon + 1);
            nodes.add(new RedisNode(hostPart, Integer.parseInt(portNum)));
        }
        return nodes;
    }

}
