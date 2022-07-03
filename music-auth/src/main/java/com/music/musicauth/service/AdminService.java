package com.music.musicauth.service;

import com.music.musicauth.entity.Admin;
import com.music.musiccommon.utils.R;

public interface AdminService {
    /**
     * 登录接口
     * @param user
     * @return
     */
    public Object login(Admin user);

    public Object login1(Admin user);

    /**
     * 登出
     * @return
     */
    public Object logout(String token);

    R regist(Admin admin);

}

