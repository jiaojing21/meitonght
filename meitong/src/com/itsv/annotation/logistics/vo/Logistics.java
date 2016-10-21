package com.itsv.annotation.logistics.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： logistics 值对象类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

@Entity
@Table(name = "logistics")
public class Logistics {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID")
	private String id;

	@Column(name = "LOGISTICS_NUMBER", length = 32)
	private String logisticsNumber; // 物流单号

	@Column(name = "ORDER_NUMBER", length = 32)
	private String orderNumber; // 订单号

	@Column(name = "NAME", length = 200)
	private String name; // 物流名称

	@Column(name = "CODE", length = 50)
	private String code; // 物流简称

	@Column(name = "SENDTIME", length = 0)
	private Date sendTime; // 发送时间

	@Column(name = "REMARK_1", length = 50)
	private String remark1; // remark_1

	@Column(name = "REMARK_2", length = 50)
	private String remark2; // remark_2

	/** 以下为get,set方法 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogisticsNumber() {
		return this.logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
