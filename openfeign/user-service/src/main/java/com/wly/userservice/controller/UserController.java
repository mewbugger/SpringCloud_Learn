package com.wly.userservice.controller;

import com.wly.userservice.domain.Order;
import com.wly.userservice.feign.UserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserOrderFeign userOrderFeign;

    /**
     *  总结
     *  浏览器（前端）------->user-service(/userOoOrder)----RPC---->order-service(/doOrder)
     *  * @return
     */
    @GetMapping("userDoOrder")
    public String userDoOrder() {
        System.out.println("有用户进来了");
        String result = userOrderFeign.doOrder();
        return result;
    }

    @GetMapping("testParam")
    public String testParam() {
        String wly = userOrderFeign.testUrl("wly", 18);
        System.out.println(wly);

        String 老王 = userOrderFeign.oneParam("老王");
        System.out.println(老王);

        String wly1 = userOrderFeign.twoParam("wly", 23);
        System.out.println(wly1);

        Order order = Order.builder().name("麦当劳").price(199D).time(new Date()).id(1).build();

        String s = userOrderFeign.oneObj(order);
        System.out.println(s);

        String s1 = userOrderFeign.oneObjOneParam(order, "王乐岩");
        System.out.println(1);

        return "ok";
    }
}
