package com.itsv.annotation.ratio.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 比例表 值对象类
 * 
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */

 @Entity
 @Table(name="RATIO")
public class Ratio {
                  @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="TITLE",length=32  )
	private String title; //标题

    @Column(name="TIME",length=32  )
	private String time; //时间

    @Column(name="TYPE",length=2  )
	private String type; //类型

    @Column(name="PICURL",length=200  )
	private String picurl; //图片地址

    @Column(name="CONTENT",length=4000  )
	private String content; //内容

    @Column(name="SUBTYPE",length=2  )
	private String subtype; //子分类

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

	public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

	public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

	public String getPicurl() {
    return this.picurl;
  }

  public void setPicurl(String picurl) {
    this.picurl = picurl;
  }

	public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

	public String getSubtype() {
    return this.subtype;
  }

  public void setSubtype(String subtype) {
    this.subtype = subtype;
  }

}
