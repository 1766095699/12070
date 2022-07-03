package com.music.musicsongs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌手
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SingerVo implements Serializable {
    /*主键*/
    private Integer id;
    /*账号*/
    private String name;
    /*性别*/
    private String sex;
    /*头像*/
    private String pic;
    /*生日*/
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT-8")
    private Date birth;
    /*地区*/
    private String location;
    /*简介*/
    private String introduction;


}
