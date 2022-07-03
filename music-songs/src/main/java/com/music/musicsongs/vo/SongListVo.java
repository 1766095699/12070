package com.music.musicsongs.vo;

import com.music.musicsongs.pojo.Song;

import java.util.List;

/**
 * @ClassName SongListVo
 * @Description TODO
 * @Author 86183
 * @Date2022-06-089:09
 * @Version 1.0
 **/
public class SongListVo {
    /*主键*/
    private Integer id;
    /*歌单标题*/
    private String title;
    /*歌单图片*/
    private String pic;
    /*简介*/
    private String introduction;
    /*风格*/
    private String style;
    //歌单歌曲
    private List<Song>songlist;
}
