package com.music.musicauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })
@MapperScan("com.music.musicauth.dao")
public class MusicAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicAuthApplication.class, args);
    }


}
