package com.mymusic.myrabbitmq.config;

import com.mymusic.myrabbitmq.listener.DlxListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DlxConfig
 * @Description TODO
 * @Author 86183
 * @Date2022-03-208:42
 * @Version 1.0
 **/
@Configuration
public class DlxConfig {
    /**
     * 配置music项目的死信交换机
     * @return
     */
    @Bean
    public TopicExchange dlxExchange(){
        /**
         * 交换机名,数据是否持久化,是否自动删除，额外的参数
         * 自动删除是指和该交换机绑定的队列或其它交换机解除绑定关系
         * autoDelete：当最后一个绑定到Exchange上的队列(应该还有或者交换机)删除后，自动删除该Exchange
         */
        return new TopicExchange("exchange.music.dlx",true,false,null);
    }


    @Bean
    public Queue dlxQueue(){
        String t="";
        t.toCharArray();
//        new String
        return new Queue("queue.music.dlx",true,false,false);
    }

    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("#");//#表示所有消息都路由进去
    }

    @Bean
    public SimpleMessageListenerContainer deadLetterListenerContainer(ConnectionFactory connectionFactory,
                                                                      DlxListener dlxListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(dlxQueue());
        container.setExposeListenerChannel(true);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(dlxListener);
        /** 设置消费者能处理消息的最大个数 */
        container.setPrefetchCount(100);
        return container;
    }

    @Bean
    public Queue dlxQueue_DaylerOrder(){
        return new Queue("queue.music.order.dlx",true,false,false);
    }

    @Bean
    public Binding dlxBinding_DaylerOrder(){
        return BindingBuilder.bind(dlxQueue_DaylerOrder()).to(dlxExchange()).with("key.delayedorder.dlx");//#表示所有消息都路由进去
    }





}
