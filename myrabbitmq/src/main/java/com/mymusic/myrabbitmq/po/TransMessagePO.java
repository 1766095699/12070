package com.mymusic.myrabbitmq.po;

import com.mymusic.myrabbitmq.enumType.TransMessageType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName TransMessagePO
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1920:25
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
public class TransMessagePO {
    private String id;
    private String service;
    private TransMessageType type;
    private String exchange;
    private String routingKey;
    private String queue;
    private Integer sequence;
    private String payload;
    private Date date;
}

