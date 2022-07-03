package com.mymusic.music.service.impl;

import com.mymusic.music.dao.FileMapper;
import com.mymusic.music.dao.MessageMapper;
import com.mymusic.music.domain.Message;
import com.mymusic.music.domain.MyFile;
import com.mymusic.music.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    FileMapper fileMapper;
    /**
     * 获取聊天记录
     *
     * @param fromid
     * @param toid
     */
    @Override
    public ArrayList<Message> getAllMessages(int fromid, int toid) {
        ArrayList<Message> arrayList = messageMapper.getAllMessages(fromid,toid);
        Collections.sort(arrayList, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getSendTime().compareTo(o2.getSendTime());
            }
        });
        for(Message message:arrayList){
            Integer type = message.getType();
            if(type==2){
                System.out.println(message.getContent());
                MyFile file = fileMapper.getFile(message.getContent());//对于文件类型,content中装的是文件的相对路径
                message.setMyFile(file);
                System.out.println(file);
            }
        }
        return arrayList;
    }

    /**
     * 存储聊天记录
     *
     * @param message
     */
    @Override
    public boolean saveMessages(Message message) {
        boolean flag1=false;
        boolean flag2=false;
        flag1 = messageMapper.saveMessages(message)>0;
        Integer fromid = message.getFromid();
        Message message1 = new Message();
        message1.setState(message.getState());
        message1.setFromid(message.getToid());
        message1.setToid(message.getFromid());
        message1.setState(1);//1表示这条信息中fromid是接收者
        message1.setContent(message.getContent());
        message1.setStatus(message.getStatus());
        message1.setSendTime(message.getSendTime());
        message1.setType(message.getType());
        flag2 = messageMapper.saveMessages(message1)>0;
        return flag1&&flag2;
    }

    /**
     * 获取最后一条聊天记录
     */
    @Override
    public Message getLastMessage(int fromid,int toid) {
        return messageMapper.getLastMessage(fromid,toid);
    }

    /**
     * 修改消息接收状态
     *
     * @param message
     */
    @Override
    public boolean updateStatus(Message message) {
        return messageMapper.updateStatus(message)>0;
    }

    public boolean updateContentStatus(Integer fromid, Integer toid){
        return messageMapper.updateContentStatus(fromid,toid)>=0;
    }

    @Override
    public ArrayList<Message> getIndexList(Integer fromid) {
        return messageMapper.getIndexList(fromid);
    }
}
