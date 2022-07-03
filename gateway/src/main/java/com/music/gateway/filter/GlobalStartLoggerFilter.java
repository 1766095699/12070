//package com.music.gateway.filter;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.time.Instant;
//import java.util.stream.Collectors;
//
///**
// * @author Aki
// * @version 1.0
// * @date 2022/1/7 12:19 PM
// */
//@Component
//@Slf4j
//public class GlobalStartLoggerFilter implements GlobalFilter, Ordered {
//
//    private void startLogInfo(ServerWebExchange exchange) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        String url = request.getURI().toString();
//        String params = request.getQueryParams().toString();
//        String method = request.getMethodValue();
//        String headers = request.getHeaders().entrySet().stream()
//                .map(entry -> entry.getKey() + ": [" + String.join(";", entry.getValue()) + "]")
//                .collect(Collectors.joining("\n\t"));
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("****************************************请求路径:[{}]请求开始****************************************\n");
//        builder.append("url: " + url + "\n");
////        builder.append("gateway_request_url: " + exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR) + "\n");
//        builder.append("method: " + method + "\n");
//        builder.append("params: " + params + "\n");
//        builder.append("headers: \n{ \n" + headers + "  \n}\n");
//        log.info(String.valueOf(builder), url);
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        //前置过滤
//        BaseContextHandler.getContextMap().put("beginTime", Instant.now());
//        this.startLogInfo(exchange);
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return HIGHEST_PRECEDENCE;
//    }
//}
//
