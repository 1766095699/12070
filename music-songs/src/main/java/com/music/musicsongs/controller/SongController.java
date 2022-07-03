package com.music.musicsongs.controller;

import com.music.musicsongs.dao.SongMapper;
import com.music.musicsongs.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SongController
 * @Description TODO
 * @Author 86183
 * @Date2022-06-081:16
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/song")
public class SongController {
    @Autowired
    SongService songService;
    @Autowired
    SongMapper songMapper;
//    @GetMapping("/allSongs")
//    public Object getSongById(){
//        List<Song> songList =  songMapper.selectList(null);
//        System.out.println(songList);
//        for(Song song1 : songList){
//
//        }
////        return song;
//    }
}
