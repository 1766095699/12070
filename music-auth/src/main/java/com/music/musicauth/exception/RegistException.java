package com.music.musicauth.exception;

/**
 * @ClassName RegistException
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1420:48
 * @Version 1.0
 **/
public class RegistException extends RuntimeException {
    String msg;
    int code;
    public RegistException(String msg, int code){
        this.msg = msg;
        this.code = code;
    }
    public RegistException(){}
}
