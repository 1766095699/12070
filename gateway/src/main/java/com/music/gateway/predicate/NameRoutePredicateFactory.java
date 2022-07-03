package com.music.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @ClassName NameRoutePredicateFactory
 * @Description TODO
 * @Author 86183
 * @Date2022-06-179:13
 * @Version 1.0
 **/
@Component
public class NameRoutePredicateFactory extends AbstractRoutePredicateFactory<NameRoutePredicateConfig> {

    public NameRoutePredicateFactory() {
        super(NameRoutePredicateConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(NameRoutePredicateConfig config) {
        return (serverWebExchange)->{
            //获取url中的第一个参数参数
            String name = serverWebExchange.getRequest().getQueryParams().getFirst("name");
            if (StringUtils.isEmpty(name)){
                name = "";
            }
//            System.out.println("name=="+name);
//            System.out.println("config=="+config.getName());
            return name.equals(config.getName());
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
//        System.out.println("进入了shortcutFieldOrder方法");
        return Arrays.asList("name");
    }
}
