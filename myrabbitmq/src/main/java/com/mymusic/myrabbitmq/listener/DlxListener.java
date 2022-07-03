package com.mymusic.myrabbitmq.listener;

import com.mymusic.myrabbitmq.service.TransMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @ClassName DlxListener
 * @Description TODO
 * @Author 86183
 * @Date2022-03-208:48
 * @Version 1.0
 **/
@Component
//@ConditionalOnProperty("moodymq.dlxEnabled")
@Slf4j
public class DlxListener implements ChannelAwareMessageListener {

    @Autowired
    TransMessageService transMessageService;

    //监听死信队列消息
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String messageBody = new String(message.getBody());
        log.error("dead letter message：{} | tag：{}", messageBody, message.getMessageProperties().getDeliveryTag());
        //         发邮件告警
        //        sendEmail(logKey, messageProperties.getMessageId(), messageBody);
        log.error("sendEmail");
        //入库
        transMessageService.messageDead(
                message.getMessageProperties().getMessageId(),
                message.getMessageProperties().getReceivedExchange(),
                message.getMessageProperties().getReceivedRoutingKey(),
                message.getMessageProperties().getConsumerQueue(),
                messageBody);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}

