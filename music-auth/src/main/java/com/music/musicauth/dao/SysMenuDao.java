package com.music.musicauth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.musicauth.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Aki
 * @email 1766095699@gmail.com
 * @date 2022-06-11 15:06:28
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {
    List<String> selectPermsByUserId(@Param("userid") Long userid);
}
