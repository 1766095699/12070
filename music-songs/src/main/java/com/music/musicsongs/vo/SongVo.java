package com.music.musicsongs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 歌曲
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongVo implements Serializable {
    /*主键*/
    private Integer id;
    //歌手id
    private Integer singerId;
    /*歌名*/
    private String name;
    /*简介*/
    private String introduction;
    /*歌曲图片*/
    private String pic;
    /*歌词*/
    private String lyric;
    /*歌曲地址*/
    private String url;
    /*mv地址*/
    private String mvurl;
    /*歌曲价格*/
    private BigDecimal price;
    /*歌手名称*/
    private String singerName;
    //专辑id
    private Integer albumId;
    //专辑名
    private String albumName;
}
