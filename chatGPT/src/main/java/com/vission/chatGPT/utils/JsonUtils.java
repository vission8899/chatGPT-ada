package com.vission.chatGPT.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Jackson工具类
 *
 * @since 2022 /3/8 15:40
 */
@Slf4j
public class JsonUtils {

    /**
     * The constant OBJECT_MAPPER.
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 失败处理
        JsonUtils.OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonUtils.OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 日期格式化
        JsonUtils.OBJECT_MAPPER.registerModule(new JavaTimeModule());
        JsonUtils.OBJECT_MAPPER.findAndRegisterModules();
    }

    /**
     * 将对象序列化成json字符串
     *
     * @param <T>   T 泛型标记
     * @param value javaBean
     * @return jsonString json字符串
     */
    public static <T> String toJson(T value) {
        try {
            return JsonUtils.OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param <T>       T 泛型标记
     * @param content   content
     * @param valueType class
     * @return Bean t
     */
    public static <T> T parse(String content, Class<T> valueType) {
        if (null == content || "".equals(content)) {
            return null;
        }
        try {
            return JsonUtils.OBJECT_MAPPER.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转换成list 对象
     *
     * @param json   Json字符串
     * @param object 目标类
     * @param <T>    泛型类
     * @return 集合
     */
    public static <T> List<T> stringToList(String json, Class<T> object) {

        try {
            if (StringUtils.isBlank(json)) {
                return null;
            }
            CollectionType listType = JsonUtils.OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(ArrayList.class, object);
            return JsonUtils.OBJECT_MAPPER.readValue(json, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
