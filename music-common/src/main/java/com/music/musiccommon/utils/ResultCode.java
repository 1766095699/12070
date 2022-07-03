package com.music.musiccommon.utils;

public enum ResultCode {
    SUCCESS(200),
    ERROR(500);
    Integer code;
    ResultCode(Integer code1){
        code = code1;
    }
}
