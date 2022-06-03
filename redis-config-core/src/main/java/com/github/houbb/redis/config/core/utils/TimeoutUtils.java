package com.github.houbb.redis.config.core.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public final class TimeoutUtils {

    private TimeoutUtils(){}

    public static long toSeconds(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toSeconds(timeout));
    }

    public static long toMillis(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toMillis(timeout));
    }

    private static long roundUpIfNecessary(long timeout, long convertedTimeout) {
        return timeout > 0L && convertedTimeout == 0L ? 1L : convertedTimeout;
    }

}
