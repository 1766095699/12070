package com.mymusic.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.mymusic.music.domain.Consumer;
import com.mymusic.music.domain.Song;
import com.mymusic.music.domain.User;
import com.mymusic.music.service.ConsumerService;
import com.mymusic.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ConsumerController
 * @Description TODO
 * @Author 86183
 * @Date2021-07-2914:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    ConsumerService consumerService;

    @PostMapping("/add")
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username =  request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        Byte sex =Byte.parseByte( request.getParameter("sex").trim());
        System.out.println("123");
        System.out.println(sex);
        System.out.println(request.getParameter("sex"));
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        String avator = "/img/avatorImg/hhh.jpg";
        String birth = request.getParameter("birth").trim();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setAvator(avator);
        consumer.setSex(sex);
        consumer.setBirth(birthDate);
        consumer.setEmail(email);
        consumer.setIntroduction(introduction);
        consumer.setPassword(password);
        consumer.setPhoneNum(phoneNum);
        consumer.setUsername(username);
        consumer.setLocation(location);
        Boolean flag = consumerService.addConsumer(consumer);
        if(flag==true){
            jsonObject.put(Consts.CODE,1 );
            jsonObject.put(Consts.MSG,"添加成功" );
        }else {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"添加失败" );
        }
        return jsonObject;
    }
    @PostMapping("/update")
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Integer id = Integer.parseInt(request.getParameter("id"));
        String username =  request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        Byte sex =Byte.parseByte( request.getParameter("sex").trim());
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location");
        String introduction = request.getParameter("introduction");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setSex(sex);
        consumer.setBirth(birthDate);
        consumer.setEmail(email);
        consumer.setIntroduction(introduction);
        consumer.setPassword(password);
        consumer.setPhoneNum(phoneNum);
        consumer.setUsername(username);
        consumer.setLocation(location);
        Boolean flag = consumerService.updateConsumer(consumer);;
        if(flag==true){
            jsonObject.put(Consts.CODE,1 );
            jsonObject.put(Consts.MSG,"添加成功" );
        }else {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"添加失败" );
        }
        return jsonObject;
    }
    @GetMapping("/delete")
    public Object deleteConsumerById(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Boolean flag = consumerService.deleteConsumerById(Integer.parseInt(request.getParameter("id")));
        if(flag==true){
            jsonObject.put(Consts.CODE,1 );
            jsonObject.put(Consts.MSG,"删除成功" );
        }else {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"删除失败" );
        }
        return jsonObject;
    }

    @GetMapping("/detail")
    public Object getAllData(HttpServletRequest request){
        return consumerService.getAllConsumer();
    }


    /**
     * 更新用户图片
     */
    @RequestMapping(value = "updateConsumerPic",method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        //看看文件是否存在
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败");
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")+"avatorImg";
        //如果文件路径不存在，新增该路径
        File file1  = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/avatorImg/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setAvator(storeAvatorPath);
            consumer.setId(id);
            boolean flag = consumerService.updateConsumer(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avatar",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"修改失败" );
            return jsonObject;
        }
        finally {
            return jsonObject;
        }
    }



    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }
    /**
     * 前端用户登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();     //账号
        String password = request.getParameter("password").trim();     //密码
        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }

        //保存到前端用户的对象中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        boolean flag = consumerService.verifyPassword(username,password);
        if(flag){   //验证成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登录成功");
            jsonObject.put("userMsg",consumerService.getByUsername(username));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名或密码错误");
        return jsonObject;
    }


}
