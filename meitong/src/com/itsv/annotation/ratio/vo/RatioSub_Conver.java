package com.itsv.annotation.ratio.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 比例图子表 值对象类
 * 
 * @author quyf
 * @since 2014-12-30
 * @version 1.0
 */

public class RatioSub_Conver {
	private String id;

	private String rname; //地名

	private String dataone; //数据1

	private String datatwo; //数据2

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRname() {
    return this.rname;
  }

  public void setRname(String rname) {
    this.rname = rname;
  }

	public String getDataone() {
    return this.dataone;
  }

  public void setDataone(String dataone) {
    this.dataone = dataone;
  }

	public String getDatatwo() {
    return this.datatwo;
  }

  public void setDatatwo(String datatwo) {
    this.datatwo = datatwo;
  }

}
