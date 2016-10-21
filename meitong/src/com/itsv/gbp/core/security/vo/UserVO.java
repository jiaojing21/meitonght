package com.itsv.gbp.core.security.vo;

import java.io.Serializable;
import java.util.List;

/**
 * �û������ࡣ<br>
 * ����ֻ�Ǹ�demo��Ϊ����ʾ��ֵ�����������Acegi��ȫģ�顣ʵ��Ӧ����Ӧ��ʹ��Ӧ��������ࡣ
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 ����11:15:06
 * @version 1.0
 */
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1938324297611862390L;

	private Integer userId;

	private String userName;

	private String password;

	private Boolean enabled;

	private List<RoleVO> roles;

	public UserVO() {
	}

	public UserVO(int id, String name, String password) {
		this.userId = id;
		this.userName = name;
		this.password = password;
		this.enabled = Boolean.TRUE;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
