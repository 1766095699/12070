package com.mymusic.music.dao;

import com.mymusic.music.domain.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MessageMapper {
    /**
     * 获取聊天记录
     */
    public ArrayList<Message> getAllMessages(@Param("fromid") Integer fromid, @Param("toid") Integer toid);
    /**
     * 存储聊天记录
     */
    public int saveMessages(Message message);
    /**
     * 获取最后一条聊天记录
     */
    public Message getLastMessage(@Param("fromid") int fromid, @Param("toid") int toid);
    /**
     * 修改消息接收状态
     */
    public int updateStatus(Message message);

    public Integer updateContentStatus(@Param("fromid") Integer fromid, @Param("toid") Integer toid);

    /**
     * 获取主页正在聊天的列表记录
     */
    public ArrayList<Message> getIndexList(@Param("fromid") Integer fromid);
}
