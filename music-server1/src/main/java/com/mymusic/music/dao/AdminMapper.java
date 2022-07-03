package com.mymusic.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymusic.music.domain.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 管理员Dao
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     *验证密码是否正确
     */
    public int verifyPassword(@Param("username") String username, @Param("password") String password);
}
