package com.wly.loginservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LoginController {

    @GetMapping("doLogin")
    public String doLogin(String name, String pwd) {
        System.out.println(name);
        System.out.println(pwd);
        // token
        String token = UUID.randomUUID().toString();
        return token;
    }
}
