package com.mymusic.music.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Singer implements Serializable {
    /*主键*/
    private Integer id;
    /*账号*/
    private String name;
    /*性别*/
    private Byte sex;
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
