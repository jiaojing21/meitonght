package com.itsv.annotation.goodCode.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： goodcode 值对象类
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */

 @Entity
 @Table(name="goodcode")
public class GoodCode {
          @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="GOODID",length=32  )
	private String goodId; //goodid

    @Column(name="CODE",length=100  )
	private String code; //code

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodId() {
    return this.goodId;
  }

  public void setGoodId(String goodId) {
    this.goodId = goodId;
  }

	public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
