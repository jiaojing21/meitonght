package com.itsv.annotation.vision.vo;

import java.util.Date;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.itsv.annotation.util.CustomDateSerializer;

/**
 * ˵���� vision ֵ������
 * 
 * @author swk
 * @since 2016-05-04
 * @version 1.0
 */

 @Entity
 @Table(name="vision")
public class Vision {
                    @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="CUSID",nullable=true ,length=32  )
	private String cusId; //�û�id

    @Column(name="SEATOF",length=255  )
	private String seatof; //���ڵ���

    @Column(name="MOOD",length=2000  )
	private String mood; //�ľ�

    @Column(name="CREATETIME",length=0  )
	private Date createtime; //���ʱ��

    @Column(name="AUDITSTATUS",length=2  )
	private String auditStatus; //״̬

    @Column(name="USERID",length=32  )
	private String userId; //�����

    @Column(name="AUDITTIME",length=0  )
	private Date auditTime; //���ʱ��

    @Transient
    private String zt;//״̬
    public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	/** ����Ϊget,set���� */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCusId() {
    return this.cusId;
  }

  public void setCusId(String cusId) {
    this.cusId = cusId;
  }

	public String getSeatof() {
    return this.seatof;
  }

  public void setSeatof(String seatof) {
    this.seatof = seatof;
  }

	public String getMood() {
    return this.mood;
  }

  public void setMood(String mood) {
    this.mood = mood;
  }
  @JsonSerialize(using=CustomDateSerializer.class) 
	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

	public String getAuditStatus() {
    return this.auditStatus;
  }

  public void setAuditStatus(String auditStatus) {
    this.auditStatus = auditStatus;
  }

	public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

	public Date getAuditTime() {
    return this.auditTime;
  }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

}
