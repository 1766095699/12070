package com.mymusic.music.config;

import com.alibaba.fastjson.JSON;
import com.mymusic.music.domain.Song;
import com.mymusic.music.domain.TestA;
import com.mymusic.music.dto.SignDataDTO;
import com.mymusic.music.feign.PointFeign;
import com.mymusic.myrabbitmq.listener.AbstractMessageListener;
import com.mymusic.myrabbitmq.resend.TransMessageSender;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.ch.ThreadPool;

import java.text.ParseException;
import java.util.AbstractSequentialList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MySignMessageListner
 * @Description TODO
 * @Author 86183
 * @Date2022-03-259:47
 * @Version 1.0
 **/
@Service
@Slf4j
public class MySignMessageListner extends AbstractMessageListener {
    @Autowired
    private PointFeign pointFeign;
    ThreadLocal<Integer> threadLocal = new ThreadLocal<>();//不能用全局变量int id来代替,会产生线程共享的问题,要改用ThreadLocal
    @Transactional
    @Override
    public void receiveMessage(Message message) throws ParseException {
        String s = new String(message.getBody());
       SignDataDTO signDataDTO = JSON.parseObject(s, SignDataDTO.class);
        Map<String,Object> map = new HashMap<>();
        log.info("监听器监听到的数据为："+signDataDTO);
        map = (Map<String, Object>) pointFeign.addPoint(signDataDTO.getUserId(),signDataDTO.getRewardPoint(),signDataDTO.getEndTime(),signDataDTO.getEndTime());
        threadLocal.set((Integer) map.get("res"));
    }

//    @Override
//    public void handleError(Message message) {
//        log.warn("开始执行事务补偿机制");
//        pointFeign.delete(threadLocal.get());
//    }

    @Override
    public void onMessageBatch(List<Message> list, Channel channel) {

    }
}
