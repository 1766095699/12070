package com.mymusic.music.dao;


import com.mymusic.music.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author 86183
 * @Date2021-08-0420:50
 * @Version 1.0
 **/
@Repository
public interface UserMapper  {
    /**
     * 验证密码是否正确
     */
    public User userLogin(@Param("username") String username, @Param("password") String password);
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
    public int getNoReceiveMessageCounts(@Param("fromid") int fromid, @Param("toid") int toid);
    /**
     * 获取当前用户的是否在线
     */
    public Integer getStatus(int id);

    public User getSingleUser(@Param("username") String username);

    public Integer updateUserStatus(@Param("status") Integer status, @Param("id") Integer id);

    public User getUserById(@Param("id") Integer id);
}
