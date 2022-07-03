package com.mymusic.myrabbitmq.listener;

import com.mymusic.myrabbitmq.po.TransMessagePO;
import com.mymusic.myrabbitmq.service.TransMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareBatchMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.text.ParseException;

/**
 * @ClassName AbstractMessageListener
 * @Description 手动写见监听器更灵活
 * @Author 86183
 * @Date2022-03-201:04
 * @Version 1.0
 **/
@Slf4j
public abstract class CustomDlxQueueListener implements ChannelAwareBatchMessageListener {
    @Autowired
    private TransMessageService transMessageService;
    public abstract void receiveMessage(Message message) throws ParseException;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String messageBody = new String(message.getBody());
        log.error("正在处理超时订单:{}",messageBody );
        receiveMessage(message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
