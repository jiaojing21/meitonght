package com.itsv.annotation.customer.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� customer ֵ������
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
	private String phone; //��ϵ�绰

    @Column(name="NICKNAME",length=50  )
	private String nickname; //�ǳ�

    @Column(name="REALNAME",length=50  )
	private String realname; //��ʵ����

    @Column(name="LOGINPASS",length=50  )
	private String loginpass; //��¼����

    @Column(name="PAYPASS",length=50  )
	private String paypass; //֧������

    @Column(name="PLATFORM",length=50  )
	private String platform; //��¼ƽ̨

    @Column(name="LOGINCODE",length=100  )
	private String logincode; //��ݵ�½�˺�

    @Column(name="SEX",length=10  )
	private String sex; //�Ա�

    @Column(name="BIRTHDAY",length=20  )
	private String birthday; //����

	@Column(name="CASH",length=20  )
	private String cash; //�˻����

    @Column(name="REMARK",length=200  )
	private String remark; //��ע

    @Column(name="REMARK_1",length=50  )
	private String remark1; //remark_1

    @Column(name="REMARK_2",length=50  )
	private String remark2; //remark_2
    
    @Column(name="CREATETIME",length=0  )
	private Date createTime; //ע��ʱ��
    


    /** ����Ϊget,set���� */
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
