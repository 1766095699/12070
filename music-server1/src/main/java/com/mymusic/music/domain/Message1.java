package com.mymusic.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Message1
 * @Description TODO
 * @Author 86183
 * @Date2021-11-2721:10
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message1 {
    Integer userid;//接收这条群消息的人
    Integer fromid;//发送群消息消息的人
    Integer toid;
    Integer type;//发送的消息类型(0表示双人聊天,1表示群聊)
    String content;
    Integer contentType;//数据类型

    @Override
    public String toString() {
        return "Message1{" +
                "userid=" + userid +
                ", fromid=" + fromid +
                ", toid=" + toid +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}
