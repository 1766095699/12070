package com.mymusic.myrabbitmq.listener;

import com.mymusic.myrabbitmq.po.TransMessagePO;
import com.mymusic.myrabbitmq.service.TransMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareBatchMessageListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
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
public abstract class AbstractMessageListener implements ChannelAwareMessageListener {
    @Autowired
    private TransMessageService transMessageService;

    @Value("${rb.resendTimes}")
    private Integer resendTimes;
    @Autowired
    private AbstractMessageListener abstractMessageListener;
    @Transactional
    public abstract void receiveMessage(Message message) throws ParseException;

    @Transactional
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();

        TransMessagePO transMessagePO = transMessageService.messageReceiveReady(messageProperties.getMessageId(), messageProperties.getReceivedExchange(),
                messageProperties.getReceivedRoutingKey(),messageProperties.getConsumerQueue() ,new String(message.getBody()));
        log.info("收到消息:{},消费次数:{}",messageProperties.getMessageId(),transMessagePO.getSequence());
        log.info("transMessage{}",transMessagePO);
        //下面是正式的业务处理
        try{
            //这个方法是一个抽象方法,由业务子类去实现(这里用了模板方法设计模式)
           abstractMessageListener.receiveMessage(message);//如果在这个方法中发生异常,直接回滚,消息状态变为SuccessFromExchange
           //如果receiveMessage处理完了,但是在basicAck或者数据库修改消息状态的时候出问题了,导致消息重复消费怎么办,无法保证消息的幂等性？
           //消费完成,发送ack确认
           channel.basicAck(deliveryTag,false);//如果在ack途中发生异常也会有事务机制重新回滚数据库数据重新执行
            //如果已经成功ack了但是在修改消息消费状态的时候失败了,此时因为保证了了接口的幂等性,所以消息重发也不影响结果
            //消息消费成功,删除消息
            transMessageService.messageReceiveSuccess(messageProperties.getMessageId());//修改消息
        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//分布式事务无法直接回滚
            log.error(e.getMessage(),e);
            if(transMessagePO.getSequence()>=resendTimes){
                channel.basicReject(deliveryTag,false );//直接把消息送到死信队列
            }else{
                Thread.sleep((long) Math.pow(2,transMessagePO.getSequence()));//每次睡眠时间一次增加,2,4,8,16,....,因为越后面说明越难成功
                log.warn("正在尝试重发消息"+transMessagePO.getSequence());
                channel.basicNack(deliveryTag,false ,true );//消息重回对垒做重试
                throw new Exception();
            }
        }
    }
}
