# redis-config

[redis-config](https://github.com/houbb/redis-config) redis 配置的简单整合实现。

[![Build Status](https://travis-ci.com/houbb/redis-config.svg?branch=master)](https://travis-ci.com/houbb/redis-config)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/redis-config/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/redis-config)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/redis-config/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/redis-config)

## 创作目的

redis 的配置比较多样，每次都是重复拷贝，缺少统一规范。

## 特性

- jedis 整合

- jedis pool 整合

- spring 整合

- springboot 整合

## 变更日志

> [变更日志](https://github.com/houbb/redis-config/blob/master/CHANGELOG.md)

# 快速开始

## core 

### 引入

```xml
<dependency>
    <group>com.github.houbb</group>
    <artifact>redis-config-core</artifact>
    <version>1.0.0</version>
</dependency>
```

### jedis

```java
IJedisService simpleRedis = new SimpleJedisService("127.0.0.1", 6379);
Jedis jedis = simpleRedis.getJedis();

//1. 设置
final String key = "key";
final String value = "123456";
jedis.set(key, value);
Assert.assertEquals("123456", jedis.get(key));
```

当然，还可以通过池化的方式获取资源：

```java
IJedisService servcie = new PooledJedisService("127.0.0.1", 6379);
Jedis jedis = servcie.getJedis();
```

## spring 整合

### maven 引入

```xml
<dependency>
    <group>com.github.houbb</group>
    <artifact>redis-config-spring</artifact>
    <version>1.0.0</version>
</dependency>
```

### 代码配置

指定 `@EnableRedisConfig` 注解即可。

```java
@Configurable
@ComponentScan(basePackages = "com.github.houbb.redis.config.test.service")
@EnableRedisConfig
public class SpringConfig {
}
```

### 使用入门

直接注入 `SpringRedisService` 即可正常使用。

```java
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SpringRedisService redisService;

    @Test
    public void queryLogTest() {
        final String key = "name";
        final String value = userService.queryUserName(1L);

        redisService.set(key, value);
        //2. 获取
        Assert.assertEquals(value, redisService.get(key));
        //3. 过期
        redisService.expire(key, 100, TimeUnit.SECONDS);
        //4. 删除
        redisService.delete(key);
        Assert.assertNull(redisService.get(key));
    }

}
```

## springboot 自动整合

### maven 引入

```xml
<dependency>
    <group>com.github.houbb</group>
    <artifact>redis-config-springboot-starter</artifact>
    <version>1.0.0</version>
</dependency>
```

### 使用 

同 spring

# Road-Map

- [ ] Redisson 整合

- [ ] 更多 service 方法
