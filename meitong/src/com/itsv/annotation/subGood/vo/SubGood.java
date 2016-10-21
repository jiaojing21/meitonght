package com.itsv.annotation.subGood.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： subgood 值对象类
 * 
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */

 @Entity
 @Table(name="subgood")
public class SubGood {
              @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="TITLE",length=200  )
	private String title; //title

    @Column(name="COMMENT",length=200  )
	private String comment; //comment

    @Column(name="REMARK_1",length=100  )
	private String remark1; //remark_1

    @Column(name="REMARK_2",length=100  )
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

	public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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
