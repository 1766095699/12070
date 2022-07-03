package com.mymusic.music.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName IndexListItem
 * @Description TODO
 * @Author 86183
 * @Date2021-11-2713:10
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexListItem {
    Integer id;
    String content;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    Date lastTime;
    String nickname;//用户昵称
    String avatar;//用户头像
    Integer type;//表示消息类型
}
