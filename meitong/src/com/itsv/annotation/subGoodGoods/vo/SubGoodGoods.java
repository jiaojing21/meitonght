package com.itsv.annotation.subGoodGoods.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： subgood_goods 值对象类
 * 
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */

 @Entity
 @Table(name="subgood_goods")
public class SubGoodGoods {
              @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="SUBGOODID",length=32  )
	private String subgoodId; //subgoodid

    @Column(name="GOODSID",length=32  )
	private String goodsId; //goodsid

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

	public String getSubgoodId() {
    return this.subgoodId;
  }

  public void setSubgoodId(String subgoodId) {
    this.subgoodId = subgoodId;
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
