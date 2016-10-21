package com.itsv.platform.system.chooseunit.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 单位信息值对象类
 * 
 * @author admin
 * 
 */
public class ChooseUnit extends BaseEntity {

	private static final long serialVersionUID = -7740005796375536970L;

	private String id;

	private String code; // 单位层次码

	private String name; // 单位名称

	private String totalName; // 单位全称

	private Integer idClass;// 级次

	private Boolean leaf;// 是否是底级菜单（1是，0不是）

	private Boolean enabled; // 是否可用（0、不可用 1、可用。默认可用）

	public ChooseUnit() {

	}

	/**
	 * 构造函数 id,单位层次码,单位名称,单位全称,级次,是否是底级菜单（1是，0不是）
	 */
	public ChooseUnit(String id, String code, String name, String totalName,
			Integer idClass, Boolean leaf) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.totalName = totalName;
		this.idClass = idClass;
		this.leaf = leaf;
	}

	/** 以下为get,set方法 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getIdClass() {
		return idClass;
	}

	public void setIdClass(Integer idClass) {
		this.idClass = idClass;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getTotalName() {
		return totalName;
	}

	public void setTotalName(String totalName) {
		this.totalName = totalName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, TOSTRING_STYLE, false,
				BaseEntity.class);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}