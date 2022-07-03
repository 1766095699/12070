package com.music.gateway.filter;

/**
 * @ClassName GatewayFilterConfig
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1710:26
 * @Version 1.0
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @author binghe
 * @version 1.0.0
 * @description 网关过滤器配置
 */
@Configuration
@Slf4j
public class GatewayFilterConfig {
    @Bean
    @Order(-1)
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            log.info("执行前置过滤器逻辑");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("执行后置过滤器逻辑");
            }));
        };
    }
}
