package com.mymusic.music.aoptest;

import org.springframework.stereotype.Component;

/**
 * @ClassName Math
 * @Description TODO
 * @Author 86183
 * @Date2022-01-0614:54
 * @Version 1.0
 **/
@Component
public class MathCalculator {
    public int div(int a,int b){
        System.out.println("div方法被调用");
        return a/b;
    }
}
