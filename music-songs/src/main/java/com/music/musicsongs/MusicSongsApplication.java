package com.music.musicsongs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.music.musicsongs.dao")
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
public class MusicSongsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicSongsApplication.class, args);
    }

}
