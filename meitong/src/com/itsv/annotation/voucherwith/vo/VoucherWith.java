package com.itsv.annotation.voucherwith.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� ����ȯ��ϸ ֵ������
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */

@Entity
@Table(name = "voucherwith")
public class VoucherWith {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID")
	private String id;

	@Column(name = "CODE", nullable = true, length = 16)
	private String code; // ����ȯΨһ��

	@Column(name = "VOUCHERID", nullable = true, length = 32)
	private String voucherId; // ����ȯ����

	@Column(name = "TYPE", length = 2)
	private String type; // ʹ��״̬

	@Column(name = "CREATETIME", length = 0)
	private Date createtime; // ���ʱ��

	@Column(name = "CREATEUSER", length = 32)
	private String createuser; // �����

	@Transient
	private String worth;// ����ȯ��ֵ
	@Transient
	private String issueduser;// �����û�
	@Transient
	private String access;// ��ȡ;��
	@Transient
	private String fetchTime;// ��ȡʱ��
	@Transient
	private String failureTime;// ����ʱ��
	@Transient
	private String status;// ��ȡ״̬

	/** ����Ϊget,set���� */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getWorth() {
		return worth;
	}

	public void setWorth(String worth) {
		this.worth = worth;
	}

	public String getIssueduser() {
		return issueduser;
	}

	public void setIssueduser(String issueduser) {
		this.issueduser = issueduser;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(String fetchTime) {
		this.fetchTime = fetchTime;
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
