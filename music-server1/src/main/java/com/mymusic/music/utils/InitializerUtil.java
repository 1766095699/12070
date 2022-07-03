package com.mymusic.music.utils;

import org.springframework.stereotype.Component;

/**
 * @ClassName InitializerUtil
 * @Description TODO
 * @Author 86183
 * @Date2021-09-230:02
 * @Version 1.0
 **/
@Component
public class InitializerUtil {
    public InitializerUtil(TokenSetting tokenSetting){
        System.out.println("初始化token配置"+tokenSetting);
        JwtTokenUtil.setJwtProperties(tokenSetting);
    }
}
