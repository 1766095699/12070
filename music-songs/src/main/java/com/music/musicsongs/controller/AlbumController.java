package com.music.musicsongs.controller;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.pojo.Album;
import com.music.musicsongs.pojo.Song;
import com.music.musicsongs.service.AlbumService;
import com.music.musicsongs.service.SongService;
import com.music.musicsongs.vo.AlbumVo;
import com.music.musicsongs.vo.SongVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName AlbumController
 * @Description TODO
 * @Author 86183
 * @Date2022-06-081:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;
    @GetMapping("/songByAlbumId/{albumId}")
    public Object getSongsByAlbumId(@PathVariable Integer albumId){
        List<Song> list = songService.getSongsByAlbumId(albumId);
        List<SongVo>datalist=  list.stream().map(item->{
            SongVo songVo = new SongVo();
            BeanUtils.copyProperties(item,songVo);
            return songVo;
        }).collect(Collectors.toList());
        return R.ok(datalist);
    }
    @GetMapping("/albumOf5")
    public Object getAlbumListOf5(){
        List<Album> albumListOf5 = albumService.getAlbumListOf5();
        List<AlbumVo>albumVoList =  albumListOf5.stream().map(item -> {
            AlbumVo albumVo = new AlbumVo();
            BeanUtils.copyProperties(item,albumVo);
            return albumVo;
        }).collect(Collectors.toList());
        return R.ok(albumVoList);
    }
}
