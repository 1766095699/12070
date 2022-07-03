package com.mymusic.music.exception;

import lombok.Data;

/**
 * @ClassName SignException
 * @Description TODO
 * @Author 86183
 * @Date2022-03-2422:23
 * @Version 1.0
 **/
@Data
public class SignException extends RuntimeException {
    public String code;
    public String msg;
    public SignException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
