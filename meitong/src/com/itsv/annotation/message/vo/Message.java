package com.itsv.annotation.message.vo;

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
 * 说明： message 值对象类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="message")
public class Message {
                      @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="TITLE",length=255  )
	private String title; //标题

    @Column(name="CONTENT",length=500  )
	private String content; //内容

    @Column(name="CREATETIME",length=0  )
	private Date createtime; //发送时间

    @Column(name="SENDER",nullable=true ,length=255  )
	private String sender; //发送人

    @Column(name="RECEVIER",length=500  )
	private String recevier; //接收人

    @Column(name="TYPE",length=10  )
	private String type; //消息类型

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

	public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

	public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  @JsonSerialize(using=CustomDateSerializer.class) 
	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

	public String getSender() {
    return this.sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

	public String getRecevier() {
    return this.recevier;
  }

  public void setRecevier(String recevier) {
    this.recevier = recevier;
  }

	public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
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
