package com.mymusic.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mymusic.music.MusicServer1Application;
import com.mymusic.music.domain.Consumer;
import com.mymusic.music.domain.User;
import com.mymusic.music.exception.AriException;
import com.mymusic.music.feign.MessageFeign;
import com.mymusic.music.service.AdminService;
import com.mymusic.music.service.ConsumerService;
import com.mymusic.music.service.MessageService;
import com.mymusic.music.service.UserService;
import com.mymusic.music.utils.Constant;
import com.mymusic.music.utils.Consts;
import com.mymusic.music.utils.JwtTokenUtil;
import com.mymusic.music.utils.R;
import com.mymusic.music.vo.ResponseUser;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author 86183
 * @Date2021-08-0420:55
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
//@CrossOrigin(origins = "http://localhost:9876/",maxAge = 3600)
public class UserController {
    @Autowired
    ConsumerService consumerService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    public static final Logger LOGGER = LoggerFactory.getLogger(MusicServer1Application.class);
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private MessageFeign messageFeign;

    @GetMapping(value = "getMessage1")
    public String getMessage(){
        String str = messageFeign.getMessage();
        System.out.println(str);
        return str;
    }
    //登录
    @PostMapping("login")
    public R loginUser(HttpServletRequest request) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        System.out.println("username=="+request.getParameter("password"));
        String token = adminService.login(request.getParameter("username"),request.getParameter("password"));
        return R.ok().data("token",token).data("userInfo",userService.getSingleUser(request.getParameter("username"))).data("code",1);
    }
    /**
     * 之前写的关于refresh token 续约的问题
     * @param id
     * @return
     */
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public Object login(HttpServletRequest request){
//        JSONObject jsonObject = new JSONObject();
//        String username = request.getParameter("username").trim();     //账号
//        String password = request.getParameter("password").trim();     //密码
//        if(username==null||username.equals("")){
//            jsonObject.put(Consts.CODE,0);
//            jsonObject.put(Consts.MSG,"用户名不能为空");
//            return jsonObject;
//        }
//        if(password==null||password.equals("")){
//            jsonObject.put(Consts.CODE,0);
//            jsonObject.put(Consts.MSG,"密码不能为空");
//            return jsonObject;
//        }
//        //保存到前端用户的对象中
//        Consumer consumer = new Consumer();
//        consumer.setUsername(username);
//        consumer.setPassword(password);
//        boolean flag = consumerService.verifyPassword(username,password);
//        User sysUser = userService.getSingleUser(username);
//        if(flag){   //验证成功
//            jsonObject.put(Consts.CODE,1);
//            jsonObject.put(Consts.MSG,"登录成功");
//            Map<String,Object> claims = new HashMap<>();//claim存jwt中的数据
//            claims.put(Constant.JWT_USER_NAME, username);//用户名username
//            claims.put(Constant.ROLES_INFOS_KEY,sysUser.getId());//用户id userId
//            claims.put(Constant.PERMISSIONS_INFOS_KEY,"null"); //权限列表
//            String accessToken = JwtTokenUtil.getAccessToken(sysUser.getId().toString(),claims);//生成accessToken
//            String refreshToken = JwtTokenUtil.getRefreshToken(sysUser.getId().toString(),claims);//生成refreshToken
//            System.out.println("ididid="+JwtTokenUtil.getUserId(accessToken));
//            LOGGER.info("accessToken="+accessToken);
//            jsonObject.put("accessToken",accessToken);
//            jsonObject.put("refreshToken", refreshToken);
//            redisTemplate.opsForValue().set("accessToken"+username,accessToken,1, TimeUnit.MINUTES);
//            redisTemplate.opsForValue().set("refreshToken"+username,refreshToken,3,TimeUnit.MINUTES);
//            return jsonObject;
//        }
//        jsonObject.put(Consts.CODE,0);
//        jsonObject.put(Consts.MSG,"用户名或密码错误");
//        return jsonObject;
//    }

    @ApiOperation(value="获取好友列表方法")
    @GetMapping("/friends")
    public Object myFriends(@RequestParam("id") int id){
        ArrayList<User> arrayList = userService.getFriends(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",arrayList);
        return jsonObject;
    }

    @GetMapping("/refreshToken/{username}/{refreshToken}")
    public Object getRefreshToken(@PathVariable("username")String username,@PathVariable("refreshToken")String refreshToken){
        JSONObject jsonObject = new JSONObject();
        Long refreshTokenExpire = redisTemplate.getExpire("refreshToken" + username);
        Object redis_refreshToken = redisTemplate.opsForValue().get("refreshToken"+username);
        System.out.println("refreshToken="+refreshToken);
        System.out.println("redis="+redis_refreshToken);
        //该用户的refreshToken过期或者传进来的这个refreshToken不存在(有可能别人乱写的),就直接返回错误信息
        if(refreshTokenExpire<=0||(redis_refreshToken!=null&&!redis_refreshToken.toString().equals(refreshToken))){
                jsonObject.put("code",0 );
            jsonObject.put("msg","token过期请重新登录");
            return jsonObject;
        }
        System.out.println("accessToken"+username);
        User sysUser = userService.getSingleUser(username);
        Map<String,Object> claims = new HashMap<>();//claim存jwt中的数据
        claims.put(Constant.JWT_USER_NAME, username);//用户名username
        claims.put(Constant.ROLES_INFOS_KEY,sysUser.getId());//用户id userId
        claims.put(Constant.PERMISSIONS_INFOS_KEY,"null"); //权限列表
        String accessToken = JwtTokenUtil.getAccessToken(sysUser.getId().toString(),claims);
        redisTemplate.opsForValue().set("accessToken"+username,accessToken,1,TimeUnit.MINUTES);//存入accessToken到redis
        Long accessTokenExpire  = redisTemplate.getExpire("accessToken"+username);
        System.out.println(accessTokenExpire);
        if(refreshTokenExpire>0&&refreshTokenExpire<2*accessTokenExpire){//refreshToken存活时间<两倍的accessToken存货时间就刷新refreshToken
            refreshToken = JwtTokenUtil.getRefreshToken(sysUser.getId().toString(),claims);//生成refreshToken
            redisTemplate.opsForValue().set("refreshToken"+username,refreshToken,3,TimeUnit.MINUTES);//在redis中刷新refreshToken
        }

        jsonObject.put("refreshToken", refreshToken);
        jsonObject.put("accessToken",accessToken);
        return jsonObject;
    }


    @ApiOperation(value="获取当前用户是否在线")
    @GetMapping("/getStatus")
    public boolean getStatus(@RequestParam("id") int id) {
        return userService.getStatus(id)>0;
    }

    @ApiOperation(value="获取当前用户")
    @GetMapping("/getSingleUser")
    public Object getSingleUser(@RequestParam("nickname") String name) {
        User user = userService.getSingleUser(name);
        JSONObject jsonObject = new JSONObject();
        if(user==null){
            jsonObject.put(Consts.CODE,0 );
            return jsonObject;
        }
        ResponseUser responseUser = new ResponseUser();
        responseUser.setAvatar(user.getAvatar());
        responseUser.setId(user.getId());
        responseUser.setNickname(user.getNickname());
        responseUser.setUsername(user.getUsername());
        System.out.println(user);
        jsonObject.put(Consts.CODE,1 );
        jsonObject.put("data",responseUser);
//        throw new AriException(500,"321313算数");
        return jsonObject;
    }

    @ApiOperation(value="获取当前用户ById")
    @GetMapping("/getUserById")
    public Object getUserById(@RequestParam("id") int id,HttpSession session) {
        User user = userService.getUserById(id);
        System.out.println(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", user);
//        System.out.println("内存+"+session.getAttribute("user"));
//        System.out.println(session);
//        session.setAttribute("user","root" );
//        redisTemplate.opsForValue().set("user",session);
        System.out.println(redisTemplate.opsForValue().get("user"));
        return jsonObject;
    }
    @GetMapping("get")
    public String get(HttpSession session){
        String s = (String) session.getAttribute("msg");
        System.out.println(s);
        return s;
    }
    @GetMapping("set")
    public String set(HttpSession session){
        session.setAttribute("msg","hello");
        System.out.println("get");
            return "ok";
    }

}



