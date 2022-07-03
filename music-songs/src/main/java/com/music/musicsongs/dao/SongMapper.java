package com.music.musicsongs.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.musicsongs.pojo.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌曲
 */
@Repository
public interface SongMapper extends BaseMapper<Song>{
    /**
     * 根据歌单id获取对应歌单的全部歌曲(官方歌单)
     * @param id
     * @return
     */
    List<Song> getSongsById(Integer id);

    /**
     * 根据歌单id和用户id获取用户歌单的所有歌曲
     * @param userId
     * @param songId
     * @return
     */
    List<Song> getSongsBySongListIdAndUserId(Integer userId, Integer songId);

    /**
     * 通过专辑id来获取专辑中的所有歌曲
     */
    List<Song> getSongsByAlbumId(Integer albumId);

    /**
     * 通过歌手id获取歌手的获取该歌手前五十首歌曲(网易云的歌手页就是这样做的
     * @return
     */
    List<Song>getAllSongsBySingerId(Integer id);

}
















