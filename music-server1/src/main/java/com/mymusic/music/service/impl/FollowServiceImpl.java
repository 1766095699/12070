package com.mymusic.music.service.impl;

import com.mymusic.music.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @ClassName FollowServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-03-171:00
 * @Version 1.0
 **/
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Set commonFollowList(Integer userid1, Integer userid2) {
        Set intersect = redisTemplate.opsForSet().intersect(userid1.toString(), userid2.toString());//key要传入字符串才行不能传入Integer
        return intersect;
    }
}
