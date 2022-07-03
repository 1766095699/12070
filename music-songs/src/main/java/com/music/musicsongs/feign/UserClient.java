package com.music.musicsongs.feign;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.feign.fallback.UserServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @ClassName UserClient
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1510:08
 * @Version 1.0
 **/
@Component
@FeignClient(value = "music-auth",fallbackFactory = UserServiceFallBackFactory.class)
public interface UserClient {
    @PostMapping("/auth/getUser")
    public R getUser(List<Integer>list);
}
