package com.itsv.platform.system.chooseuser.vo;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;
import com.itsv.platform.system.chooseuser.vo.UserRole;

/**
 * ˵��������һ������Ա����
 * 
 * @author admin 2005-1-18
 */
public class ChooseUser extends BaseEntity {

	private static final long serialVersionUID = -3878541290529881645L;

	private String id;

	private String unitId; // �����ĵ�λ����

	private String userName; // ��¼����

	private String password; // ����

	private String realName; // ��ʵ����

	private String remark; // ��ע

	private Boolean enabled; // �Ƿ���á�
	
	private String status; //״̬
	
	private Long sortno; //������

	private List<UserRole> roles; // �����Ľ�ɫ�б�

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getSortno() {
		return sortno;
	}

	public void setSortno(Long sortno) {
		this.sortno = sortno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}