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

## 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>redis-config-core</artifactId>
    <version>1.5.0</version>
</dependency>
```

## 使用

### 初始化

```java
IRedisService redisService = JedisRedisServiceFactory.simple("127.0.0.1", 6379);
```

或者下面的池化方式：

```java
IRedisService redisService = JedisRedisServiceFactory.pooled("127.0.0.1", 6379);
```

### 使用

```java
//1. 设置
final String key = "key";
final String value = "123456";
redisService.set(key, value);

//2. 获取
Assert.assertEquals("123456", redisService.get(key));

//3. 过期
redisService.expire(key, 100, TimeUnit.SECONDS);

//4. 删除
redisService.remove(key);
Assert.assertNull(redisService.get(key));
```

# spring 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>redis-config-spring</artifactId>
    <version>1.5.0</version>
</dependency>
```

## 代码配置

指定 `@EnableRedisConfig` 注解即可。

```java
@Configurable
@ComponentScan(basePackages = "com.github.houbb.redis.config.test.service")
@EnableRedisConfig
public class SpringConfig {
}
```

## 配置说明

| 配置 | 说明 | 默认值
|:---|:---|:----|
| redis.address | redis 地址 | 127.0.0.1 |
| redis.port | redis 端口 | 6379 |
| redis.password | redis 密码 | |

## 使用入门

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
        redisService.remove(key);
        Assert.assertNull(redisService.get(key));
    }

}
```

# springboot 自动整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>redis-config-springboot-starter</artifactId>
    <version>1.5.0</version>
</dependency>
```

## 使用 

同 spring

# Road-Map

- [ ] Redisson 整合

- [ ] 更多 service 方法

## 开源矩阵

下面是一些缓存系列的开源矩阵规划。

| 名称 | 介绍 | 状态  |
|:---|:---|:----|
| [resubmit](https://github.com/houbb/resubmit) | 防止重复提交核心库 | 已开源 |
| [rate-limit](https://github.com/houbb/rate-limit) | 限流核心库 | 已开源 |
| [cache](https://github.com/houbb/cache) | 手写渐进式 redis | 已开源 |
| [lock](https://github.com/houbb/lock) | 开箱即用的分布式锁 | 已开源 |
| [common-cache](https://github.com/houbb/common-cache) | 通用缓存标准定义 | 已开源 |
| [redis-config](https://github.com/houbb/redis-config) | 兼容各种常见的 redis 配置模式 | 已开源 |
| [quota-server](https://github.com/houbb/quota-server) | 限额限次核心服务 | 待开始 |
| [quota-admin](https://github.com/houbb/quota-admin) | 限额限次控台 | 待开始 |
| [flow-control-server](https://github.com/houbb/flow-control-server) | 流控核心服务 | 待开始 |
| [flow-control-admin](https://github.com/houbb/flow-control-admin) | 流控控台 | 待开始 |