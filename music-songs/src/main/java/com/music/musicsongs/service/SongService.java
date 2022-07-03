package com.music.musicsongs.service;

import com.music.musicsongs.pojo.Song;

import java.util.List;

public interface SongService {
    /**
     * 根据歌单id获取对应歌单的全部歌曲(官方歌单)
     * @param id
     * @return
     */
    List<Song> getOfficialSongsById(Integer id);

    /**
     * 根据歌单id和用户id获取用户歌单的所有歌曲
     *
     * @param songlistId
     * @return
     */
    List<Song> getSongsBySongListIdAndUserId(Integer songlistId);

    /**
     * 通过专辑id来获取专辑中的所有歌曲
     */
    List<Song> getSongsByAlbumId(Integer albumId);

    /**
     * 通过歌手id获取歌手的获取该歌手前五十首歌曲(网易云的歌手页就是这样做的
     * @return
     */
    List<Song>getSongsBySingerIdOf50(Integer id);

    /**
     * 返回榜单上的十首歌
     * @param id (榜单id)
     * @return
     */
    List<Song>getRankSongOf10(Integer id);
    /**
     * 插入歌曲
     */
    Boolean insertSong(Song song);
}
