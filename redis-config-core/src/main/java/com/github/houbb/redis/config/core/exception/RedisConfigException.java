package com.github.houbb.redis.config.core.exception;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class RedisConfigException extends RuntimeException {

    public RedisConfigException() {
    }

    public RedisConfigException(String message) {
        super(message);
    }

    public RedisConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisConfigException(Throwable cause) {
        super(cause);
    }

    public RedisConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
