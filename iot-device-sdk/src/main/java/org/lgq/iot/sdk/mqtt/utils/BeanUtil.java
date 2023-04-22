package org.lgq.iot.sdk.mqtt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtil {

    /**
     * pojo -> json string
     * @param obj 需要有默认构造器，拥有至少一个字段，且必须要有getter方法
     * @return
     */
    public static String pojoToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }


    public static <T> T jsonToPojo(String json, Class<T> tClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, tClass);
    }
}
