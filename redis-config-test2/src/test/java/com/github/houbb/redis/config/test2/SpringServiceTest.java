package com.github.houbb.redis.config.test2;


import com.github.houbb.redis.config.spring.service.SpringRedisService;
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
@ContextConfiguration(classes = MyApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringServiceTest {

    @Autowired
    private SpringRedisService redisService;

    @Test
    public void queryLogTest() {
        final String key = "name";
        final String value = "value";

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
