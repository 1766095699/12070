package com.mymusic.music.controller;

import com.mymusic.music.common.R;
import com.mymusic.myrabbitmq.resend.TransMessageSender;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1116:14
 * @Version 1.0
 **/
@RestController
@RequestMapping("/ordertest")
public class OrderControllerTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TransMessageSender transMessageSender;
    @GetMapping("topic")
    public String topic(@RequestParam("key")String key){
        transMessageSender.send("exchangeTopic", key, "hello");
        return "ok";
    }
    @GetMapping("pay")
    public Object Pay(){
        String userid = "root_1";
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //用lua脚本做原子验证令牌和删除令牌,防止重复下订单
        Long res = (Long) redisTemplate.execute(new DefaultRedisScript<Long>(script,Long.class), Arrays.asList(userid), "123456");
        System.out.println(res);
        return R.ok();
    }
}
