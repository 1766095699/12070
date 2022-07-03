package com.mymusic.music.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mymusic.music.domain.Order;
import com.mymusic.music.service.OrderService;
import com.mymusic.music.utils.JwtUtils;
import com.mymusic.music.utils.R;
import com.mymusic.myrabbitmq.resend.TransMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@Slf4j
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController{

    @Autowired
    private OrderService orderService;
    @Autowired
    private TransMessageSender transMessageSender;
    @Autowired
    RedissonClient redission;
    //1 生成订单的方法
    @GetMapping("createOrder/{courseId}/{userId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request,@PathVariable String userId) throws InterruptedException {
        RLock lock = redission.getLock(userId.toString().intern());//这里一定要用intern,不然每个string对象地址不同即便值相同
        boolean isLock = lock.tryLock(10,30,TimeUnit.SECONDS);
        if(!isLock){
            log.info("分布式锁正在被占有");
            return R.error().code(110);
        }
        //用redission
        //创建订单，返回订单号
            log.info("获取到了分布式锁:{}",Thread.currentThread().getId() );
            //TODO 查询用户是否已经购买了这首歌,存在则返回创建订单失败
        //TODO (已解决)这里不知道为什么测试一人一单的时候会出现有两个连续的请求接连获取到锁的情况,tryLock不是应该立刻返回吗
            if(orderService.isExistOrder(userId, courseId)){
                log.info("存在重复下单");
                return R.error();
            }
            String orderNo = orderService.createOrders(courseId, userId);//生成订单号
            //TODO 生成的订单号存进redis,key为用户id
            log.info("订单号="+orderNo);
            try{
                transMessageSender.send("exchange.music.order", "key.order", orderNo);//理论上要等消息保存到数据库成功后才能返回才能返回
                if (lock != null && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //TODO这里要靠异步线程去把购买歌曲的具体信息取出来才行
            return R.ok().data("orderId",orderNo);


    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }


}

