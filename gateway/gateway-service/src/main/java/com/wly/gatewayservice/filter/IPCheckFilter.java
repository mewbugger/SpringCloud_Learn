package com.wly.gatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *  网关里面 过滤器
 *  ip拦截
 *  请求都有一个源头
 */
@Component
public class IPCheckFilter implements GlobalFilter, Ordered {

    /**
     *  网关的并发比较高 不要在网关里面直接操作mysql
     *  后台系统可以查询数据库 用户量 并发量不大
     *  如果并发量大 可以查redis 或者在内存中查询
     */
    public static final List<String> BLACK_LIST = Arrays.asList("127.0.0.1", "144.128.232.147");


    /**
     *  1.拿到ip
     *  2.校验ip是否符合规范
     *  3.放行/拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getHeaders().getHost().getHostString();
        // 查询数据库 看这个ip是否存在黑名单里面
        // 只要是能存储数据的地方都叫数据库
        if (!BLACK_LIST.contains(ip)) {
            return chain.filter(exchange);
        }
        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type", "application/json;charset=utf-8");
        // 组装业务返回值
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code", 438);
        map.put("msg", "你是黑名单");

        ObjectMapper objectMapper = new ObjectMapper();
        // 把一个map转换成一个字节
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 通过buffer工厂把字节数组包装成一个数据包
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        // 放行 到下一个过滤器了
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
