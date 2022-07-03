package com.mymusic.music.controller;
import com.mymusic.music.service.PayLogService;
import com.mymusic.music.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createNatvie(orderNo);
        System.out.println("****返回二维码map集合:"+map);

        return R.ok().data(map);
    }

    //查询订单支付状态
    //参数：订单号，根据订单号查询 支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //TODO 用redis存订单编号和开始时间,判断订单是否超时,如果超时则直接返回错误(生成微信二维码的时候也可以设置超时时间相关的参数)
        //TODO 用线程池来做异步编排,在支付成功后修改订单状态的同时,把这首歌的购买记录加到加到用户已购买的歌曲表中
        /**
         * TODO 支付成功后加上要用消息队列消费如下的消息：
         * ①歌曲销量+1
         * ②增加用户积分
         * ③将
         */
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合:"+map);
        if(map == null) {
            return R.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")) {//支付成功
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);

            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

