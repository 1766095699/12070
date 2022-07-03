package com.mymusic.music.service;

import com.mymusic.music.domain.Consumer;

import java.util.List;

/**
 * @ClassName ConsumerService
 * @Description TODO
 * @Author 86183
 * @Date2021-07-2914:19
 * @Version 1.0
 **/
public interface ConsumerService {
    public Boolean addConsumer(Consumer consumer);

    public Boolean deleteConsumerById(Integer id);

    public Boolean updateConsumer(Consumer consumer);

    public List<Consumer>getAllConsumer();

    /**
     * 根据主键查询整个对象
     */
    public Consumer selectByPrimaryKey(Integer id);



    /**
     * 查看密码是否正确
     */
    public boolean verifyPassword(String username,String password);

    /**
     * 根据账号查询
     */
    public Consumer getByUsername(String username);
    public int addconsumer1();

}
