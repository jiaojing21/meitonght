package com.itsv.annotation.voucher.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 代金券 值对象类
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */

 @Entity
 @Table(name="voucher")
public class Voucher {
          @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="WORTH",length=10  )
	private String worth; //代金券价值

    @Column(name="TERM",length=10  )
	private String term; //期限
    
    @Column(name="CREATETIME",length=0  )
	private Date createtime; //添加时间
    
    @Column(name="CREATEUSER",length=32  )
	private String createuser; //添加人
    
    @Transient
	private String amount; //数量
    

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorth() {
    return this.worth;
  }

  public void setWorth(String worth) {
    this.worth = worth;
  }

	public String getTerm() {
    return this.term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

public String getAmount() {
	return amount;
}

public void setAmount(String amount) {
	this.amount = amount;
}



public Date getCreatetime() {
	return createtime;
}

public void setCreatetime(Date createtime) {
	this.createtime = createtime;
}

public String getCreateuser() {
	return createuser;
}

public void setCreateuser(String createuser) {
	this.createuser = createuser;
}
  

}
