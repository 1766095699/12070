package com.mymusic.music.controller;

import com.alibaba.fastjson.JSONObject;

import com.mymusic.music.domain.Message;
import com.mymusic.music.domain.MyFile;
import com.mymusic.music.domain.User;
import com.mymusic.music.service.FilePathService;
import com.mymusic.music.service.MessageService;
import com.mymusic.music.service.UserService;
import com.mymusic.music.utils.Consts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileController
 * @Description TODO
 * @Author 86183
 * @Date2021-11-2815:37
 * @Version 1.0
 **/
//@RequestMapping("/file")
@RestController
@Api("上传文件")
public class FileController {
    @Autowired
    FilePathService filePathService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @ApiOperation("上传图片")
    @RequestMapping(value = "/uploadPic",method = RequestMethod.POST)
    public Object uploadPic(@RequestParam("file") MultipartFile avatorFile,@RequestParam("type")Integer type,@RequestParam("fromid")Integer fromid,@RequestParam("toid")Integer toid){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"imgfiles"+System.getProperty("file.separator");
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/imgfiles/"+fileName;
        try {
            boolean flag = false;
            avatorFile.transferTo(dest);
            System.out.println("文件上传");
            if(type==1){//1表示传的是图片
                //保存这条发送图片的聊天记录到数据库(这里没做事务处理)
                Message message = new Message();
                message.setFromid(fromid);
                message.setToid(toid);
                message.setType(1);
                message.setState(0);
                message.setStatus(false);
                message.setContent(storeAvatorPath);
                flag = messageService.saveMessages(message);
            }
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }


    @ApiOperation("上传文件")
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public Object uploadFile(@RequestParam("file") MultipartFile avatorFile,@RequestParam("type")Integer type,@RequestParam("fromid")Integer fromid,@RequestParam("toid")Integer toid){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+"-"+avatorFile.getOriginalFilename();
        User user = userService.getUserById(fromid)
                ;
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"uploadFiles"
                +System.getProperty("file.separator")+user.getId()+"-"+user.getUsername()+System.getProperty("file.separator");
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/uploadFiles/"+user.getId()+"-"+user.getUsername()+"/"+fileName;
        boolean flag1 = false;
        try {
            boolean flag = false;
            avatorFile.transferTo(dest);
            System.out.println("文件上传");
            if(type==2){//1表示传的是文件
                //保存这条发送图片的聊天记录到数据库(这里没做事务处理)
                Message message = new Message();
                message.setFromid(fromid);
                message.setToid(toid);
                message.setType(2);
                message.setState(0);
                message.setStatus(false);
                message.setContent(storeAvatorPath);
                flag = messageService.saveMessages(message);//存聊天记录
                MyFile myFile = new MyFile();
                myFile.setFilename(avatorFile.getOriginalFilename());
                myFile.setSize(new Long(avatorFile.getSize()).toString());
                myFile.setFilepath(storeAvatorPath);//存相对路径
                System.out.println(myFile);
                flag1 = filePathService.insertFilePath(myFile)>0;
                System.out.println("插入成功");
            }
            if(flag&&flag1){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

}
