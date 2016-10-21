package com.itsv.annotation.voucheruser.vo;

import java.util.Date;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 用户代金券表 值对象类
 * 
 * @author yfh
 * @since 2016-04-21
 * @version 1.0
 */

 @Entity
 @Table(name="voucheruser")
public class VoucherUser {
                    @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="VOUCHERWITHID",nullable=true ,length=32  )
	private String voucherWithId; //代金券详细主键

    @Column(name="CUSID",nullable=true ,length=32  )
	private String cusId; //用户表主键

    @Column(name="CODE",nullable=true ,length=16  )
	private String code; //代金券唯一码

    @Column(name="ACCESS",length=200  )
	private String access; //获取途径（例：新用户注册）

    @Column(name="FETCHTIME",length=0  )
	private Date fetchTime; //获取时间

    @Column(name="FAILURETIME",length=0  )
	private Date failureTime; //失效时间

    @Column(name="TYPE",length=2  )
	private String type; //状态（0：未领取；1：已领取）

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVoucherWithId() {
    return this.voucherWithId;
  }

  public void setVoucherWithId(String voucherWithId) {
    this.voucherWithId = voucherWithId;
  }

	public String getCusId() {
    return this.cusId;
  }

  public void setCusId(String cusId) {
    this.cusId = cusId;
  }

	public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

	public String getAccess() {
    return this.access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

	public Date getFetchTime() {
    return this.fetchTime;
  }

  public void setFetchTime(Date fetchTime) {
    this.fetchTime = fetchTime;
  }

	public Date getFailureTime() {
    return this.failureTime;
  }

  public void setFailureTime(Date failureTime) {
    this.failureTime = failureTime;
  }

	public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
