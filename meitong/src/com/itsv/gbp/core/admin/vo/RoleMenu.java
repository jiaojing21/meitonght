package com.itsv.gbp.core.admin.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 描述角色－菜单对应关系
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-7 上午11:51:14
 * @version 1.0
 */
public class RoleMenu extends BaseEntity {

	private static final long serialVersionUID = 7047031874640453645L;

	private String roleId;

	private String menuId;

	public RoleMenu() {
	}

	public RoleMenu(String roleId, String menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}

	/**get,set*/
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, TOSTRING_STYLE, false, BaseEntity.class);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
