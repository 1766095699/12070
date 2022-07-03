package com.mymusic.music.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @ClassName test1
 * @Description TODO
 * @Author 86183
 * @Date2022-04-0410:03
 * @Version 1.0
 **/
public class c  {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("起来了");
        });
        t1.start();
        t1.join();
        System.out.println("222");
    }
}

