package com.mymusic.myrabbitmq.task;

import com.mymusic.myrabbitmq.po.TransMessagePO;
import com.mymusic.myrabbitmq.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ResendTask
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1920:36
 * @Version 1.0
 **/
@EnableScheduling
@Configuration
@Component
@Slf4j
public class ResendTask {

    @Autowired
    TransMessageService transMessageService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("#{new Integer('${rb.resendTimes}')}")
    int resendTimes;

    @Scheduled(fixedDelayString = "${rb.resendFreq}")
    public void resendMessage() {
        log.info("resendMessage()");
//        System.out.println("----------------"+transMessageService.messageResend(););
        List<TransMessagePO> messagePOS = transMessageService.listReadyMessages();
        for (TransMessagePO messagePO : messagePOS) {
            log.info("resendMessage(): messagePO:{}", messagePO);
            if (messagePO.getSequence() > resendTimes) {
                log.error("message resend too many times! transMessagePO:{}", messagePO);
                transMessageService.messageDead(messagePO.getId());
                continue;
            }
            //设置消息属性
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType("application/json");
            Message message = new Message(messagePO.getPayload().getBytes(), messageProperties);
            message.getMessageProperties().setMessageId(messagePO.getId());
            //发送
            rabbitTemplate.convertAndSend(messagePO.getExchange(), messagePO.getRoutingKey(),
                    message, new CorrelationData(messagePO.getId()));
            transMessageService.messageResend(messagePO.getId());
        }
    }
}
