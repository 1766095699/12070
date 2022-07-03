package com.mymusic.music.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName ThreadPoolConfigProperties
 * @Description TODO
 * @Author 86183
 * @Date2022-02-2822:36
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "gulimall.thread")
//@Component
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;
}
