package com.mymusic.music.service.impl;

import com.mymusic.music.dao.ConsumerMapper;
import com.mymusic.music.domain.Consumer;
import com.mymusic.music.service.ConsumerService;
import com.mymusic.music.utils.Constant;
import com.mymusic.music.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConsumerImpl
 * @Description TODO
 * @Author 86183
 * @Date2021-07-2914:21
 * @Version 1.0
 **/
@Service
public class ConsumerImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    @Override
    public Boolean addConsumer(Consumer consumer) {
        return consumerMapper.addConsumer(consumer)>0;
    }

    @Override
    public Boolean deleteConsumerById(Integer id) {
        return consumerMapper.deleteConsumerById(id)>0;
    }

    @Override
    public Boolean updateConsumer(Consumer consumer) {
        return consumerMapper.updateConsumer(consumer)>0;
    }

    @Override
    public List<Consumer> getAllConsumer() {
        return consumerMapper.getAllConsumer();
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Consumer selectByPrimaryKey(Integer id) {
        return consumerMapper.selectByPrimaryKey(id);
    }


    /**
     * 查看密码是否正确
     *
     * @param username
     * @param password
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        //验证通过后就设置claims
        Map<String,Object> claims = new HashMap<>();//claim存jwt中的数据
        claims.put(Constant.JWT_USER_NAME, username);//用户名username
        String accessToken = JwtTokenUtil.getAccessToken(username,claims);//获取accessToken
        System.out.println(accessToken);
        return consumerMapper.verifyPassword(username,password)>0;
    }

    /**
     * 根据账号查询
     *
     * @param username
     */
    @Override
    public Consumer getByUsername(String username) {
        return consumerMapper.getByUsername(username);
    }
    @Transactional
    public int addconsumer1(){
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setUsername("root123");
        consumer.setPassword("root123");
//        System.out.println(1/0);
        consumerMapper.addConsumer(consumer);
        return 1;
    }
}
