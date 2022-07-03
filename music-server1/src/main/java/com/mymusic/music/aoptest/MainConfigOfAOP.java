package com.mymusic.music.aoptest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MainConfigOfAOP
 * @Description TODO
 * @Author 86183
 * @Date2022-01-0615:08
 * @Version 1.0
 **/
@Configuration
public class MainConfigOfAOP {
    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    };
}
