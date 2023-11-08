package com.wly.orderservice.controller;

import com.wly.orderservice.domain.Order;
import org.springframework.web.bind.annotation.*;

/**
 *  url /doOrder/热干面/油条
 *  get传递一个参数
 *  get传递多个参数
 *  post传递一个对象
 *  post传递一个对象+一个基本参数
 */
@RestController
public class ParamController {

    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable("name")String name, @PathVariable("age")Integer age) {
        System.out.println(name + ":" + age);
        return "ok";
    }

    @GetMapping("oneParam")
    public String oneParam(@RequestParam String name) {
        System.out.println(name);
        return "ok";
    }

    @GetMapping("twoParam")
    public String twoParam(@RequestParam String name, @RequestParam Integer age) {
        System.out.println(name);
        System.out.println(age);
        return "ok";
    }


    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order){
        System.out.println(order);
        return "ok";
    }

    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order, @RequestParam("name") String name) {
        System.out.println(name);
        System.out.println(order);
        return "ok";
    }

}
