package com.itsv.annotation.ratio.vo;

import java.util.List;

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

public class Ratio_Conver {

	private String id;

	private String title; //标题

	private String time; //时间

	private String type; //类型

	private String picurl; //图片地址

	private String content; //内容

	private String subtype; //子分类
	
	private List<RatioSub_Conver> list;

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

public List<RatioSub_Conver> getList() {
	return list;
}

public void setList(List<RatioSub_Conver> list) {
	this.list = list;
}
  
}
