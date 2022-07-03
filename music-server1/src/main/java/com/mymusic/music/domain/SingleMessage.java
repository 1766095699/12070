package com.mymusic.music.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SingleMessage
 * @Description 存储单条聊天记录
 * @Author 86183
 * @Date2021-08-0517:10
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
public class SingleMessage {
    //消息内容
    String message;
    //(用来判断该条消息是否是用户发的)
    boolean issMyMsg;
    //消息发送方
    String fromNmae;
    //消息接收方
    String toName;
}
