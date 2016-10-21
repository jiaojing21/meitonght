package com.itsv.platform.role.vo;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 说明： 功能 值对象类
 * 
 * @author Houxc
 * @since 2007-07-09
 * @version 1.0
 */
public class Function extends BaseEntity {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String menu_id; //menu_id

	private String name; //功能名称

	private String code; //功能码

	private String remarks; //说明

	private Date adddate; //adddate

    private int vc;
    

    public int getVc() {
        return vc;
    }

    public void setVc(int vc) {
        this.vc = vc;
    }    
    
    /** 以下为get,set方法 */

	public String getMenu_id() {
    return this.menu_id;
  }

  public void setMenu_id(String menu_id) {
    this.menu_id = menu_id;
  }

	public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

	public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

	public String getRemarks() {
    return this.remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

	public Date getAdddate() {
    return this.adddate;
  }

  public void setAdddate(Date adddate) {
    this.adddate = adddate;
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
