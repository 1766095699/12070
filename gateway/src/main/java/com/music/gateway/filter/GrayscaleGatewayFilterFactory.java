package com.music.gateway.filter;

/**
 * @ClassName GrayscaleGatewayFilterFactory
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1710:03
 * @Version 1.0
 **/

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aki
 * @version 1.0.0
 * @description 自定义过滤器模拟实现灰度发布
 */
@Component
public class GrayscaleGatewayFilterFactory extends AbstractGatewayFilterFactory<GrayscaleGatewayFilterConfig> {

    public GrayscaleGatewayFilterFactory(){
        super(GrayscaleGatewayFilterConfig.class);
    }
    @Override
    public GatewayFilter apply(GrayscaleGatewayFilterConfig config) {
        return (exchange, chain) -> {
            if (config.isGrayscale()){
                System.out.println("开启了灰度发布功能...");
            }else{
                System.out.println("关闭了灰度发布功能...");
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("grayscale");
    }
}
