package com.baixs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/redis")
    @ResponseBody
    public String redis(String name, String value) {
        stringRedisTemplate.opsForValue().set(name, value);
        return stringRedisTemplate.opsForValue().get(name);
    }
}
