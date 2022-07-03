package com.mymusic.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResultMessage
 * @Description 服务器发送给浏览器的websocket数据
 * @Author 86183
 * @Date2021-08-0419:37
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage {
    private boolean isSystem;//是否是系统消息
    private String fromName;//发送消息的用户
    private String toName; //接收消息的用户
    private Object message;//如果是系统消息则传递的是数组

}
