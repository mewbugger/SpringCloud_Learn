package com.wly.resttemplate.controller;

import com.wly.resttemplate.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("testGet")
    public String get(String name) {
        System.out.println(name);
        return "ok";
    }

    /**
     *  参数是json类型
     * @param user
     * @return
     */
    @PostMapping("testPost1")
    public String testPost1(@RequestBody User user) {
        System.out.println(user);
        return "ok";
    }

    /**
     *  接收表单参数
     */
    @PostMapping("testPost2")
    public String testPost2(User user) {
        System.out.println(user);
        return "ok";
    }

}
