package com.mymusic.music.exception;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @ClassName BaseException
 * @Description TODO
 * @Author 86183
 * @Date2022-01-110:03
 * @Version 1.0
 **/
public class BaseException extends RuntimeException {
    private Integer code = 0;

    public BaseException(String msg){
        super(msg);
    }
    public BaseException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
    public Integer getCode() {
        return code;
    }
}
