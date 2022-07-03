package com.mymusic.music.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RestExceptionHandler
 * @Description TODO
 * @Author 86183
 * @Date2022-01-1023:53
 * @Version 1.0
 **/
@RestControllerAdvice
public class RestExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public String handleException(ExceptionHandler e){
//        System.out.println
//        return e.toString();
//
//    }
//    @ExceptionHandler(AriException.class)//传入你要处理的异常
//    public String handleAriException(BaseException e){
//        System.out.println("exception321321"+e.getCode()+" "+e.getMessage());
//        return e.toString();
//    }

    @ExceptionHandler(SignException.class)//传入你要处理的异常
    public Object handleSignException(SignException e){
        System.out.println("exception321321"+e.getCode()+" "+e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("code",e.getCode() );
        map.put("msg",e.getMsg());
        return map;
    }
}
