package com.mymusic.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位各种文件或头像地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //别忘了还默认带上配置文件中的server1,我这边做了一个比较巧妙的映射
        registry.addResourceHandler("/avatar/**").addResourceLocations("classpath:/avatar/");
//        //歌手头像地址
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1"+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator")
        );
        //歌单图片地址
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\img"
                        +System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator")
        );
        //歌曲图片地址
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\img"
                        +System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator")
        );
        //歌曲地址
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\song"+System.getProperty("file.separator")
        );
        //前端用户头像地址
        registry.addResourceHandler("/img/avatorImgs/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\img"+System.getProperty("file.separator")+"avatorImgs"+System.getProperty("file.separator")
        );
        //用户头像默认地址
        registry.addResourceHandler("/img/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\img"+System.getProperty("file.separator")
        );
        //用户头像默认地址
        registry.addResourceHandler("/uploadFiles/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\uploadFiles"+System.getProperty("file.separator")
        );

        registry.addResourceHandler("/mv/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"music-server1\\mv"+System.getProperty("file.separator")
        );
    }
}

















