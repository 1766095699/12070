//package com.mymusic.music.services.impl;
//
//import com.mymusic.music.dao.ConsumerMapper;
//import com.mymusic.music.dao.StockMapper;
//import com.mymusic.music.domain.Consumer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @ClassName StockServiceImpl
// * @Description TODO
// * @Author 86183
// * @Date2022-01-0122:55
// * @Version 1.0
// **/
//@Service
//public class StockServiceImpl {
//    @Autowired
//    public StockMapper stockMapper;
//    @Autowired
//    public ConsumerMapper consumerMapper;
//    @Transactional
//    public int decreaseStock(int id){
//        int res1 =  stockMapper.decreaseStock(id);//减库存
//        addconsumer();
//        int res2 = stockMapper.decreaseStock(id);//减库存
//        return res1+res2;
//    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public int addconsumer(){
//        Consumer consumer = new Consumer();
//        consumer.setUsername("root123");
//        consumer.setPassword("root123");
//        consumerMapper.addConsumer(consumer);
//
//        return 1;
//    }
//}

package com.mymusic.music.service.impl;

import com.mymusic.music.dao.ConsumerMapper;
import com.mymusic.music.dao.StockMapper;
import com.mymusic.music.domain.Consumer;
import com.mymusic.music.exception.BaseException;
import com.mymusic.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @ClassName StockServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-01-0122:55
 * @Version 1.0
 **/
@Service
public class StockServiceImpl {
    @Autowired
    public StockMapper stockMapper;
    @Autowired
    public ConsumerService consumerService;
    @Autowired
    public RedisTemplate redisTemplate;

    @Transactional
    public int decreaseStock(int id){
        int res1 =  stockMapper.save();//生成订单
        if(res1==0){
            throw new BaseException(0,"订单创建失败" );
        }
        int a[] = new int[3];
        ArrayList<Integer> arrayList = new ArrayList<>();

//        a = arrayList.toArray();
//        System.out.println()
//        Arrays.rev
        redisTemplate.opsForZSet().remove("follower:1",12);
        Long increment = redisTemplate.opsForHash().increment("a1", "amount", -1);
        if(increment<0){
            redisTemplate.opsForHash().increment("a1", "amount", 1);//返还库存给redis
            throw  new BaseException(0,"库存被扣完");

        }
        return 2;
    }


//    @Transactional
//    public int decreaseStock(int id){
//        int res1 =  stockMapper.decreaseStock(id);//减库存\
//
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
//        consumerService.addconsumer1();
////            }
////        }).start();
////        try {
////            Thread.sleep(1000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        System.out.println(1/0);
//        int res2 = stockMapper.decreaseStock(id);//减库存
//        return res1+res2;
//    }

}
