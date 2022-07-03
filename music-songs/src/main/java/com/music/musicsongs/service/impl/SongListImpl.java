package com.music.musicsongs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.musiccommon.utils.Query;
import com.music.musicsongs.dao.ListSongMapper;
import com.music.musicsongs.dao.SongListMapper;
import com.music.musicsongs.pojo.ListSong;
import com.music.musicsongs.pojo.SongList;
import com.music.musicsongs.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SongListMapper
 * @Description TODO
 * @Author 86183
 * @Date2022-06-080:45
 * @Version 1.0
 **/
@Service
public class SongListImpl implements SongListService {
    @Autowired
    private SongListMapper songListMapper;
    @Autowired
    private ListSongMapper listSongMapper;
    /**
     * 获取前8个歌单用于首页渲染
     *
     * @return
     */
    @Override
    public List<SongList> getSongListOf8() {
        QueryWrapper<SongList>queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 0,8");
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        return songLists;
    }

    /**
     * 把歌曲插入对应歌单
     *
     * @param songId
     * @param songListId
     * @return
     */
    @Override
    public Boolean insertSongToSongList(Integer songId, Integer songListId,Integer userId) {
        ListSong listSong = new ListSong();
        listSong.setSongId(songId);
        listSong.setSongListId(songListId);
        listSong.setUserId(userId);
        int insert = listSongMapper.insert(listSong);
        /**
         * 插入成功返回false,否则返回true
         */
        if(insert==0){
            return false;
        }
        return true;
    }

    /**
     * 把歌曲从歌单中删除
     *
     * @param songId
     * @param songListId
     * @return
     */
    @Override
    public Boolean deleteSongToSongList(Integer songId, Integer songListId,Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper( );
        queryWrapper.eq("song_id",songId );
        queryWrapper.eq("song_list_id",songListId);
        queryWrapper.eq("user_id", userId);
        int delete = listSongMapper.delete(queryWrapper);
        /**
         * 删除成功返回false,否则返回true
         */
        if(delete==0){
            return false;
        }
        return true;
    }

    /**
     * 创建歌单
     *
     * @param songList
     * @return
     */
    @Override
    public Boolean insertSongList(SongList songList){
        int insert = songListMapper.insert(songList);
        return insert==0?false:true;
    }

    public static void main(String[] args) {

    }
}
