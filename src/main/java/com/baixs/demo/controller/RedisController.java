package com.baixs.demo.controller;

import com.baixs.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/redis")
    @ResponseBody
    public String redis(String name, String value) {
        stringRedisTemplate.opsForValue().set(name, value);
        User u = new User(1, "白福均", "123");
        redisTemplate.opsForValue().set("name", u);
        return stringRedisTemplate.opsForValue().get(name);
    }
}
