package com.github.houbb.redis.config.test.service;

import org.springframework.stereotype.Service;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
@Service
public class UserService {

    public String queryUserName(Long userId) {
        return userId+"-name";
    }

}
