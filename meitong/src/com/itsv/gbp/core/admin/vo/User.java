package com.itsv.gbp.core.admin.vo;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵��������һ������Ա����
 * 
 * @author admin 2005-1-18
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = -3878541290529881645L;

	private String unitId; //�����ĵ�λ����

	private String userName; //��¼����

	private String password; //����

	private String realName; //��ʵ����

	private String remark; //��ע

	private Boolean enabled; //�Ƿ���á�
	
	private String tel; //�绰
	private String fax; //����
	private String mobtel; //�ƶ��绰
	private String othertel; //������ϵ�绰
	private String email; //��������
	private String certno; //���֤��
	private String duty; //ְ��
	private String status; //״̬
	private Long sortno; //������
	private Unit unit;//��λ����

	private List<Role> roles; //�����Ľ�ɫ�б�

	/** ����Ϊget,set���� */
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
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
		return ToStringBuilder.reflectionToString(this, TOSTRING_STYLE, false, BaseEntity.class);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobtel() {
		return mobtel;
	}

	public void setMobtel(String mobtel) {
		this.mobtel = mobtel;
	}

	public String getOthertel() {
		return othertel;
	}

	public void setOthertel(String othertel) {
		this.othertel = othertel;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSortno() {
		return sortno;
	}

	public void setSortno(Long sortno) {
		this.sortno = sortno;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}