package com.itsv.annotation.orders.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� orders ֵ������
 * 
 * @author swk
 * @since 2016-04-14
 * @version 1.0
 */

 @Entity
 @Table(name="orders")
public class Orders {
                                @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="CUSTOMERID",length=32  )
	private String customerId; //�ͻ�id

    @Column(name="ORDER_NUMBER",length=32  )
	private String orderNumber; //������

    @Column(name="ADDRESS",length=500  )
	private String address; //��ַ

    @Column(name="PAYMENT",length=100  )
	private String payment; //֧����ʽ

    @Column(name="PAYPLATFORM",length=100  )
	private String payPlatform; //֧��ƽ̨

    @Column(name="CREATETIME",length=0  )
	private Date createtime; //��������ʱ��

    @Column(name="PAYTIME",length=0  )
	private Date payTime; //֧��ʱ��

    @Column(name="CONFIRMATTIME",length=0  )
	private Date confirmatTime; //ȷ�϶���ʱ��

    @Column(name="STATUS",length=10  )
	private String status; //����״̬;0:δ���;1:��֧��,2:ȷ�϶���,3:����,4:�������,5:ȡ������(ȡ���Ķ�������չʾ��app��

    @Column(name="FLAG",length=10  )
	private String flag; //flag

    @Column(name="TOTAL",length=50  )
	private String total; //�ܼ�

    @Column(name="REMARK_1",length=50  )
	private String remark1; //remark_1

    @Column(name="REMARK_2",length=50  )
	private String remark2; //remark_2

    /** ����Ϊget,set���� */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

	public String getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

	public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

	public String getPayment() {
    return this.payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

	public String getPayPlatform() {
    return this.payPlatform;
  }

  public void setPayPlatform(String payPlatform) {
    this.payPlatform = payPlatform;
  }

	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

	public Date getPayTime() {
    return this.payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

	public Date getConfirmatTime() {
    return this.confirmatTime;
  }

  public void setConfirmatTime(Date confirmatTime) {
    this.confirmatTime = confirmatTime;
  }

	public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

	public String getFlag() {
    return this.flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

	public String getTotal() {
    return this.total;
  }

  public void setTotal(String total) {
    this.total = total;
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
