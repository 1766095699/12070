package com.mymusic.music.dao;

import com.mymusic.music.domain.Consumer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerMapper {
    public Integer addConsumer(Consumer consumer);

    public Integer deleteConsumerById(Integer id);

    public Integer updateConsumer(Consumer consumer);

    public List<Consumer> getAllConsumer();

    /**
     * 根据主键查询整个对象
     */
    public Consumer selectByPrimaryKey(Integer id);



    /**
     * 验证密码
     */
    public int verifyPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据账号查询
     */
    public Consumer getByUsername(String username);
}
