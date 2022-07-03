package com.music.musicauth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.musicauth.dao.AdminDao;
import com.music.musicauth.entity.Admin;
import com.music.musicauth.exception.RegistException;
import com.music.musicauth.service.AdminService;
import com.music.musicauth.utils.JwtUtils;
import com.music.musiccommon.utils.Query;
import com.music.musiccommon.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao userDao;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    /**
     * springsecurity开启时使用的登录接口,登录成功返回用户对象
     * @param user
     * @return
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Object login(Admin user) {
        log.info("调用Service层的login方法" );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword() );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println("hello");
        //authenticate为空说明认证失败
        if(Objects.isNull(authenticate)){//认证失败
            throw  new RuntimeException("登录失败");
        }
        Admin admin = (Admin) authenticate.getPrincipal();
        String username = admin.getUsername();
        //获取token
        String token = JwtUtils.getJwtToken(admin.getId().toString(),admin.getUsername());;
        redisTemplate.opsForValue().set("loginUser:"+token,admin , 30000, TimeUnit.SECONDS);
        return R.ok(token);

    }

    @Override
    public Object login1(Admin user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password",user.getPassword());
        Admin admin = userDao.selectOne(queryWrapper);
        if(admin==null){
            R.error(521, "用户登录失败");
        }
        return R.ok(admin);
    }
    /**
     * 登出
     *
     * @return
     */
    @Override
    public Object logout(String token) {
        //获取SpringSecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //zai
        Admin user  = (Admin) authentication.getPrincipal();
        Long id = user.getId();
        //删除Redis中的值
        System.out.println("token==="+token);
        //TODO 删除redis中的值
        redisTemplate.delete("loginUser:"+token);
        return R.ok("注销成功");
    }

    @Override
    @Transactional
    public R regist(Admin admin) {
        Integer res = userDao.insert(admin);
        if(res==1){
            return R.ok("用户注册成功");
        }else{
            throw new RegistException();
        }

    }

}