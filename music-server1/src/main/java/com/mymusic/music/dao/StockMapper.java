package com.mymusic.music.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 管理员Dao
 */
@Repository
public interface StockMapper {
    /**
     *验证密码是否正确
     */
    int decreaseStock(@Param("id") Integer id);

    @Insert("INSERT INTO stock  (good,number)VALUES('good',100)")
    int save();
}
