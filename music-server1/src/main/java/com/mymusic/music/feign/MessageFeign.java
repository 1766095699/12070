package com.mymusic.music.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName MessageFeign
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1917:59
 * @Version 1.0
 **/
@FeignClient("third-part")
@Component
public interface MessageFeign {
    @GetMapping("/message/getMessage")
    public String getMessage();
}
