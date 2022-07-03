package com.music.musicauth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author Aki
 * @email 1766095699@gmail.com
 * @date 2022-06-11 15:06:28
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 菜单名
	 */
	private String menuName;
	/**
	 * 路径名
	 */
	private String path;
	/**
	 * 0可用,1不可用
	 */
	private String status;
	/**
	 * 
	 */
	private String component;
	/**
	 * 
	 */
	private String perms;

}
