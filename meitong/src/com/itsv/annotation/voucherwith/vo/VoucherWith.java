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
 * 说明： 代金券详细 值对象类
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
	private String code; // 代金券唯一码

	@Column(name = "VOUCHERID", nullable = true, length = 32)
	private String voucherId; // 代金券主键

	@Column(name = "TYPE", length = 2)
	private String type; // 使用状态

	@Column(name = "CREATETIME", length = 0)
	private Date createtime; // 添加时间

	@Column(name = "CREATEUSER", length = 32)
	private String createuser; // 添加人

	@Transient
	private String worth;// 代金券价值
	@Transient
	private String issueduser;// 发放用户
	@Transient
	private String access;// 获取途径
	@Transient
	private String fetchTime;// 获取时间
	@Transient
	private String failureTime;// 过期时间
	@Transient
	private String status;// 领取状态

	/** 以下为get,set方法 */
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
