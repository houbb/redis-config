package com.github.houbb.redis.config.spring.enums;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public enum RedisConnectionTypeEnum {
    AUTO("auto", "自动"),
    CLUSTER("cluster", "集群"),
    SENTINEL("sentinel", "哨兵"),
    JEDISPOOL("jedispool", "池化"),
    ;

    private final String code;
    private final String desc;

    RedisConnectionTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
