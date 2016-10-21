package com.itsv.platform.common.dictionary.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * 说明： 数据字典 值对象类
 * 
 * @author milu
 * @since 2007-07-22
 * @version 1.0
 */
public class Itsv_dictionary extends BaseEntity {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String parentcode; // 父级编码

	private String code; // 层级编码

	private Long codeclass; // 级次编号

	private Long dictno; // 顺序编号

	private String dictname; // 字典名称

	private String description; // 字典描述

	private String hardcode; // 业务编码

	private Long candelete; // 删除标志

	private String type; // 字典类型（0：默认单选，1：单选，2：多选）

	private String brandcode;// 品牌（只有字典为《系列》时才会有品牌选项）

	private String exterior;// 是否为外观（0：是；1：否）

	private String screen;// 是否作为外部筛选条件（0：是；1：否）

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** 以下为get,set方法 */

	public String getParentcode() {
		return this.parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCodeclass() {
		return this.codeclass;
	}

	public void setCodeclass(Long codeclass) {
		this.codeclass = codeclass;
	}

	public Long getDictno() {
		return this.dictno;
	}

	public void setDictno(Long dictno) {
		this.dictno = dictno;
	}

	public String getDictname() {
		return this.dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHardcode() {
		return this.hardcode;
	}

	public void setHardcode(String hardcode) {
		this.hardcode = hardcode;
	}

	public Long getCandelete() {
		return this.candelete;
	}

	public void setCandelete(Long candelete) {
		this.candelete = candelete;
	}

	public String getBrandcode() {
		return brandcode;
	}

	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}

	public String getExterior() {
		return exterior;
	}

	public void setExterior(String exterior) {
		this.exterior = exterior;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
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
