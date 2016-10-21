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

 @Entity
 @Table(name="RATIO_SUB")
public class RatioSub {
              @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="RATIOID",length=32  )
	private String ratioid; //ratioid

    @Column(name="RNAME",length=50  )
	private String rname; //地名

    @Column(name="DATAONE",length=30  )
	private String dataone; //数据1

    @Column(name="DATATWO",length=30  )
	private String datatwo; //数据2
    
    @Column(name="PX",length=22  )
	private Long px; //px

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRatioid() {
    return this.ratioid;
  }

  public void setRatioid(String ratioid) {
    this.ratioid = ratioid;
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

public Long getPx() {
	return px;
}

public void setPx(Long px) {
	this.px = px;
}
  
}
