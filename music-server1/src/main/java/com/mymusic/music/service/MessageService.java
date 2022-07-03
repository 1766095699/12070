package com.mymusic.music.service;


import com.mymusic.music.domain.Message;

import java.util.ArrayList;

/**
 * 聊天记录service层
 */
public interface MessageService {
    /**
     * 获取聊天记录
     */
    public ArrayList<Message> getAllMessages(int fromid, int toid);
    /**
     * 存储聊天记录
     */
    public boolean saveMessages(Message message);
    /**
     * 获取最后一条聊天记录
     */
    public Message getLastMessage(int fromid, int toid);
    /**
     * 修改消息接收状态
     */
    public boolean updateStatus(Message message);

    public boolean updateContentStatus(Integer fromid, Integer toid);

    public ArrayList<Message> getIndexList(Integer fromid);
}
