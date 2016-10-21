package com.itsv.annotation.goods.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� goods ֵ������
 * 
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */

@Entity
@Table(name = "goods")
public class Goods {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(name = "ID")
	private String id;

	@Column(name = "SPECID", length = 32)
	private String specId; // ��Ʒ����id

	@Column(name = "GOODNO", length = 50)
	private String goodNo; // ��Ʒ���

	@Column(name = "FLAG", length = 50)
	private String flag; // ��Ʒ��ǩ����Ʒ����ͨ..��

	@Column(name = "NAME", length = 200)
	private String name; // ����

	@Column(name = "GOODS_NUMBER", length = 50)
	private String goodsNumber; // ��Ʒ����

	@Column(name = "PRICE", length = 20)
	private String price; // ��Ʒ�۸�

	@Column(name = "REMARK_1", length = 50)
	private String remark1; // remark_1

	@Column(name = "REMARK_2", length = 50)
	private String remark2; // remark_2
	
	@Column(name = "STATE", length = 2)
	private String state; //״̬:���ύ:0;���ύ�ȴ����:1;���������ϼ�:2;�¼ܵȴ����:3;�¼����������¼�:4;�����ϼܵȴ����:5;
	
	@Transient
	private String pinlei; //Ʒ��
	
	@Transient
	private String brand;//Ʒ��

	@Transient
	private String sum;//ͬ����Ʒ��������
	
	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	/** ����Ϊget,set���� */
	
	public String getId() {
		return id;
	}

	public String getPinlei() {
		return pinlei;
	}

	public void setPinlei(String pinlei) {
		this.pinlei = pinlei;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpecId() {
		return this.specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getGoodNo() {
		return goodNo;
	}

	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsNumber() {
		return this.goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
