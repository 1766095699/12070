package com.music.musicauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.musicauth.dao.AdminDao;
import com.music.musicauth.dao.SysMenuDao;
import com.music.musicauth.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-06-100:23
 * @Version 1.0
 **/
@Slf4j
@Service
public class SecurityUserDatilService implements UserDetailsService {

    @Resource
    private AdminDao umsUserDao;

    @Autowired
    private SysMenuDao menuDao;
    /**
     *  只有你走登录请求的时候才会进入这个方法
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取验证码此处可以省略
        Object captcha = request.getSession().getAttribute("captcha");
        System.out.println("captcha = " + captcha);
        //输出用户名
        System.out.println("username = " +username);
        //根据用户名查询用户信息
        QueryWrapper<Admin> umsUserQueryWrapper = new QueryWrapper<>();
        umsUserQueryWrapper.eq("username", username);
        System.out.println(umsUserDao.selectOne(umsUserQueryWrapper));
        //这里的查询出了问题,登录都进不来了
        Admin umsUser = umsUserDao.selectOne(umsUserQueryWrapper);
        //判断用户是否存在
        if (umsUser == null) {
            System.out.println("用户名不对" );
            throw new UsernameNotFoundException("用户名不对");
        }
        //赋予登录权限
        List<String>list = menuDao.selectPermsByUserId(umsUser.getId());
//        list.add("test");
//        list.add("member");
        umsUser.setPermissions(list);
        return umsUser;
    }
}


