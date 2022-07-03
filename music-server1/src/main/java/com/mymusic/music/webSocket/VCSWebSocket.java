package com.mymusic.music.webSocket;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Component
@ServerEndpoint("/VCSWebsocket/{vcsId}/{staffName}/{username}")
public class VCSWebSocket {
    //concurrent包的线程安全Map，用来存放每个客户端对应的LoginWebsocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String,VCSWebSocket> webSocketMap = new ConcurrentHashMap<String, VCSWebSocket>();
    //会议用户列表
    private static ConcurrentHashMap<String,HashMap<String,String>> VCSList = new ConcurrentHashMap<String, HashMap<String,String>>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "vcsId")String vcsId , @PathParam(value = "staffName") String staffName, @PathParam(value = "username") String username, Session session){
            System.out.println(new Date() + "有信息视频通话建立" + vcsId + " 用户名" + staffName + " 账号" +username);
            //将该用户连接信息存入
        this.session = session;
            webSocketMap.put(username, this);
        //与服务器建立连接后，获取相关集合包，
        if(VCSList.containsKey(vcsId)){//已经有通话信息就创建广播有人加入了，
            // 这个代码块可以不管-----
            HashMap<String,String> list=VCSList.get(vcsId);
            JSONObject message=new JSONObject();
            message.put("type","text");
            message.put("text",staffName+"加入会议");
            JSONObject message2=new JSONObject();
            message2.put("type","login");
            message2.put("username",username);
            message2.put("staffName",staffName);
            //根据集合包发送自己参加会议的消息
            for(String joiner:list.keySet()){
                try {
                    System.out.println("message:"+message);
                    System.out.println("message2:"+message);
                    webSocketMap.get(joiner).sendMessage(message.toString());
                    webSocketMap.get(joiner).sendMessage(message2.toString());
                    //给客户端发送之前已经参加会议的人的集合包
                    JSONObject message3=new JSONObject();
                    message3.put("type","login");
                    message3.put("username",joiner);
                    message3.put("staffName",list.get(joiner));
                    System.out.println("message3"+message3);
                    this.sendMessage(message3.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            list.put(username,staffName);
        }
            else{
            //这个代码块是核心，添加会议实例和用户实例
            System.out.println(new Date() +" "+ staffName +"开启视频通话"+vcsId);
            HashMap<String,String> list=new HashMap<String ,String>();
            //TODO 后期进行群视频的拓展
            list.put(username,staffName);//添加用户名
            VCSList.put(vcsId,list);//记录参与该视频通话群的所有人
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "vcsId")String vcsId , @PathParam(value = "staffName") String staffName, @PathParam(value = "username") String username){
        HashMap<String,String> list=VCSList.get(vcsId);
        JSONObject message=new JSONObject();
        message.put("type","text");
        message.put("text",staffName+"离开视频通话");
        System.out.println(staffName+"离开视频通话");
        JSONObject message2=new JSONObject();
        message2.put("type","unlogin");//退出登录
        message2.put("username",username);
        list.remove(username);
        webSocketMap.remove(username);
        //根据集合包发送自己离开会议的消息
        for(String joiner:list.keySet()){
            try {
                webSocketMap.get(joiner).sendMessage(message.toString());
                webSocketMap.get(joiner).sendMessage(message2.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(new Date() +" "+ staffName +"离开视频通话 视频通话ID："+vcsId);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam(value = "vcsId")String vcsId , @PathParam(value = "staffName") String staffName, @PathParam(value = "username") String username) {
        //首先对消息进行解析，判断消息的类型
        JSONObject data=JSONObject.fromObject(message);
        System.out.println("流信息"+message);
        if(data.getString("type").equals("offer") || data.getString("type").equals("answer")||data.getString("type").equals("icecandidate")){
            //信令服务器作为连接时候的中转站发送消息
            try {
                webSocketMap.get(data.getString("send")).sendMessage(data.toString());//发送数据
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(@PathParam(value = "staffName") String staffName, Session session, Throwable error){
        System.out.println(new Date() +" " +staffName +"发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

}