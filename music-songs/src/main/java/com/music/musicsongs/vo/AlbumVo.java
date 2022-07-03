package com.music.musicsongs.vo;

import com.music.musicsongs.pojo.Song;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Album
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0722:46
 * @Version 1.0
 **/
@Data
public class AlbumVo {
    //专辑id
    Integer id;
    //专辑名
    String name;
    //歌手
    Integer singerId;
    //发行日期
    Date publishDate;
    //专辑发行时间
    String publishCompany;
    List<Song> songlist;
}
