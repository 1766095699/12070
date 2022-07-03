package com.mymusic.music.controller;

import com.mymusic.music.common.R;
import com.mymusic.music.dto.SignDataDTO;
import com.mymusic.music.exception.SignException;
import com.mymusic.music.feign.PointFeign;
import com.mymusic.music.service.SignService;
import com.mymusic.myrabbitmq.resend.TransMessageSender;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName SignController
 * @Description 签到
 * @Author 86183
 * @Date2022-03-179:53
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("sign")
public class SignController {
    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    public SignService signService;
    @Autowired
    private PointFeign pointFeign;
    @Autowired
    private TransMessageSender transMessageSender;
    /**
     * 获取当前最长连续签到次数的接口
     * @param userid
     * @return
     */
    //TODO 这个接口要压测做一下幂等性的判断
    @Transactional
    @GetMapping("signData")
    public Object signData(@RequestParam("userid")Integer userid) throws ExecutionException, InterruptedException, ParseException {
        long start = System.currentTimeMillis();
        //任务1：执行签到
//        transMessageSender.send(, , );
        CompletableFuture<Boolean>future1 = CompletableFuture.supplyAsync(()->{
            return sign(userid.toString());//签到成功才能继续向下执行,否则直接交给全局异常处理
        },threadPoolExecutor);
        Boolean res = future1.get();//TODO 这么写没意义啊
        if(res==false){
            throw new SignException("600","签到失败");
        }
        /**
         * 签到成功后开始执行加积分和返回当前签到天数的查询
         */
        CompletableFuture<Integer>future2 = CompletableFuture.supplyAsync(()->{
            //任务2:获取当前连续签到天数
            return signService.signData(userid.toString());//返回当前签到天数
        });
//        Integer day = future2.get();
        CompletableFuture<Boolean> future3 = CompletableFuture.supplyAsync(()->{
            //任务3：添加积分的任务交给mq处理
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();;
            String endTime = format.format(date);
            SignDataDTO data= new SignDataDTO();
            data.setEndTime(endTime);
            data.setPointFrom("签到");
            data.setRewardPoint(1);
            data.setUserId(userid);
            try {
                System.out.println(" transMessageSender:-"+transMessageSender);
                transMessageSender.send("exchange.music.sign", "key.sign", data);
//                pointFeign.addPoint(userid,1,endTime,"签到");
                return true;
            } catch (Exception e) {
                throw new SignException("502","签到失败" );
            }
        });
        Map<String,Object>map = new HashMap<>();
        CompletableFuture<Object> objectCompletableFuture = future2.thenCombine(future3, (res1, res2) -> {
            map.put("day", res2);
            //返回当前用户总积分
//  404          pointFeign.getPoint(userid)
            return true;
        });
        //TODO 添加增加积分的业务做异步编排

        map.put("point",objectCompletableFuture.get());
        map.put("code",200 );
        pointFeign.getPoint(userid);
//        map.put("day",day);
        long end = System.currentTimeMillis();
        log.warn("接口响应时间{}",end-start);
        return R.ok().setData("您已经连续签到"+1+"天");
    }


    /**
     * 签到接口
     * @param userid
     * @return
     */
//    @GetMapping("sign")
    public boolean sign(@RequestParam("userid") String userid){
//        DateTimeFormatter.da
        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.get(Calendar.YEAR));
//        System.out.println(calendar.get(Calendar.MONTH));//注意月是从0开始计算的
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//        System.out.println(calendar.get(Calendar.HOUR));
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer year = calendar.get(Calendar.YEAR);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
//        System.out.println(day);
        String key = year+"_"+month+"_"+day+"_"+userid;
        if(redisTemplate.opsForValue().getBit(key, day-1)==true){//当天已经签到过了
            throw  new SignException("501","签到失败");
        }
        redisTemplate.opsForValue().setBit(key,day-1 , true);//这里要注意一下setbit的offset从0开始的,也就是说0表示1号
        return true;
    }
}
