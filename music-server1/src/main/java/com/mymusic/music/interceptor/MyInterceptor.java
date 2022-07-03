package com.mymusic.music.interceptor;

import com.mymusic.music.MusicServer1Application;
import com.mymusic.music.MusicServer1Application;
import com.mymusic.music.domain.Collect;
import com.mymusic.music.utils.JwtTokenUtil;
import com.mymusic.music.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyInterceptor
 * @Description TODO
 * @Author 86183
 * @Date2022-01-0710:42
 * @Version 1.0
 **/
public class MyInterceptor implements HandlerInterceptor {
    ThreadLocal<String>threadLocal = new ThreadLocal<>();
    public static final Logger LOGGER = LoggerFactory.getLogger(MusicServer1Application.class);
    @Autowired
    RedisTemplate redisTemplate;//一定要自己重新注入RedisTemplate且修改它的序列化方式才行。不然会出问题
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        String myToken = request.getParameter("songListId");
//        request.ip
        //TODO 这里很奇怪,有时候能拿到header中的token,有时候拿不到。我怀疑是前端代码或者网络的问题？？
        String myToken = request.getHeader("token");
        System.out.println("myToken1 = "+"="+myToken);
        if(myToken==null){
            response.getWriter().write("222");
            return false;
        }
        if(myToken!=null){
            if(!JwtUtils.checkToken(myToken)){
                return false;
            }
            System.out.println("myToken="+myToken);
            threadLocal.set(myToken);//用threadLocal来存当前用户的token
        }
//        try {
//            LOGGER.info("进入拦截器preHandle");
//            String username = request.getParameter("username");
//            String token = request.getParameter("accessToken");
//            System.out.println(username);
//            System.out.println(token);
////            Object refreshToken = redisTemplate.opsForValue().get("refreshToken" + username);
//            if(username==null||token==null)return false;
//            Object redis_token = redisTemplate.opsForValue().get("accessToken"+username);//判断是否过期
//            System.out.println("token的剩余时间:"+redisTemplate.getExpire("accessToken"+username));
//            if(redisTemplate.getExpire("accessToken"+username)>0&&token.equals(redis_token)){//accessToken没过期
//                System.out.println("放行成功");
//                return true;
//            }
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write("您没有访问权限");//这里就应该让它
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("进入拦截器postHandler");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("进入afterCompletion");
//        System.out.println("nickname="+JwtUtils.getUsername(threadLocal.get()));//当请求接口结束的时候,把用户信息返回给用户
//        response.setHeader("nickname", JwtUtils.getUsername(threadLocal.get()));
//        response.getLocale();
        response.addCookie(new Cookie("nickname", threadLocal.get()));
    }


}
