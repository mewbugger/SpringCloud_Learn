package com.wly.resttemplate;

import com.wly.resttemplate.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestTemplateApplicationTests {

    @Test
    void contextLoads() {
        // 在Java代码中去发送一个请求 请求一个页面
        RestTemplate restTemplate = new RestTemplate();
        // 如果你访问的是一个页面 会返回html代码
        String url = "https://www.baidu.com";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

    @Test
    void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testGet?name=wly";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

    /**
     *  参数是json格式
     */
    @Test
    void testPost1() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost1";
        User user = new User("wangleyan", 23, 30000D);
        // 发送post 而且是json参数 因为web里面默认使用jackson 会把你的对象转换成json字符串
        String result = restTemplate.postForObject(url, user, String.class);
        System.out.println(result);
    }

    /**
     *  参数是form表单格式
     */
    @Test
    void testPost2() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost2";
        // 构建表单参数
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "wangleyan");
        map.add("age", 23);
        map.add("price", 30000D);

        String result = restTemplate.postForObject(url, map, String.class);
        System.out.println(result);
    }

}
