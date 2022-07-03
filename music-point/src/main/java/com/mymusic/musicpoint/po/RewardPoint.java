package com.mymusic.musicpoint.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName reward_point
 * @Description TODO
 * @Author 86183
 * @Date2022-03-2419:43
 * @Version 1.0
 **/
@Data
@TableName("reward_point")
public class RewardPoint {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer userId;
    Integer rewardPoint;
    Date createdTime;
    Date endTime;
    String pointFrom;
}
