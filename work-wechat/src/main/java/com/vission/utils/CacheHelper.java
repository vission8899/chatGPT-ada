package com.vission.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

/**
 * @author vission
 * @date 2023/3/7 9:44 下午
 * @description
 */
public class CacheHelper {

    private static Cache<String, String> cache;

    static {
        CacheHelper.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(120, TimeUnit.MINUTES)
                .build();
    }

    public static void set(String key, String value) {
        CacheHelper.cache.put(key, value);
    }

    public static String get(String key) {
        return CacheHelper.cache.getIfPresent(key);
    }


}
