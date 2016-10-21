package com.itsv.test.xsgl.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵���� student ֵ������
 * 
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class Student extends BaseEntity {

	private Long age; //����

	private String name; //����

	private String nick; //�ǳ�

    /** ����Ϊget,set���� */

	public Long getAge() {
    return this.age;
  }

  public void setAge(Long age) {
    this.age = age;
  }

	public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

	public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
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
