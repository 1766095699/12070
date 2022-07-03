package com.music.musicauth.exception;

import com.music.musiccommon.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

/**
 * @ClassName AuthGlobalException
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1112:24
 * @Version 1.0
 **/
@Slf4j
@RestControllerAdvice
public class AuthGlobalException {
    @ExceptionHandler(AuthenticationException.class)
    //这里设置异常捕捉也不会失效,假如过滤器那边配置了的话
    public R error(AuthenticationException e) {
//        System.out.println("gle");
        return R.error(400,"认证失败" );
    }
    @ExceptionHandler(RegistException.class)
    public R registError(String msg,RegistException e){
        return R.error(501,"用户注册失败" );
    }


//    @ExceptionHandler(value = RuntimeException.class)
//    public R ErrorException(RuntimeException e){
//        log.warn("捕获全局异常{}",e);
//        return R.error(500,"发生异常");
//    }
}
