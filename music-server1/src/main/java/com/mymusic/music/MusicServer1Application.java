package com.mymusic.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.mymusic")
@MapperScan("com.mymusic.music.dao")
@EnableFeignClients
//@ComponentScan("com.mymusic")
@EnableDiscoveryClient
public class MusicServer1Application {
    public static void main(String[] args) {
        SpringApplication.run(MusicServer1Application.class, args);
    }

}
