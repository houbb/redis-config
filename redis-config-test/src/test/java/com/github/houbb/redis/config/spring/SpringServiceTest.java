package com.github.houbb.redis.config.spring;


import com.github.houbb.redis.config.spring.service.SpringRedisService;
import com.github.houbb.redis.config.test.config.SpringConfig;
import com.github.houbb.redis.config.test.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
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
