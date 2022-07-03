package com.music.musicsongs.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.music.musiccommon.utils.R;
import com.music.musicsongs.feign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1510:09
 * @Version 1.0
 **/
@RestController
@RefreshScope
@RequestMapping("songs1")
public class UserController {
    @Value("${myusername}")
    String username;
    @Autowired
    private UserClient userClient;
//    static int cnt = 0;
    @GetMapping("/test11")
    public R test(){
        System.out.println("username=="+username);
        List<Integer>list = new ArrayList<>();
        list.add(1);
        list.add(2);

        R user = userClient.getUser(list);
        if(user.getCode().equals(100)){
            System.out.println("哈哈哈哈哈啊哈哈");
        }
        return user;
    }
    @PostMapping("/test2//{red}/blue/{blue}")
    public R test2(@PathVariable("red")String red,@PathVariable("blue")Integer blue){
        System.out.println("username=="+username);
        List<Integer>list = new ArrayList<>();
        list.add(1);
        list.add(2);
        System.out.println("red="+red);
        System.out.println("blue="+blue);
        return userClient.getUser(list);
    }


    @PostMapping("/api2/test3")
    public R test3(@RequestParam("blue")String blue,@RequestParam("red")Integer red){
        System.out.println("username=="+username);
        List<Integer>list = new ArrayList<>();
        list.add(1);
        list.add(2);
        System.out.println("red="+red);
        System.out.println("blue="+blue);
        return userClient.getUser(list);
    }

    @GetMapping("/api1/test4")
    public R test4() throws InterruptedException {
        System.out.println("username=="+username);
        int sum = 0;
        for(int i=0;i<1000;i++){
//            Thread.sleep(1);
            sum+=i;
        }
        return R.ok(sum);
    }

    @GetMapping(value = "/api2/test5")
    @SentinelResource("request_sentinel3")
    public String requestSentinel3(String header, String body){
        System.out.println(header+" "+body);
        System.out.println("测试Sentinel3");
        return "sentinel3";
    }
}
