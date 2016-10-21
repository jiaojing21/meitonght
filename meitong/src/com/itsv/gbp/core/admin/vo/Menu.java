package com.itsv.gbp.core.admin.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵����ֵ�����ࡣ����˵�����Ϣ
 * 
 * @author admin
 */
public class Menu extends BaseEntity {

	private static final long serialVersionUID = 6075959489227220694L;

	private String code; //�˵���α��

	private String name; //�˵�����

	private String action; //�˵�·��

	private String remark; //��Ҫ˵��

	private Integer idClass;//����

	private Boolean enabled; //�Ƿ���ã�0�������� 1�����á�Ĭ�Ͽ��ã�

	private Boolean leaf;//�Ƿ��ǵ׼��˵���1�ǣ�0���ǣ�

	/** ����Ϊ��get,set���� */
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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