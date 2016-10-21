package com.itsv.annotation.subject.vo;

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
 * 说明： subject 值对象类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="subject")
public class Subject {
                    @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="TITLE",length=255  )
	private String title; //标题

    @Column(name="USERNAME",length=50  )
	private String userName; //用户名

    @Column(name="SUBGOODID",length=32  )
	private String subGoodId; //专题相关id

	@Column(name="CREATETIME",length=0  )
	private Date createtime; //登记时间

    @Column(name="COMMENT",length=255  )
	private String comment; //专题介绍

    @Column(name="REMARK_1",length=50  )
	private String remark1; //浏览次数

    @Column(name="REMARK_2",length=50  )
	private String remark2; //remark_2

    @Transient
    private String user;//操作用户
    
    @Transient
    private String subGood;//专题相关的标题
    
    public String getSubGood() {
		return subGood;
	}

	public void setSubGood(String subGood) {
		this.subGood = subGood;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

	public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  @JsonSerialize(using=CustomDateSerializer.class) 
	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

	public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
  

  public String getSubGoodId() {
		return subGoodId;
	}

	public void setSubGoodId(String subGoodId) {
		this.subGoodId = subGoodId;
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
