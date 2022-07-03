package com.music.musicauth.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1022:02
 * @Version 1.0
 **/
//注意这里没有继承传统的Filter,因为继承Filter的话有时候会出现重复调用的情况。这里用Spring提供的过滤器
    @Component
    @Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //TODO  为了测试方便, 我直接放行了
        filterChain.doFilter(request,response );
        return;
//        log.info("进入JwtAuthenticationTokenFilter" );
//        //获取Token
//        String token = request.getHeader("token");
//        if(StringUtils.isEmpty(token)){
//            //放行 (登录状态由后面的过滤器去做)
//            filterChain.doFilter(request,response );
//            return;
//        }
//        String userId;
//        //解析token
//        try {
//            userId = JwtUtils.getUserId(token);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("token非法");
//        }
//        String redisKey = "loginUser:"+token;
//        String value = (String) redisTemplate.opsForValue().get(redisKey);
//        Admin admin = (Admin) JSON.parseObject(value,Admin.class);//用fastjson把字符串转化成对象
//        log.info("userId:{}",userId);
//        log.info("value={}",value);
//        log.info("缓存反序列的admin={}",admin);
//        if(Objects.isNull(value)){
//            log.info("用户未登录" );
//            throw new RuntimeException("未登录,请先登录");
//        }
//        //存入SecurityContextHolder
//        //TODO 获取权限信息封装到第三个参数
//        //注意,这里的UsernamePasswordAuthenticationToken要用带三个参数的,你点进入看一下三个和两个构造函数的区别就知道了。三个的话里面会设置你已经认证成功了
//        //注意：下面第一个参数好像也可以传对象
//        //第三个参数传入的就是该用户的权限
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(admin,
//                null,admin.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        filterChain.doFilter(request,response );

    }
}
