package com.music.musicsongs.service;

import com.music.musicsongs.pojo.Album;

import java.util.List;

/**
 * @ClassName AlbumService
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0723:41
 * @Version 1.0
 **/
public interface AlbumService {
    /**
     * 返回前5张专辑的数据
     * @return
     */
    List<Album> getAlbumListOf5();
}
