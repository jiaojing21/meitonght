package com.itsv.annotation.subjectGoods.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： subject_goods 值对象类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="subject_goods")
public class SubjectGoods {
              @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="SUBJECTID",length=32  )
	private String subjectId; //subjectid

    @Column(name="GOODSID",length=32  )
	private String goodsId; //goodsid

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

	public String getSubjectId() {
    return this.subjectId;
  }

  public void setSubjectId(String subjectId) {
    this.subjectId = subjectId;
  }

	public String getGoodsId() {
    return this.goodsId;
  }

  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
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
