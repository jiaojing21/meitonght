package com.itsv.annotation.company.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� ����ϩ���ܱ� ֵ������
 * 
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */

 @Entity
 @Table(name="polytene")
public class Polytene {
                @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="COMPANYID",length=32  )
	private String companyid; //��ҵid

    @Column(name="CAPACITY",length=32  )
	private String capacity; //����

    @Column(name="PRODUCTION",length=32  )
	private String production; //����

    @Column(name="TYPE",length=32  )
	private String type; //����

    @Column(name="PTIME",length=32  )
	private String ptime; //ʱ��

    /** ����Ϊget,set���� */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyid() {
    return this.companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
  }

	public String getCapacity() {
    return this.capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

	public String getProduction() {
    return this.production;
  }

  public void setProduction(String production) {
    this.production = production;
  }

	public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

	public String getPtime() {
    return this.ptime;
  }

  public void setPtime(String ptime) {
    this.ptime = ptime;
  }

}
