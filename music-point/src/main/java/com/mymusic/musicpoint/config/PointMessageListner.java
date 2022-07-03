//package com.mymusic.musicpoint.config;
//
//import com.mymusic.myrabbitmq.listener.AbstractMessageListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.stereotype.Service;
//
//import java.text.ParseException;
//
////import com.mymusic.music.dto.SignDataDTO;
////import com.mymusic.music.feign.PointFeign;
//
///**
// * @ClassName MySignMessageListner
// * @Description TODO
// * @Author 86183
// * @Date2022-03-259:47
// * @Version 1.0
// **/
//@Service
//@Slf4j
//public class PointMessageListner extends AbstractMessageListener {
//    @Override
//    public void receiveMessage(Message message) throws ParseException {
//        String s = new String(message.getBody());
////        SignDataDTO signDataDTO = JSON.parseObject(s, SignDataDTO.class);
////        Map<String,Object> map = new HashMap<>();
////        log.info("监听器监听到的数据为："+signDataDTO);
////        map = (Map<String, Object>) pointFeign.addPoint(signDataDTO.getUserId(),signDataDTO.getRewardPoint(),signDataDTO.getEndTime(),signDataDTO.getEndTime());
////        threadLocal.set((Integer) map.get("res"));
//    }
//}
