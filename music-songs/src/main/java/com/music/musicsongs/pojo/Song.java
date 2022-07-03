package com.music.musicsongs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 歌曲
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("song")
public class Song implements Serializable {
    /*主键*/
    @TableId(type = IdType.AUTO)
    private Integer id;
    //歌手id
    @TableField("singer_id_cloud")
    private Integer singerId;
    /*歌名*/
    private String name;
    /*简介*/
    private String introduction;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;
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
    //歌曲被播放次数
    private String clickCount;
    //专辑id
    @TableField("album_id_cloud")
    private Integer albumId;
    //专辑名
    private String albumName;
}
