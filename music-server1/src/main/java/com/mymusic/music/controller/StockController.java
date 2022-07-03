package com.mymusic.music.controller;

import com.mymusic.music.dao.StockMapper;
import com.mymusic.music.service.impl.StockServiceImpl;
import io.netty.handler.codec.json.JsonObjectDecoder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StockController
 * @Description TODO
 * @Author 86183
 * @Date2022-01-0122:48
 * @Version 1.0
 **/
@RestController
public class StockController {
    @Autowired
    StockServiceImpl stockService;
    @RequestMapping("/stock/decrease")
    public Object decrease(@RequestParam("id")Integer id){
        int i = (int) stockService.decreaseStock(id);
        JSONObject jsonObject = new JSONObject();
        if(i==2)
            jsonObject.put("msg","减库存成功" );
        else
            jsonObject.put("msg","减库存失败" );
        return jsonObject;
    }
    @RequestMapping("/stock/decrease1")
    public Object decrease1(@RequestParam("id")String id){
        System.out.println(id);
        int i = 2;
        JSONObject jsonObject = new JSONObject();
        if(i==2)
            jsonObject.put("msg","减库存成功" );
        else
            jsonObject.put("msg","减库存失败" );
        return jsonObject;
    }

}
