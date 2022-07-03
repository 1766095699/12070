package com.mymusic.musicpoint.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymusic.musicpoint.dao.RewardPointDao;
import com.mymusic.musicpoint.po.RewardPoint;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName RewardServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-03-2420:11
 * @Version 1.0
 **/
@Service
public class RewardServiceImpl extends ServiceImpl<RewardPointDao, RewardPoint> implements RewardService{


    @Override
    public Integer addRewardPoint(Integer userid, Integer addreward, Date endtime, String point_from) {
//        QueryWrapper<RewardPoint>queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id",userid );
        RewardPoint rewardPoint = new RewardPoint();
        rewardPoint.setUserId(userid);
        rewardPoint.setCreatedTime(new Date());
        rewardPoint.setEndTime(endtime);
        rewardPoint.setRewardPoint(addreward);
        rewardPoint.setPointFrom(point_from);
        int insert = baseMapper.insert(rewardPoint);
        if(insert!=0){
            return rewardPoint.getId();
        }
        return null;
    }

    @Override
    public Integer getRewardPointByuserId(Integer userid) {
        QueryWrapper<RewardPoint>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid);
        queryWrapper.ge("end_time",new Date());
        queryWrapper.select("IFNULL(sum(reward_point),0) as rewardPoint");
        Map<String, Object> map = this.getMap(queryWrapper);
        BigDecimal sumCount = (BigDecimal) map.get("rewardPoint");
        System.out.println("sumCount="+sumCount);

        return new Integer(sumCount.toString());
    }

    @Override
    public Integer delete(Integer id) {
        int i = baseMapper.deleteById(id);
        return i;
    }
}
