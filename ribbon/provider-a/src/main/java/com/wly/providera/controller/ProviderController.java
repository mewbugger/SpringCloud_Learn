package com.wly.providera.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {


    @GetMapping("hello")
    public String hello () {
        return "我是提供者aaa的接口";
    }
}
