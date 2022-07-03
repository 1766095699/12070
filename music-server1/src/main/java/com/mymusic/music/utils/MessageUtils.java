package com.mymusic.music.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymusic.music.domain.ResultMessage;


/**
 * @ClassName MessageUtils
 * @Description 用来封装消息的工具类
 * @Author 86183
 * @Date2021-08-0419:40
 * @Version 1.0
 **/

public class MessageUtils {
    public static String getMessage(boolean isSystemMessage, String fromName, String toName, Object message){
        try {
            ResultMessage result = new ResultMessage();
            result.setSystem(isSystemMessage);
            result.setMessage(message);
            if(fromName != null){ //如果传的是系统消息的话fromName传的就是null
                result.setFromName(fromName);
            }
            if(toName!=null){
                result.setToName(toName);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);//把result转化成json字符串的格式
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
