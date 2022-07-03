package com.music.musicsongs.controller;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.pojo.Singer;
import com.music.musicsongs.pojo.Song;
import com.music.musicsongs.service.SingerService;
import com.music.musicsongs.service.SongService;
import com.music.musicsongs.vo.SingerVo;
import com.music.musicsongs.vo.SongVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SingerController
 * @Description TODO
 * @Author 86183
 * @Date2022-06-081:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/artist")
public class SingerController {
    @Autowired
    SingerService singerService;
    @Autowired
    SongService songService;

    /**
     * 获取首页的前五个歌手信息
     * @return
     */
    @GetMapping("/singerOf5")
    public Object getSingerOf5(){
        List<Singer> list = singerService.getSingerOf5();
        List<SingerVo>singerVoList = list.stream().map(item->{
            SingerVo singerVo = new SingerVo();
            BeanUtils.copyProperties(item,singerVo );
            return singerVo;
        }).collect(Collectors.toList());
        return R.ok(singerVoList);
    }

    @GetMapping("/top/song")
    public Object getSongsBySingerIdOf50(@RequestParam Integer id){
        List<Song> songsBySingerIdOf50 = songService.getSongsBySingerIdOf50(id);
        List<SongVo>songVos = songsBySingerIdOf50.stream().map(item->{
            SongVo songVo = new SongVo();
            BeanUtils.copyProperties(item,songVo);
            return songVo;
        }).collect(Collectors.toList());
        return R.ok(songsBySingerIdOf50);
    }
}
