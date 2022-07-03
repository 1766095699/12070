package com.mymusic.music.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mymusic.music.domain.Admin;
import com.mymusic.music.domain.User;

import java.util.ArrayList;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author 86183
 * @Date2021-08-1320:00
 * @Version 1.0
 **/
public interface UserService{
    /**
     * 验证密码是否正确
     */
    public User userLogin(String username, String password);
    /**
     * 返回好友列表
     */
    public ArrayList<User> getFriends(int id);
    /**
     * 返回尚未接收其信息的好友列表
     */
    public ArrayList<User> getNoReceiveUser(int id);
    /**
     * 返回尚未接收的某一好友的信息的条数
     */
    public int getNoReceiveMessageCounts(int fromid, int toid);
    /**
     * 获取当前用户的是否在线
     */
    public Integer getStatus(int id);

    public User getSingleUser(String username);

    public Integer updateUserStatus(Integer status, Integer id);

    public User getUserById(Integer id);
}
