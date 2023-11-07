package com.wly.consumer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     *  思考ribbon是怎么将http://provider/hello 路径请求成功的
     *  1.拦截这个请求
     *  2.截取主机名称
     *  3.借助eureka来做服务发现List<>
     *  4.通过负载均衡算法 拿到一个服务ip port
     *  5.reConstructURL
     *  6.发起请求
     * @param serviceName
     * @return
     */
    @GetMapping("testRibbon")
    public String testRibbon(String serviceName) {
        // 正常来讲 需要 拿到ip和port 以及 路径 才可以用
        String result = restTemplate.getForObject("http://" + serviceName + "/hello", String.class);
        return result;
    }
}
