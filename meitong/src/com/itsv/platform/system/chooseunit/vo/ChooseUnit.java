package com.itsv.platform.system.chooseunit.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ��λ��Ϣֵ������
 * 
 * @author admin
 * 
 */
public class ChooseUnit extends BaseEntity {

	private static final long serialVersionUID = -7740005796375536970L;

	private String id;

	private String code; // ��λ�����

	private String name; // ��λ����

	private String totalName; // ��λȫ��

	private Integer idClass;// ����

	private Boolean leaf;// �Ƿ��ǵ׼��˵���1�ǣ�0���ǣ�

	private Boolean enabled; // �Ƿ���ã�0�������� 1�����á�Ĭ�Ͽ��ã�

	public ChooseUnit() {

	}

	/**
	 * ���캯�� id,��λ�����,��λ����,��λȫ��,����,�Ƿ��ǵ׼��˵���1�ǣ�0���ǣ�
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

	/** ����Ϊget,set���� */
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