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
 * 说明： vision 值对象类
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
	private String cusId; //用户id

    @Column(name="SEATOF",length=255  )
	private String seatof; //所在地区

    @Column(name="MOOD",length=2000  )
	private String mood; //心境

    @Column(name="CREATETIME",length=0  )
	private Date createtime; //添加时间

    @Column(name="AUDITSTATUS",length=2  )
	private String auditStatus; //状态

    @Column(name="USERID",length=32  )
	private String userId; //审核人

    @Column(name="AUDITTIME",length=0  )
	private Date auditTime; //审核时间

    @Transient
    private String zt;//状态
    public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	/** 以下为get,set方法 */
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
