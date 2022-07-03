package com.music.musicauth.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 
 * 
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2022-06-09 22:38:32
 */
@Data
@TableName("admin")
public class Admin implements UserDetails {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 个性签名
	 */
	private String sign;
	/**
	 * 用户状态（0：离线；1：在线）
	 */
	private Boolean status;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String avatar;
	/**
	 * 二维码
	 */
	private String codeurl;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	/**
	 * 会员等级
	 */
	private String vipLevel;

	/**
	 * 角色权限
	 */
	private String role;

	@TableField(exist = false)//这里表示数据库中不存在该字段,这样mp查询的时候才不会带上这个字段
	List<String>permissions;
	public Admin(){};
	public Admin(String username,String password,List<String> permissions){
		this.username = username;
		this.password = password;
		this.permissions = permissions;
	}
	@TableField(exist = false)//这里表示数据库中不存在该字段,这样mp查询的时候才不会带上这个字段
	@JSONField(serialize = false)
	private List<GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("获取权限："+authorities);
		if(authorities!=null){//不为空直接返回
			return authorities;
		}
		authorities = new ArrayList<>();
		System.out.println("per=="+permissions);
		if(Objects.isNull(permissions))return authorities;
        for (String role : permissions) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
