package com.mymusic.musicpoint.controller;

import com.mymusic.musicpoint.service.RewardService;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RewardPointController
 * @Description TODO
 * @Author 86183
 * @Date2022-03-2420:32
 * @Version 1.0
 **/
@RestController
@RequestMapping("point")
public class RewardPointController{

    @Autowired
    private RewardService rewardService;
    @PostMapping("addPoint")
    @Transactional
    public Object addPoint(@RequestParam("userid") Integer userid, @RequestParam("addreward") Integer addreward, @RequestParam("endtime")String endtime,@RequestParam("point_from") String point_from) throws ParseException {
//        System.out.println(endtime);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date parse = sdf.parse(endtime);
        Integer id = rewardService.addRewardPoint(userid, addreward, parse, point_from);
        HashMap<String,Object>map = new HashMap<>();
        if(id==null){
            map.put("code", 501);
            map.put("res",id );
            return map;
        }
        map.put("code", 200);
        map.put("res",id);
        return map;
    }

    @PostMapping("getPoint")
    public Object getPoint(@RequestParam("userid")Integer userid){
        HashMap<String,Object>map = new HashMap<>();
        Integer rewardPointByuserId = rewardService.getRewardPointByuserId(userid);
        map.put("code", 200);
        map.put("res",rewardPointByuserId);
        return map;
    }

    @GetMapping("delete")
    public Object delete(@RequestParam("id")Integer id){
        HashMap<String,Object>map = new HashMap<>();
        Integer rewardPointByuserId = rewardService.delete(id);
        map.put("code", 200);
        map.put("res",rewardPointByuserId);
        return map;
    }
    @Autowired
    RedissonClient redission;
    @SneakyThrows
    @GetMapping("test1")
    public Object test1(@RequestParam("id")String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RLock lock = redission.getLock(id.intern());//这里一定要用intern,不然每个str
                    System.out.println(new Date()+"加锁前"+" "+Thread.currentThread().getId());
                    if(lock.tryLock(4, 10, TimeUnit.SECONDS)){
                        System.out.println(new Date()+" "+"getLock");
                        Thread.sleep(6000);
                        lock.unlock();
                    }
                    System.out.println(new Date()+"加锁后");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return "ok";
    }
}
