package com.mymusic.myrabbitmq.config;

import com.mymusic.myrabbitmq.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @ClassName MyRabbitMqConfig
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1920:12
 * @Version 1.0
 **/
@Configuration
@Slf4j
public class MyRabbitMqConfig {
    @Autowired
    TransMessageService transMessageService;
    @Value("${rb.host}")
    String host;
    @Value("${rb.port}")
    int port;
    @Value("${rb.username}")
    String username;
    @Value("${rb.password}")
    String password;
    @Value("${rb.vhost}")
    String vhost;
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(username);
        connectionFactory.setVirtualHost(vhost);
        //此设置开启发送到交换机和队列的回调。确保发送端消息ack回调是否执行
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        connectionFactory.createConnection();
        return connectionFactory;
    }

        @Bean
        public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
            RabbitAdmin admin = new RabbitAdmin(connectionFactory);
            admin.setAutoStartup(true);//这个自动autoStartup我记得最好要开一下,不然会是懒加载的形式开启
            return admin;
    }


    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);//设置消费者的最大并行度
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 设置消息成功投递到交换机时的回调
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate customRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                    log.info("correlationData:{}, ack:{}, cause:{}", correlationData, ack, cause);
                    if (ack && null != correlationData) {
                        String messageId = correlationData.getId();
                        log.info("消息已正确投递到交换机, messageId:{}", messageId);
                        transMessageService.messageSendSuccess(messageId);//db修改消息的状态为SUCCESS
                    } else {
                        log.error("消息投递至交换机失败,correlationData:{}，cause:{}", correlationData, cause);
                    }
                }

                );

        /**
         * 设置消息投递失败的回调。当交换机无法把数据投递到队列时触发。比如routingkey不对,queue不存在等都有这种情况
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息无法路由,message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}",
                    message, replyCode, replyText, exchange, routingKey);
//            //TODO 发生这种情况后面可以做发短信邮箱等预警
//            transMessageService.messageSendReturn(
//                    message.getMessageProperties().getMessageId(),
//                    exchange,
//                    routingKey,
//                    new String(message.getBody())
//            );
        });
        return rabbitTemplate;
    }


}
