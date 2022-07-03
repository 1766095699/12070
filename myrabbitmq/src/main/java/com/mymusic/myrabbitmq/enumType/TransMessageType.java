package com.mymusic.myrabbitmq.enumType;

/**
 * @ClassName TransMessageType
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1920:24
 * @Version 1.0
 **/
public enum TransMessageType {
    SEND,
    SucessFromExchange,
    SuccessFromConsumer,
    RECEIVE,
    DEAD;
}