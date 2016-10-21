package com.itsv.platform.role.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 说明： 功能角色 值对象类
 * 
 * @author Houxc
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionRole extends BaseEntity {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String function_id; //function_id

	private String role_id; //role_id
    private String menu_id; //role_id


    /** 以下为get,set方法 */

	public String getFunction_id() {
    return this.function_id;
  }

  public void setFunction_id(String function_id) {
    this.function_id = function_id;
  }

	public String getRole_id() {
    return this.role_id;
  }

  public void setRole_id(String role_id) {
    this.role_id = role_id;
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

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

}
