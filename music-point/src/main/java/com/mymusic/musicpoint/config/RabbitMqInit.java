package com.mymusic.musicpoint.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @ClassName RabbitMqInit
 * @Description TODO
 * @Author 86183
 * @Date2022-03-2422:55
 * @Version 1.0
 **/
@Configuration
public class RabbitMqInit {
    @Bean
    public Queue queue1(){
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "exchange.music.dlx");
        arguments.put("x-max-length",10);
        arguments.put("x-dead-letter-routing-key", "key.delayedpoint.dlx");//超时的任务扔给order.release.order这个路由键
        arguments.put("x-message-ttl", 5000); // 消息过期时间 1分钟
        return new Queue("queue.music.point",true,false,false,arguments);
    }
    @Bean
    public Binding binding1(){
        return new Binding("queue.music.point", Binding.DestinationType.QUEUE ,"exchange.music.point" ,"key.order.point" ,null );
    }

    @Bean
    public Exchange exchange_sign(){
        return new DirectExchange("exchange.music.sign");
    }
    @Bean
    public Queue queue_sign(){
         /*
            Queue(String name,  队列名字
            boolean durable,  是否持久化
            boolean exclusive,  是否排他
            boolean autoDelete, 是否自动删除
            Map<String, Object> arguments) 属性
         */
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "exchange.music.dlx");
        arguments.put("x-max-length",3 );
        arguments.put("x-dead-letter-routing-key", "key.sign.dlx");//超时的任务扔给order.release.order这个路由键
//        arguments.put("x-message-ttl", 60000); // 消息过期时间 1分钟
        Queue queue = new Queue("queue.music.sign", true, false, false, arguments);
        return queue;
    }
    @Bean
    public Binding binding2(){
        return new Binding("queue.music.sign", Binding.DestinationType.QUEUE ,"exchange.music.sign" ,"key.sign" ,null );
    }


//   20220520
//    @Autowired
//    MySignMessageListner mySignMessageListner;
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory,
//                                                                         MySignMessageListner mySignMessageListner) {
//
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("queue.music.sign");
//        container.setExposeListenerChannel(true);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(mySignMessageListner);
//        return container;
//    }


//    @Autowired
//    Queue dlxQueue_DaylerOrder;
//    @Bean
//    public SimpleMessageListenerContainer deadLetterListenerContainer_DaylerOrder(ConnectionFactory connectionFactory,
//                                                                                  MyDelayedOrderDlxQueueListener dlxListener) {
//
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(dlxQueue_DaylerOrder);
//        container.setExposeListenerChannel(true);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(dlxListener);
//        /** 设置消费者能处理消息的最大个数 */
//        container.setPrefetchCount(100);
//        return container;
//    }
}
