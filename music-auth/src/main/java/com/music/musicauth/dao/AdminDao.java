package com.music.musicauth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.musicauth.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Aki
 * @Description 用户Dao
 * @Date  2022-06-09
 * @Param 
 * @return 
 **/
@Mapper
public interface AdminDao extends BaseMapper<Admin> {
	
}
