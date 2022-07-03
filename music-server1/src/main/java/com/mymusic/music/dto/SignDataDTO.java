package com.mymusic.music.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName SignDataDTO
 * @Description TODO
 * @Author 86183
 * @Date2022-03-259:32
 * @Version 1.0
 **/
@Data
public class SignDataDTO {
    Integer userId;
    Integer rewardPoint;
    String endTime;
    String pointFrom;

}
