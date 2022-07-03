package com.mymusic.music.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResponseUser
 * @Description TODO
 * @Author 86183
 * @Date2021-12-1119:40
 * @Version 1.0
 **/
@NoArgsConstructor
@Data
public class ResponseUser {
    Integer id;
    String nickname;
    String username;
    String avatar;
}
