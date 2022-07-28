package com.wei.blogservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 凌雪
 */
public class JsonUtil {

    private static final ObjectMapper om = new ObjectMapper();

    public static byte[] toBytes(Object o){
        try {
            return om.writeValueAsBytes(o);
        }catch (Exception ignored){
        }
        return null;
    }

    public static <T> T toObject(byte[] bytes, Class<T> tClass){
        try {
            return om.readValue(bytes, tClass);
        }catch (Exception ignored){
        }
        return null;
    }
}
