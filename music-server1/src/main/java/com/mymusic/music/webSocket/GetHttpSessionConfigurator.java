package com.mymusic.music.webSocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @ClassName GetHttpSessionConfigurator
 * @Description TODO
 * @Author 86183
 * @Date2021-08-053:00
 * @Version 1.0
 **/
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
//        System.out.println("get方法中的："+httpSession);
        //将httpSession对象存储到配置对象中
                sec.getUserProperties().put(HttpSession.class.getName(), httpSession);//为了保证key唯一,就用了HttpSession字节码对象的名称
    }
}
