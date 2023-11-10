package com.wly.nacosclienta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "nacos-client-b")
public interface TestFeign {

    @GetMapping("info")
    String info();
}
