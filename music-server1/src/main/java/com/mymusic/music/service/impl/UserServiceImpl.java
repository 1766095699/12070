package com.mymusic.music.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mymusic.music.dao.UserMapper;
import com.mymusic.music.domain.Admin;
import com.mymusic.music.domain.User;
import com.mymusic.music.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2021-08-1320:01
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    /**
     * 验证密码是否正确
     */
    @Override
    public User userLogin(String username, String password) {
        return userMapper.userLogin(username,password);
    }

    /**
     * 返回好友列表
     */
    @Override
    public ArrayList<User> getFriends(int id) {
        return userMapper.getFriends(id);
    }

    /**
     * 返回尚未接收其信息的好友列表
     */
    @Override
    public ArrayList<User> getNoReceiveUser(int id) {
        return userMapper.getNoReceiveUser(id);
    }

    /**
     * 返回尚未接收的某一好友的信息的条数
     */
    @Override
    public int getNoReceiveMessageCounts(int fromid,int toid) {
        return userMapper.getNoReceiveMessageCounts(fromid,toid);
    }

    /**
     * 获取当前用户的是否在线
     *
     * @param id
     */
    @Override
    public Integer getStatus(int id) {
        return userMapper.getStatus(id);
    }

    @Override
    public User getSingleUser(String username) {
        return userMapper.getSingleUser(username);
    }
    @Override
    public Integer updateUserStatus(Integer status, Integer id){
        return userMapper.updateUserStatus(status,id);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }




}
