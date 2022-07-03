package com.mymusic.music.exception;

/**
 * @ClassName AriException
 * @Description TODO
 * @Author 86183
 * @Date2022-01-1023:58
 * @Version 1.0
 **/
public class AriException extends BaseException{
    public AriException(Integer code,String msg){
        super(code,msg);
    }

    public AriException(String msg){
        super(msg);
    }
}
