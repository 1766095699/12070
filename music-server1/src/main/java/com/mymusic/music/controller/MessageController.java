package com.mymusic.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.mymusic.music.domain.IndexListItem;
import com.mymusic.music.domain.Message;
import com.mymusic.music.domain.User;
import com.mymusic.music.service.MessageService;
import com.mymusic.music.service.UserService;
import com.mymusic.music.utils.Consts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@RestController
@RequestMapping("/message")
@Api(tags = "消息接口")
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
//    @Autowired
//    GroupService groupService;
//    @Autowired
//    GroupMessageService groupMessageService;
    @ApiOperation(value="获取所有聊天记录方法")
    @GetMapping("/getAllMessages")
    public ArrayList<Message> getAllMessages(@RequestParam("fromid") int fromid, @RequestParam("toid") int toid){
        ArrayList<Message> arrayList = messageService.getAllMessages(fromid,toid);
        return arrayList;
    }
    @ApiOperation(value="存储聊天记录方法")
    @PostMapping("/saveMessages")
    public Object saveMessages(@RequestBody Message message){
        JSONObject jsonObject = new JSONObject();
        boolean flag1 = messageService.saveMessages(message);
        int toid = message.getToid();
        int fromid = message.getFromid();
        int state = message.getState();
        if(state == 1){
            state = 0;
        }else {
            state = 1;
        }
        Message reverMessage = new Message();
        reverMessage = message;
        reverMessage.setToid(fromid);
        reverMessage.setFromid(toid);
        reverMessage.setState(state);
        boolean flag2 = messageService.saveMessages(reverMessage);
        if(flag1 && flag2){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"保存聊天记录成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"保存聊天记录失败");
        return jsonObject;
    }
    @ApiOperation(value="修改消息接收状态为已接收方法")
    @PostMapping("/updateStatus2Receive")
    public Object updateStatus2Receive(@RequestBody Message message){
        JSONObject jsonObject = new JSONObject();
        boolean flag = messageService.updateStatus(message);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    @GetMapping("/updateContentStatus")
    @ApiOperation(value="修改和这个好友的所有消息接收状态")
    public Object updateContentStatus(@RequestParam("fromid") Integer fromid, @RequestParam("toid") Integer toid){
        JSONObject jsonObject = new JSONObject();
        messageService.updateContentStatus(fromid,toid);//修改状态为已读
        jsonObject.put(Consts.CODE, 1);
        return jsonObject;
    }

    @GetMapping("/getIndexList")
    @ApiOperation(value = "获取主页正在聊天的好友列表")
    public Object getIndexList(@RequestParam("fromid") Integer fromid){
        JSONObject jsonObject = new JSONObject();
        ArrayList<Message> list = messageService.getIndexList(fromid);
        System.out.println(list);
        ArrayList<IndexListItem> indexList = new ArrayList<>();
//        System.out.println(list);
        //添加双人聊天的首页item信息
        for(Message msg:list){
            IndexListItem indexListItem = new IndexListItem();
            indexListItem.setContent(msg.getContent());
            indexListItem.setLastTime(msg.getSendTime());
            User user = ((User) userService.getUserById(msg.getToid()));
            indexListItem.setId(msg.getToid());
            indexListItem.setAvatar(user.getAvatar());
            indexListItem.setNickname(user.getNickname());
            indexListItem.setType(msg.getType());
            indexList.add(indexListItem);
        }
//        System.out.println(fromid);
        System.out.println(indexList);
        //添加群聊的首页item信息
//        ArrayList<GroupMessage> groupContentList= groupMessageService.getIndexGroupList(fromid);//获取当前用户有聊天记录的群聊信息
//////        ArrayList<Group>grouplist = new ArrayList<>();
//////        System.out.println(fromid);
//////        System.out.println(groupContentList);
////        for(GroupMessage groupMessage:groupContentList){
////            Integer groupid = groupMessage.getGroupid();
//////            System.out.println(groupid);
////            Group group = groupService.getGroupById(groupid);
//////            System.out.println(group);
//////            grouplist.add(group);
////            IndexListItem indexListItem = new IndexListItem();
////            indexListItem.setType(1);
////            indexListItem.setId(groupid);
////            indexListItem.setNickname(group.getGroupname());
////            indexListItem.setAvatar(group.getAvatar());
////            indexListItem.setContent(groupMessage.getContent());
////            indexListItem.setLastTime(groupMessage.getSendTime());
////            indexList.add(indexListItem);
////        }
        //以时间为排序字段返回最终的聊天列表
        Collections.sort(indexList, new Comparator<IndexListItem>() {
            @Override
            public int compare(IndexListItem o1, IndexListItem o2) {
                return o2.getLastTime().compareTo(o1.getLastTime());
            }
        });
        jsonObject.put("data", indexList);
//        System.out.println(indexList);
        return jsonObject;
    }

//    @ApiOperation("获取群聊的聊天记录")
//    @GetMapping("/getGroupMessageById")
//    public Object getGroupMessageById(@RequestParam("groupid") Integer groupid, @RequestParam("userid") Integer userid1){
//        JSONObject jsonObject = new JSONObject();
//        ArrayList<GroupMessage> list = groupMessageService.getGroupMessageById(groupid,userid1);
//        for(GroupMessage groupMessage:list){//遍历群长远获取他们的头像
//            Integer userid = groupMessage.getFromid();
//            User user = userService.getUserById(userid);
//            groupMessage.setAvatar(user.getAvatar());
//        }
//        jsonObject.put("data", list);
//        return jsonObject;
//    }
//
//    @ApiOperation("添加群聊记录")
//    @PostMapping("/addGroupMessage")
//    public Object saveGroupMessages(@RequestBody GroupMessage groupMessage){
//        JSONObject jsonObject = new JSONObject();
//        Integer integer = groupMessageService.saveGroupMessages(groupMessage);
//        jsonObject.put("integer",integer);
//        return jsonObject;
//    }

}
