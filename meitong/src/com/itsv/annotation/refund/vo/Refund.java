package com.itsv.annotation.refund.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： refund 值对象类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="refund")
public class Refund {
                          @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="ORDER_NUMBER",length=32  )
	private String orderNumber; //订单号

    @Column(name="REFUND_NUMBER",length=32  )
	private String refundNumber; //退换货单号

    @Column(name="PROPOSER",length=50  )
	private String proposer; //申请人

    @Column(name="PROPOSER_PHONE",length=20  )
	private String proposerPhone; //申请人手机号

    @Column(name="LOGISTICS_NUMBER",length=32  )
	private String logisticsNumber; //物流单号

    @Column(name="REASON",length=500  )
	private String reason; //理由

    @Column(name="REFUNDTIME",length=0  )
	private Date refundTime; //退货时间

    @Column(name="FLAG",length=10  )
	private String flag; //退换货标记；0：未收货；1：已收货

    @Column(name="REMARK_1",length=50  )
	private String remark1; //remark_1

    @Column(name="REMARK_2",length=50  )
	private String remark2; //remark_2

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

	public String getRefundNumber() {
    return this.refundNumber;
  }

  public void setRefundNumber(String refundNumber) {
    this.refundNumber = refundNumber;
  }

	public String getProposer() {
    return this.proposer;
  }

  public void setProposer(String proposer) {
    this.proposer = proposer;
  }

	public String getProposerPhone() {
    return this.proposerPhone;
  }

  public void setProposerPhone(String proposerPhone) {
    this.proposerPhone = proposerPhone;
  }

	public String getLogisticsNumber() {
    return this.logisticsNumber;
  }

  public void setLogisticsNumber(String logisticsNumber) {
    this.logisticsNumber = logisticsNumber;
  }

	public String getReason() {
    return this.reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

	public Date getRefundTime() {
    return this.refundTime;
  }

  public void setRefundTime(Date refundTime) {
    this.refundTime = refundTime;
  }

	public String getFlag() {
    return this.flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
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

}
