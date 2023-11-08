package com.wly.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("doOrder")
    public String doOrder() {
        return "油条豆浆-豆干面";
    }
}
