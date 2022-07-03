package com.mymusic.music.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName Message
 * @Description 浏览器发送给服务器的websocket数据
 * @Author LJH
 * @Date2021-08-0419:36
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer id;
    private Integer fromid;
    private Integer toid;
    private String content;
    private Integer type;//消息是群聊还是私聊
    private Integer state;
    private Integer contentType;//消息类型
    private MyFile myFile;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date sendTime;
    private Boolean status;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date send_time;

}
