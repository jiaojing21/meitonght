package com.itsv.gbp.core.group.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵���� �� ֵ������
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_group extends BaseEntity {

	private String groupremarks; //��ע

	private String groupname; //������

    /** ����Ϊget,set���� */

	public String getGroupremarks() {
    return this.groupremarks;
  }

  public void setGroupremarks(String groupremarks) {
    this.groupremarks = groupremarks;
  }

	public String getGroupname() {
    return this.groupname;
  }

  public void setGroupname(String groupname) {
    this.groupname = groupname;
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
