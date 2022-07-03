package com.mymusic.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymusic.music.dao.AdminMapper;
import com.mymusic.music.domain.Admin;
import com.mymusic.music.service.AdminService;
import com.mymusic.music.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员service实现类
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService{

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证密码是否正确
     *
     * @param username
     * @param password
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username,password)>0;
    }
    @Override
    public String login(String username,String password) {
        //获取登录手机号和密码
        //判断手机号是否正确
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("password",password );
        Admin admin = baseMapper.selectOne(wrapper);
//        System.out.println(username+" "+password);
        System.out.println("admin"+admin);
//        log.info(admin.toString());
//        //判断查询对象是否为空
//        if(mobileMember == null) {//没有这个手机号
//            throw new GuliException(20001,"登录失败");
//        }
//
//        //判断密码
//        //因为存储到数据库密码肯定加密的
//        //把输入的密码进行加密，再和数据库密码进行比较
//        //加密方式 MD5
//        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
//            throw new GuliException(20001,"登录失败");
//        }
//
//        //判断用户是否禁用
//        if(mobileMember.getIsDisabled()) {
//            throw new GuliException(20001,"登录失败");
//        }
//
//        //登录成功
//        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(admin.getId().toString(), admin.getUsername());
        return "222";
    }
}
