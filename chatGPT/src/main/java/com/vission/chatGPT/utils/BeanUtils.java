package com.vission.chatGPT.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

@Slf4j
public class BeanUtils {

    /**
     * 深拷贝
     *
     * @param <S>    the type parameter
     * @param <T>    the type parameter
     * @param source 源对象
     * @param target 目标对象类型
     * @return 目标对象 t
     */
    public static <S, T> T deepCopy(S source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        T result;
        try {
            result = JsonUtils.parse(JsonUtils.toJson(source), target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 浅拷贝
     *
     * @param <S>    the type parameter
     * @param <T>    the type parameter
     * @param source 源对象
     * @param target 目标对象类型
     * @return 目标对象 t
     */
    public static <S, T> T shallowCopy(S source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        T result;
        try {
            result = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 深拷贝列表
     *
     * @param <S>    the type parameter
     * @param <T>    列表的Item的泛型
     * @param source 列表
     * @param target 列表的Item的Class对象
     * @return 目标列表 list
     */
    public static <S, T> List<T> deepCopyList(List<S> source, Class<T> target) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        List<T> result;
        try {
            result = JsonUtils.stringToList(JsonUtils.toJson(source), target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 浅拷贝列表
     *
     * @param <S>    the type parameter
     * @param <T>    列表的Item的泛型
     * @param source 列表
     * @param target 列表的Item的Class对象
     * @return 目标列表 list
     */
    public static <S, T> List<T> shallowCopyList(List<S> source, Class<T> target) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (S o : source) {
            result.add(BeanUtils.shallowCopy(o, target));
        }
        return result;
    }

    /**
     * 实例化类
     *
     * @param clazz 对象类型
     * @param <T>   对象类型
     * @return 对象实例
     */
    public static <T> T instantiateClass(Class<T> clazz) {
        return org.springframework.beans.BeanUtils.instantiateClass(clazz);
    }

}
