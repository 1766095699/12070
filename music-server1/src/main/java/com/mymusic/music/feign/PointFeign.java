package com.mymusic.music.feign;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@FeignClient("reward-point")
@Component
public interface PointFeign {
    @Transactional
    @PostMapping("point/addPoint")
    public Object addPoint(@RequestParam("userid") Integer userid, @RequestParam("addreward") Integer addreward, @RequestParam("endtime")String endtime,@RequestParam("point_from") String point_from) throws ParseException;
    @PostMapping("point/getPoint")
    public Object getPoint(@RequestParam("userid")Integer userid);

    @GetMapping("point/delete")
    public Object delete(@RequestParam("id")Integer id);
}
