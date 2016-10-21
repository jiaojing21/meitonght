package com.itsv.annotation.ordersGoods.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� orders_goods ֵ������
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="orders_goods")
public class OrdersGoods {
                  @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="ORDER_NUMBER",length=32  )
	private String orderNumber; //������

    @Column(name="GOODSID",length=32  )
	private String goodsId; //��Ʒid

    @Column(name="GOODS_NUMBER",length=20  )
	private String goodsNumber; //����

    @Column(name="GOODS_TOTAL",length=20  )
	private String goodsTotal; //�ܼ�

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

	public String getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

	public String getGoodsId() {
    return this.goodsId;
  }

  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }

	public String getGoodsNumber() {
    return this.goodsNumber;
  }

  public void setGoodsNumber(String goodsNumber) {
    this.goodsNumber = goodsNumber;
  }

	public String getGoodsTotal() {
    return this.goodsTotal;
  }

  public void setGoodsTotal(String goodsTotal) {
    this.goodsTotal = goodsTotal;
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
