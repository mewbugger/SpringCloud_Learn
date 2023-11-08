package com.wly.customerservice.controller;

import com.wly.customerservice.feign.CustomerRentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Qualifier("com.wly.customerservice.feign.CustomerRentFeign")
    @Autowired
    private CustomerRentFeign customerRentFeign;

    @GetMapping("customerRent")
    public String CustomerRent() {
        System.out.println("客户来租车了");
        // RPC
        String result = customerRentFeign.rent();
        return result;
    }
}
