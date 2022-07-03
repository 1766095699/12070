package com.music.musicsongs.feign.fallback;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.feign.UserClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName s
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1716:36
 * @Version 1.0
 **/
@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public R getUser(List<Integer> list) {
                return R.error(502,"服务访问超时");
            }
        };
    }
}
