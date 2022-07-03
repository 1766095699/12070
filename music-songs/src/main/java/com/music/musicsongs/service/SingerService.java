package com.music.musicsongs.service;

import com.music.musicsongs.pojo.Singer;

import java.util.List;

public interface SingerService {
    /**
     * 获取歌手详情
     * @param id
     * @return
     */
    Singer getSingerDetail(Integer id);


    /**
     * 获取前五个歌手用于首页渲染
     */
    List<Singer> getSingerOf5();
}
