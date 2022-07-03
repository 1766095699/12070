package com.mymusic.musicpoint.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mymusic.musicpoint.po.RewardPoint;

import java.util.Date;

public interface RewardService extends IService<RewardPoint> {
    public Integer addRewardPoint(Integer userid, Integer addreward, Date endtime, String point_from);

    public Integer getRewardPointByuserId(Integer userid);

    Integer delete(Integer id);
}
