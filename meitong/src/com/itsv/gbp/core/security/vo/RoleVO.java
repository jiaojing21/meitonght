package com.itsv.gbp.core.security.vo;

import java.io.Serializable;

/**
 * 角色值对象类。该类只是个demo，为了演示将值对象类适配给Acegi安全模块。实际应用中应该替换为应用自身的类。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 上午11:13:06
 * @version 1.0
 */
public class RoleVO implements Serializable {

	private static final long serialVersionUID = -3053357634496445839L;

	private Integer roleId;

	private String roleName;

	public RoleVO() {
	}

	public RoleVO(int id, String name) {
		this.roleId = new Integer(id);
		this.roleName = name;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
