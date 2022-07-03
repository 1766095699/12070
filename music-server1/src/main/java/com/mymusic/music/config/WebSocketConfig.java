package com.mymusic.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketConfig
 * @Description TODO
 * @Author 86183
 * @Date2021-08-052:38
 * @Version 1.0
 **/
@Configuration
public class WebSocketConfig {
    //注入ServerEndpointExporter的bean对象,自动注册使用了@ServerEndPoint注解的bean
    @Bean
    public ServerEndpointExporter serverEndpoint(){
        return new ServerEndpointExporter();
    }
}
