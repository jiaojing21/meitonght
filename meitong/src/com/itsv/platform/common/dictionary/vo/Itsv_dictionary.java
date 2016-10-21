package com.itsv.platform.common.dictionary.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵���� �����ֵ� ֵ������
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

	private String parentcode; // ��������

	private String code; // �㼶����

	private Long codeclass; // ���α��

	private Long dictno; // ˳����

	private String dictname; // �ֵ�����

	private String description; // �ֵ�����

	private String hardcode; // ҵ�����

	private Long candelete; // ɾ����־

	private String type; // �ֵ����ͣ�0��Ĭ�ϵ�ѡ��1����ѡ��2����ѡ��

	private String brandcode;// Ʒ�ƣ�ֻ���ֵ�Ϊ��ϵ�С�ʱ�Ż���Ʒ��ѡ�

	private String exterior;// �Ƿ�Ϊ��ۣ�0���ǣ�1����

	private String screen;// �Ƿ���Ϊ�ⲿɸѡ������0���ǣ�1����

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** ����Ϊget,set���� */

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
