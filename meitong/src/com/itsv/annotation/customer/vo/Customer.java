package com.itsv.annotation.customer.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： customer 值对象类
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */

 @Entity
 @Table(name="customer")
public class Customer {
                                @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="PHONE",length=11  )
	private String phone; //联系电话

    @Column(name="NICKNAME",length=50  )
	private String nickname; //昵称

    @Column(name="REALNAME",length=50  )
	private String realname; //真实姓名

    @Column(name="LOGINPASS",length=50  )
	private String loginpass; //登录密码

    @Column(name="PAYPASS",length=50  )
	private String paypass; //支付密码

    @Column(name="PLATFORM",length=50  )
	private String platform; //登录平台

    @Column(name="LOGINCODE",length=100  )
	private String logincode; //快捷登陆账号

    @Column(name="SEX",length=10  )
	private String sex; //性别

    @Column(name="BIRTHDAY",length=20  )
	private String birthday; //生日

	@Column(name="CASH",length=20  )
	private String cash; //账户余额

    @Column(name="REMARK",length=200  )
	private String remark; //备注

    @Column(name="REMARK_1",length=50  )
	private String remark1; //remark_1

    @Column(name="REMARK_2",length=50  )
	private String remark2; //remark_2
    
    @Column(name="CREATETIME",length=0  )
	private Date createTime; //注册时间
    


    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

	public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

	public String getRealname() {
    return this.realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

	public String getLoginpass() {
    return this.loginpass;
  }

  public void setLoginpass(String loginpass) {
    this.loginpass = loginpass;
  }

	public String getPaypass() {
    return this.paypass;
  }

  public void setPaypass(String paypass) {
    this.paypass = paypass;
  }

	public String getPlatform() {
    return this.platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

	public String getLogincode() {
    return this.logincode;
  }

  public void setLogincode(String logincode) {
    this.logincode = logincode;
  }

	public String getSex() {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

	public String getCash() {
    return this.cash;
  }

  public void setCash(String cash) {
    this.cash = cash;
  }

	public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  public String getBirthday() {
		return birthday;
	}

  public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

public Date getCreateTime() {
	return createTime;
}

public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
  
}
