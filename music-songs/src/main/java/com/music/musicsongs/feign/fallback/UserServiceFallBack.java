package com.music.musicsongs.feign.fallback;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName UserServiceFallBack
 * @Description
 * @Author Aki
 * @Date2022-06-1715:27
 * @Version 1.0
 **/
@Component
@Slf4j
public class UserServiceFallBack implements UserClient {

    @Override
    public R getUser(List<Integer> list) {
        log.info("请稍后再试");
        return R.error(503,"请稍后再试");
    }
}
