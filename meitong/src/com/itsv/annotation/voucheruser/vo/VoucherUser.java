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
 * ˵���� �û�����ȯ�� ֵ������
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
	private String voucherWithId; //����ȯ��ϸ����

    @Column(name="CUSID",nullable=true ,length=32  )
	private String cusId; //�û�������

    @Column(name="CODE",nullable=true ,length=16  )
	private String code; //����ȯΨһ��

    @Column(name="ACCESS",length=200  )
	private String access; //��ȡ;�����������û�ע�ᣩ

    @Column(name="FETCHTIME",length=0  )
	private Date fetchTime; //��ȡʱ��

    @Column(name="FAILURETIME",length=0  )
	private Date failureTime; //ʧЧʱ��

    @Column(name="TYPE",length=2  )
	private String type; //״̬��0��δ��ȡ��1������ȡ��

    /** ����Ϊget,set���� */
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
