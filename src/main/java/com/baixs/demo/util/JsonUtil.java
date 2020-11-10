package com.baixs.demo.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonUtil {
    public static String Json2String(Object object) throws JsonProcessingException {
        //ObjectMapper objectMapper = new ObjectMapper();
        //return objectMapper.writer().writeValueAsString(object);
        return JSON.toJSONString(object);
    }
}
