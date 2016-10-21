package com.itsv.annotation.pictext.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： pictext 值对象类
 * 
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */

 @Entity
 @Table(name="pictext")
public class Pictext {
            @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="CREATETIME",length=0  )
	private Date createtime; //createtime

    @Column(name="USERID",length=32  )
	private String userId; //userid

    @Column(name="COMENT",length=2000  )
	private String coment; //coment

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

	public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

	public String getComent() {
    return this.coment;
  }

  public void setComent(String coment) {
    this.coment = coment;
  }

}
