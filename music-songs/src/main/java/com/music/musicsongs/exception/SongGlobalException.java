package com.music.musicsongs.exception;

import com.music.musiccommon.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName SongGlobalException
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0810:55
 * @Version 1.0
 **/
@RestControllerAdvice
@Slf4j
public class SongGlobalException {
    @ExceptionHandler(value = RuntimeException.class)
    public R runtimeException(RuntimeException e){
        log.warn(e.toString());
        return R.error();
    }
}
