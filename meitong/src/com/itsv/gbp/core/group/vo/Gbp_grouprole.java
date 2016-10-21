package com.itsv.gbp.core.group.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 说明： gbp_grouprole 值对象类
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_grouprole extends BaseEntity {

	private String groupid; //groupid

	private String roleid; //roleid

    /** 以下为get,set方法 */

	public String getGroupid() {
    return this.groupid;
  }

  public void setGroupid(String groupid) {
    this.groupid = groupid;
  }

	public String getRoleid() {
    return this.roleid;
  }

  public void setRoleid(String roleid) {
    this.roleid = roleid;
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
