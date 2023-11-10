package com.wly.nacosclienta.controller;

import com.wly.nacosclienta.feign.TestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TestFeign testFeign;

    @GetMapping("test")
    public String test() {
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-client-b");
        System.out.println(instances);
        return testFeign.info();
    }
}
