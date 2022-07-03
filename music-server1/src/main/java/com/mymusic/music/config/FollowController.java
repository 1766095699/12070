package com.mymusic.music.config;

import com.mymusic.music.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;

/**
 * @ClassName FollowController
 * @Description TODO
 * @Author 86183
 * @Date2022-03-171:10
 * @Version 1.0
 **/
@RestController
@RequestMapping("follow")
public class FollowController {
    @Autowired
    FollowService followService;
    @GetMapping("commonFollowList")
    public Object commonFollowList(@RequestParam("id1") Integer userid1,@RequestParam("id2") Integer userid2) {
        HashMap<String,Object>map = new HashMap<>();
        map.put("data",followService.commonFollowList(userid1, userid2));
        return map;
    }
}
