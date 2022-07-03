package com.music.musicsongs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.music.musiccommon.utils.R;
import com.music.musicsongs.dao.AlbumMapper;
import com.music.musicsongs.dao.ListSongMapper;
import com.music.musicsongs.dao.SongMapper;
import com.music.musicsongs.pojo.Album;
import com.music.musicsongs.pojo.ListSong;
import com.music.musicsongs.pojo.Song;
import com.music.musicsongs.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName SongServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0723:56
 * @Version 1.0
 **/
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private ListSongMapper listSongMapper;
    @Autowired
    private AlbumMapper albumMapper;
    /**
     * 根据歌单id获取对应歌单的全部歌曲(官方歌单)
     *(未完成)
     * @param id
     * @return
     */
    @Override
    @Transactional
    public List<Song> getOfficialSongsById(Integer id) {
        QueryWrapper<ListSong>queryWrapper = new QueryWrapper();
        queryWrapper.eq("song_list_id", id);//userid=-1说明这个歌单是官方的
        queryWrapper.select("song_id");
        List<ListSong> listSongs = listSongMapper.selectList(queryWrapper);
        System.out.println(id);
        System.out.println(listSongs);
        if(Objects.isNull(listSongs)||listSongs.size()==0){//歌单数据为空或者该歌单不是官方歌单
            throw new RuntimeException();//非法查询,直接抛异常
        }
        QueryWrapper<Song>songQueryWrapper = new QueryWrapper<>();
        for(ListSong listSong:listSongs)
            songQueryWrapper.or().eq("id", listSong.getSongId());
        List<Song> songs = songMapper.selectList(songQueryWrapper);
        return songs;
    }

    /**
     * 根据歌单id和用户id获取用户歌单的所有歌曲
     *
     * @param userId
     * @param songlistId
     * @return
     */
    @Override
    public List<Song> getSongsBySongListIdAndUserId(Integer songlistId) {
        QueryWrapper<ListSong>queryWrapper = new QueryWrapper();
        queryWrapper.eq("song_list_id",songlistId);//userid=-1说明这个歌单是官方的
        queryWrapper.select("song_id");
        List<ListSong> listSongs = listSongMapper.selectList(queryWrapper);
        if(Objects.isNull(listSongs)||listSongs.size()==0){//歌单数据为空或者该歌单不是官方歌单
            throw new RuntimeException();//非法查询,直接抛异常
        }
        QueryWrapper<Song>songQueryWrapper = new QueryWrapper<>();
        for(ListSong listSong:listSongs){
            songQueryWrapper.or().eq("id", listSong.getSongId());
        }

        List<Song> songs = songMapper.selectList(songQueryWrapper);
        return songs;
    }


    /**
     * 通过专辑id来获取专辑中的所有歌曲
     *(未完成)
     * @param albumId
     */
    @Override
    public List<Song> getSongsByAlbumId(Integer albumId) {
        QueryWrapper<Album>queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", albumId);
        queryWrapper.select("song_id");
        List<Album> albumList = albumMapper.selectList(queryWrapper);
        QueryWrapper<Song>songQueryWrapper = new QueryWrapper<>();
        for(Album album:albumList)
            songQueryWrapper.or().eq("id", album.getSongId());
        List<Song> songs = songMapper.selectList(songQueryWrapper);
        return songs;
    }

    /**
     * 通过歌手id获取歌手的获取该歌手前五十首歌曲(网易云的歌手页就是这样做的
     *
     * @param id
     * @return
     */
    @Override
    public List<Song> getSongsBySingerIdOf50(Integer id) {
        QueryWrapper<Song>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id",id);
        queryWrapper.last("limit 0,50");
        List<Song> songs = songMapper.selectList(queryWrapper);
        return songs;
    }

    /**
     * 返回榜单上的十首歌
     *
     * @param id (榜单id)
     * @return
     */
    @Override
    public List<Song> getRankSongOf10(Integer id) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("click_count");
        queryWrapper.last("limit 0,10");
        List<Song> songs = songMapper.selectList(queryWrapper);
        return songs;
    }

    /**
     * 插入歌曲
     *
     * @param song
     */
    @Override
    public Boolean insertSong(Song song) {
        int insert = songMapper.insert(song);
        return insert==0?false:true;
    }
}
