package com.mymusic.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mymusic.music.domain.Admin;

/**
 * 管理员service接口
 */
public interface AdminService extends IService<Admin> {

    /**
     *验证密码是否正确
     */
    public boolean verifyPassword(String username,String password);

    public String login(String username,String password);
}
