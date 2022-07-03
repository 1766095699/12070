package com.music.musicsongs.service;

import com.music.musicsongs.pojo.SongList;

import java.util.List;

public interface SongListService {
    /**
     * 获取前8个歌单用于首页渲染
     * @return
     */
    List<SongList> getSongListOf8();

    /**
     * 把歌曲插入对应歌单
     * @param songId
     * @param songListId
     * @return
     */
    Boolean insertSongToSongList(Integer songId,Integer songListId,Integer userId);

    /**
     * 把歌曲从歌单中删除
     * @param songId
     * @param songListId
     * @return
     */
    Boolean deleteSongToSongList(Integer songId,Integer songListId,Integer userId);

    /**
     *  创建歌单
     * @param songList
     * @return
     */
    Boolean insertSongList(SongList songList);
}
