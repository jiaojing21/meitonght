package com.itsv.annotation.ratio.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� ������ ֵ������
 * 
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */

public class Ratio_Conver {

	private String id;

	private String title; //����

	private String time; //ʱ��

	private String type; //����

	private String picurl; //ͼƬ��ַ

	private String content; //����

	private String subtype; //�ӷ���
	
	private List<RatioSub_Conver> list;

    /** ����Ϊget,set���� */
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
