package com.music.musicauth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Aki
 * @email 1766095699@gmail.com
 * @date 2022-06-11 15:06:28
 */
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 角色权限字符串
	 */
	private String roleKey;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private Integer delFlag;

}
