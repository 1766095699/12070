package com.mymusic.music.utils;


import com.mymusic.music.domain.Admin;
import com.mymusic.music.domain.Message;
import com.mymusic.music.domain.User;
import com.mymusic.music.service.MessageService;
import com.mymusic.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName getUserDataUtil
 * @Description TODO
 * @Author 86183
 * @Date2021-08-1320:56
 * @Version 1.0
 **/
@Component
public class GetUserDataUtil {
    @Autowired
    protected UserService userService;
    @Autowired
    protected MessageService messageService;
//    @Autowired
//    protected GroupMemberService groupMemberService;
//    @Autowired
//    protected GroupMessageService groupMessageService;
    private static GetUserDataUtil getUserDataUtil;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        getUserDataUtil = this;
        getUserDataUtil.userService = userService;
        getUserDataUtil.messageService = messageService;
        // 初使化时将已静态化的testService实例化
    }

    /**
     * 查询单个用户的详细信息
     * @param username
     * @return
     */
    public User getSingleUser(String username){
        return getUserDataUtil.userService.getSingleUser(username);
    }

    public List<User> getFriends(Integer id){
        return getUserDataUtil.userService.getFriends(id);
    }

    public Integer updateUserStatus(Integer status, Integer id){
        return getUserDataUtil.userService.updateUserStatus(status,id );
    }

    public Integer getUserStatus(Integer id){
        return getUserDataUtil.userService.getStatus(id);
    }

    public boolean saveMessages(Message message){
        return getUserDataUtil.messageService.saveMessages(message);
    }

    public User getUserById(Integer id){
        return getUserDataUtil.userService.getUserById(id);
    }
    public ArrayList<Message>getAllMessages(Integer fromid, Integer toid){
        return getUserDataUtil.messageService.getAllMessages(fromid,toid );
    }

//    public ArrayList<Integer> getGroupMemberId(Integer groupid){
//        return getUserDataUtil.groupMemberService.getGroupMember(groupid);
//    }
//
//    public Integer saveGroupMessages(GroupMessage groupMessage){
//        return getUserDataUtil.groupMessageService.saveGroupMessages(groupMessage);
//    }
}
