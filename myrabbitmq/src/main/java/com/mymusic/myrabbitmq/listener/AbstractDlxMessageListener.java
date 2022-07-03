//package com.mymusic.myrabbitmq.listener;
//
//import com.mymusic.myrabbitmq.service.TransMessageService;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @ClassName AbstractDlxMessageListener
// * @Description 处理死信消息的抽象类
// * @Author 86183
// * @Date2022-04-280:18
// * @Version 1.0
// **/
//@Slf4j
//public abstract class AbstractDlxMessageListener<T> implements ChannelAwareMessageListener {
//
//    @Autowired
//    TransMessageService transMessageService;
//
//    public abstract T handleDlxMessage(Message message);
//    //监听死信队列消息
//    @Override
//    public void onMessage(Message message, Channel channel) throws Exception {
//        String messageBody = new String(message.getBody());
//        log.error("dead letter message：{} | tag：{}", messageBody, message.getMessageProperties().getDeliveryTag());
//        //         发邮件告警
//        //        sendEmail(logKey, messageProperties.getMessageId(), messageBody);
//        log.error("sendEmail");
//        //入库
//        transMessageService.messageDead(
//                message.getMessageProperties().getMessageId(),
//                message.getMessageProperties().getReceivedExchange(),
//                message.getMessageProperties().getReceivedRoutingKey(),
//                message.getMessageProperties().getConsumerQueue(),
//                messageBody);
//        handleDlxMessage(message);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }
//}
