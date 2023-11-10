package com.wly.gatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {

    public static final List<String> ALLOW_URL = Arrays.asList("/login-service/doLogin", "/muUrl");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *  前提是 和前端约定好 一般是放在请求头里面 一般key是 Authorization value是 bearer token
     *  1.拿到请求url
     *  2.判断放行
     *  3.拿到请求头
     *  4.拿到token
     *  5.校验
     *  6.放行/拦截
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (ALLOW_URL.contains(path)) {
            return chain.filter(exchange);
        }
        // 检查
        HttpHeaders headers = request.getHeaders();
        List<String> authorization = headers.get("Authorization");
        if (!CollectionUtils.isEmpty(authorization)) {
            String token = authorization.get(0);
            if (StringUtils.hasText(token)) {
                // 约定好是有前缀的 bearer token
                String realToken = token.replaceFirst("bearer ", "");
                if (StringUtils.hasText(realToken) && stringRedisTemplate.hasKey(realToken)) {
                    return chain.filter(exchange);
                }
            }
        }
        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type", "application/json;charset=utf-8");

        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "未授权");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

