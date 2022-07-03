package com.mymusic.music.webSocket;//package com.example.socket.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymusic.music.domain.*;
import com.mymusic.music.service.AdminService;
import com.mymusic.music.service.UserService;
import com.mymusic.music.utils.GetUserDataUtil;
import com.mymusic.music.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ChatEndPoint
 * @Description TODO
 * @Author 86183
 * @Date2021-08-052:31
 * @Version 1.0
 **/
//到时候肯定是要初始化的时候从数据库获取聊天记录数据赋值给talkRecords
@ServerEndpoint(value = "/chat/{username}",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    @Autowired
    UserService userService;
    //用来存储每个客户端对象对应的ChatEndPoint对象
    //因为会创建很多ChatEndPoint对象,所以onlineUsers要设置为静态的,所有用户共享
    private  static Map<Integer,ChatEndpoint> onlineUsers= new ConcurrentHashMap<>();//ConcurrentHashMap涉及到并发编程,线程安全
    private  static Map<Integer,ChatEndpoint> allOnlineUsers= new ConcurrentHashMap<>();//ConcurrentHashMap涉及到并发编程,线程安全
    //声明session对象,通过该对象可以发送消息给指定用户
    private Session session;//这个Session 来自javax.websocket.Session;

    //声明一个HttpSession对象,我们之前在HttpSession中存储了用户名
    private HttpSession httpSession;

    //用户名
    private String username;

    //存放username映射到各个toName的聊天记录,talk["username"]["toName"]表示username和toName之间的聊天记录
    Map<String,Map<String, SingleMessage>> talkRecords = new HashMap();

    User nowUser = new User();//当前登录的用户

    //调用serviec层方法的工具类
    GetUserDataUtil getUserDataUtil = new GetUserDataUtil();
    //给连接建时被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("username") String username){
        System.out.println("------------------Open----------------");
        //把局部变量参数session赋值到全局session
        this.session = session;
        //获取httpSession对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //获取当前登录上线的这个人的用户名(这个Data.username在LoginController已经赋值了,但是这里明显线程不安全)
        username = new String(username);
        System.out.println(username);
        getUserDataUtil = new GetUserDataUtil();
        User singleUser = getUserDataUtil.getSingleUser(username);
        nowUser = singleUser;
        System.out.println(singleUser);
//        onlineUsers.put(username, this);//this表示当前对象(用户的username独一无二,每个登录的用户都会创建一个EndPoint对象)
//        onlineUsers.put(singleUser.getId(),this);
        allOnlineUsers.put(singleUser.getId(),this);
        System.out.println("-------当前集合里的用户--------");
        for(Map.Entry<Integer, ChatEndpoint> user:allOnlineUsers.entrySet()) {
            System.out.println(user.getKey());
        }
        System.out.println("---------------");
        System.out.println("用户已上线:"+singleUser.getUsername());
        getUserDataUtil.updateUserStatus(1,singleUser.getId());//将用户状态改为上线

        //将当前在线用户的用户名推送给所有客户端
        //1.获取消息(一定要符合系统消息的格式)
        broadcastAllUsers(MessageUtils.getMessage(true,null ,null,getNames() ));
    }

    private Set<Integer> getNames(){
        return allOnlineUsers.keySet();//拿到所有的key,也就是User对象(现在默认所有用户互相为好友),现在存在里面的都是已经上线的
    }

    //TODO 后面广播的时候要加上判断是否是好友
    private void broadcastAllUsers(String message){
//        System.out.println("---------------");
//        for(Map.Entry<User,ChatEndpoint>user:onlineUsers.entrySet()) {
//            System.out.println(user.getKey().getUsername());
//        }
//        System.out.println("---------------");
//        try {
//            //要将该消息推送给所有客户端
//            Set<User>names = onlineUsers.keySet();//拿到所有的key
//            for(User name:names){
//                ChatEndpoint chatEndpoint =  onlineUsers.get(name);//遍历在线的所有人,然后发送消息给他们
//                chatEndpoint.session.getBasicRemote().sendText(message);//调用这个方法发送信息给这个chatEndpoint对象对应的客户端，message是信息对象数据对应的json字符串
//            }
//        }catch (Exception e){
//            //从容器中删除指定用户
//            onlineUsers.remove(nowUser);
//            String message1 = MessageUtils.getMessage(true, null, null,getNames());
//            broadcastAllUsers(message1);
//            e.printStackTrace();
//        }
    }

    //接收到客户端发送的数据的时候被调用
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println(message);
//        System.out.println(message.charAt());
        if(message.equals("ping")){
            try {
                this.allOnlineUsers.get(7).session.getBasicRemote().sendText("pong");//向接收方发送消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message1 msg1 = mapper.readValue(message, Message1.class);//前端传来的是一个json对象,这里应该是自动把json对象的元素他赋值给Message类
            System.out.println(msg1);
            System.out.println(msg1.getType());

            Message msg = new Message();
            msg.setFromid(msg1.getFromid());
            msg.setToid(msg1.getToid());
            msg.setContent(msg1.getContent());
            msg.setContentType(msg1.getContentType());
            if(msg.getContentType()==1||msg.getContentType()==2){//给对方发送图片
                Map<String,Object> map1 = new HashMap<>();
                map1.put("type",1);//群聊聊天
                map1.put("toUserId", msg1.getToid());
                String resultMessage1 = MessageUtils.getMessage(false,"1" ,"1",map1);
                if(allOnlineUsers.containsKey(msg.getFromid())){
                    allOnlineUsers.get(msg.getFromid()).session.getBasicRemote().sendText(resultMessage1);//向接收方发送消息
                }
                System.out.println(resultMessage1);
                if(allOnlineUsers.containsKey(msg.getToid())){
                    allOnlineUsers.get(msg.getToid()).session.getBasicRemote().sendText(resultMessage1);//向发送方发送消息
                }
                return ;
            }
            System.out.println(msg1.getType());
            //上面的代码再处理存料
            //获取消息接收方的用户名
            Integer toId = msg.getToid();
//            msg.setFromName(username);
            System.out.println("接收方"+toId);
            System.out.println("发送方"+msg.getFromid());
            //获取用户发送的信息
            String data = msg.getContent();
            System.out.println("发送的内容："+data);
            msg.setType(0);//0表示是文本信息
            msg.setState(0);//0表示发送者是自己
            getUserDataUtil.saveMessages(msg);//添加信息到聊天记录
            Map<String,Object> map1 = new HashMap<>();
            map1.put("type",0);//单人聊天
            map1.put("toUserId", msg.getToid());
            Map<String,Object> map2 = new HashMap<>();
            map2.put("type",0);//单人聊太难
            map2.put("toUserId", msg.getFromid());
            //获取推送给指定用户的消息格式的模板 
            //发送给发送方的数据
            String resultMessage1 = MessageUtils.getMessage(false,getUserDataUtil.getUserById(msg.getFromid()).getUsername() ,getUserDataUtil.getUserById(msg.getToid()).getUsername(),map1);
            //发送给接收方的数据(对于接收方来说toName就是发送方)
            String resultMessage2 = MessageUtils.getMessage(false,getUserDataUtil.getUserById(msg.getToid()).getUsername() ,getUserDataUtil.getUserById(msg.getFromid()).getUsername(),map2);
//            发送数据
//            找到这两个名字的用户对应的User类
//            System.out.println("-----------------------");
//            System.out.println(resultMessage1);
//            System.out.println(resultMessage2);
//            System.out.println(getUserDataUtil.getUserById(msg.getFromid()).getUsername());
//            System.out.println(getUserDataUtil.getUserById(msg.getToid()).getUsername());
            //(发送方和接收方都要发送这条消息,所以同时sendText)
            /**
             * 在用户未上线的状态下, 广播信息只发送给发送方
             */
            Integer status = getUserDataUtil.getUserStatus(toId);//判断对方是否在线
            System.out.println(getUserDataUtil.getUserById(msg.getToid()).getUsername()+"状态是："+status);
            if(status==0){//未上线状态
                System.out.println("用户未上线");
                allOnlineUsers.get(msg.getFromid()).session.getBasicRemote().sendText(resultMessage1);//向接收方发送消息
                return ;
            }
            if(allOnlineUsers.containsKey(msg.getFromid())){
//                System.out.println(msg.getFromid()+" "+allOnlineUsers.get(msg.getFromid()));
                allOnlineUsers.get(msg.getFromid()).session.getBasicRemote().sendText(resultMessage1);//向接收方发送消息
            }
            System.out.println("-------当前集合里的用户--------");
            for(Map.Entry<Integer, ChatEndpoint> user:allOnlineUsers.entrySet()) {
                System.out.println(user.getKey());
            }
            System.out.println(msg.getFromid());
            System.out.println(msg.getToid());
            System.out.println(resultMessage1);
            if(allOnlineUsers.containsKey(msg.getToid())){
//                System.out.println(msg.getFromid()+" "+onlineUsers.get(msg.getToid()));
                allOnlineUsers.get(msg.getToid()).session.getBasicRemote().sendText(resultMessage2);//向发送方发送消息
            }
            System.out.println("当前在容器中的id");
            for(Map.Entry<Integer,ChatEndpoint>id:allOnlineUsers.entrySet()) {
                System.out.println(id);
        }
        } catch (Exception e) {
            //从容器中删除指定用户
            allOnlineUsers.remove(nowUser.getId());
            getUserDataUtil.updateUserStatus(0, nowUser.getId());
//            String message1 = MessageUtils.getMessage(true, null,null, getNames());
//            broadcastAllUsers(message1);
//            e.printStackTrace();
        }
    }

    //连接关闭时调用
    @OnClose
    public void OnClose(Session session){
        //从容器中删除指定用户
        System.out.println("调用了onClose");
        allOnlineUsers.remove(nowUser.getId());
        getUserDataUtil.updateUserStatus(0, nowUser.getId());
        System.out.println("-------当前集合里的用户--------");
        for(Map.Entry<Integer, ChatEndpoint> user:allOnlineUsers.entrySet()) {
            System.out.println(user.getKey());
        }
        System.out.println("---------------");
//        onlineUsers.remove(nowUser);
//        String message = MessageUtils.getMessage(true, null,null, getNames());
//        broadcastAllUsers(message);
    }

//    @OnError
//    public void OnError(){
//        System.out.println("调用了onError");
//        getUserDataUtil.updateUserStatus(0, nowUser.getId());
//        //从容器中删除指定用户
//        allOnlineUsers.remove(nowUser.get);
////        String message1 = MessageUtils.getMessage(true, null, getNames());
////        broadcastAllUsers(message1);
//    }
}
