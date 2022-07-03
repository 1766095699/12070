package com.music.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;
/**
 * @ClassName GlobalGatewayLogFilter
 * @Description 拦截请求参数,URL等信息的过滤器(用于打印记录日志信息)
 * @Author 86183
 * @Date2022-06-178:21
 * @Version 1.0
 **/
@Slf4j
@Component
public class GlobalGatewayLogFilter implements GlobalFilter, Ordered {
    /**
     * 开始访问时间
     */
    private static final String BEGIN_VISIT_TIME = "begin_visit_time";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //先记录下访问接口的开始时间

        exchange.getAttributes().put(BEGIN_VISIT_TIME, System.currentTimeMillis());//这里put的时间在下面可以get到
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (beginVisitTime != null){

                log.info("访问网关接口主机: {}",exchange.getRequest().getURI().getHost());
                log.info("访问网关接口端口: {}",exchange.getRequest().getURI().getPort());
                log.info("访问实际接口URI: {}", exchange.getRequest().getURI().getPath());
                log.info("访问实际接口URL参数: {}", exchange.getRequest().getURI().getRawQuery());
//                System.out.println(exchange.getRequest().getRemoteAddress());//这个RemoteAddress()不知道是个啥,会一直变
                log.info("访问的实际接口的完整URL: {}",exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR).toString());
                log.info("访问接口响应的状态码: {}",exchange.getResponse().getStatusCode().toString());
                log.info("访问接口时长: {}",(System.currentTimeMillis() - beginVisitTime) + "ms");
                System.out.println(exchange.mutate().response(exchange.getResponse()).build());
                String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                System.out.println(originalResponseContentType);

//
            }
        }));
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
