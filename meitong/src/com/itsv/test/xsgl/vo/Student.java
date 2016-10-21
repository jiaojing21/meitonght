package com.itsv.test.xsgl.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 说明： student 值对象类
 * 
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class Student extends BaseEntity {

	private Long age; //年龄

	private String name; //姓名

	private String nick; //昵称

    /** 以下为get,set方法 */

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
