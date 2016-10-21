package com.itsv.annotation.singleProductPic.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.itsv.annotation.util.CustomDateSerializer;

/**
 * ˵���� single_product_pic ֵ������
 * 
 * @author swk
 * @since 2016-04-11
 * @version 1.0
 */

 @Entity
 @Table(name="single_product_pic")
public class SingleProductPic {
        @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="CREATETIME",nullable=true ,length=0  )
	private Date createtime; //����ʱ��

    /** ����Ϊget,set���� */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

}
