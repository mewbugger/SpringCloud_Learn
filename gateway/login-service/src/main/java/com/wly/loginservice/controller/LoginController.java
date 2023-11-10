package com.wly.loginservice.controller;

import com.wly.loginservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("doLogin")
    public String doLogin(String name, String pwd) {
        System.out.println(name);
        System.out.println(pwd);
        // 这里假设去做了登录
        User user = new User(1, name, pwd, 23);
        // token
        String token = UUID.randomUUID().toString();
        // 存起来
        stringRedisTemplate.opsForValue().set(token, user.toString(), Duration.ofSeconds(7200));
        return token;
    }
}
