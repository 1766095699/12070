package com.music.musicsongs.controller;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.pojo.Song;
import com.music.musicsongs.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName RankController
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0812:12
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    public SongService songService;
    /**
     * 获取榜单的前10首歌
     * @param id
     * @return
     */
    @GetMapping("/getRankSongOf10/{id}")
    public Object getRankSongOf10(@PathVariable Integer id){
        List<Song> rankSongOf10 = songService.getRankSongOf10(id);
        return R.ok(rankSongOf10);
    }
}
