package com.itsv.annotation.address.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： address 值对象类
 * 
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */

 @Entity
 @Table(name="address")
public class Address {
                        @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="CUSTOMERID",length=32  )
	private String customerId; //customerid

    @Column(name="CONSIGNEE",length=50  )
	private String consignee; //consignee

    @Column(name="CONTACT",length=11  )
	private String contact; //contact

    @Column(name="POSTCODE",length=255  )
	private String postCode; //postcode

    @Column(name="ADDRESS_REGION",length=32  )
	private String addressRegion; //address_region

    @Column(name="ADDRESS",length=500  )
	private String address; //address

    @Column(name="DETAILS",length=2000  )
	private String details; //details

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

	public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

	public String getConsignee() {
    return this.consignee;
  }

  public void setConsignee(String consignee) {
    this.consignee = consignee;
  }

	public String getContact() {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

	public String getPostCode() {
    return this.postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

	public String getAddressRegion() {
    return this.addressRegion;
  }

  public void setAddressRegion(String addressRegion) {
    this.addressRegion = addressRegion;
  }

	public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

	public String getDetails() {
    return this.details;
  }

  public void setDetails(String details) {
    this.details = details;
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
