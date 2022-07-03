package com.mymusic.music.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @ClassName TokenSetting
 * @Description TODO
 * @Author 86183
 * @Date2021-09-2223:53
 * @Version 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class TokenSetting {
    private  String secretKey;
    private  Duration accessTokenExpireTime;
    private  Duration refreshTokenExpireTime;
    private  Duration refreshTokenExpireAppTime;
    private  String  issuer;

}
