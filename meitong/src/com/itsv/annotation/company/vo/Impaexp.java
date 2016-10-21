package com.itsv.annotation.company.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 进出口 值对象类
 * 
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */

 @Entity
 @Table(name="impaexp")
public class Impaexp {
                    @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="COMPANYID",length=32  )
	private String companyid; //海关id

    @Column(name="IMPORTSL",length=32  )
	private String importsl; //进口量

    @Column(name="IMPORTSE",length=32  )
	private String importse; //进口额

    @Column(name="EXPORTSL",length=32  )
	private String exportsl; //出口量

    @Column(name="EXPORTSE",length=32  )
	private String exportse; //出口额

    @Column(name="TYPE",length=32  )
	private String type; //类型

    @Column(name="HTIME",length=32  )
	private String htime; //年份

    

	/** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
	
	public String getImportsl() {
    return this.importsl;
  }

  public void setImportsl(String importsl) {
    this.importsl = importsl;
  }

	public String getImportse() {
    return this.importse;
  }

  public void setImportse(String importse) {
    this.importse = importse;
  }

	public String getExportsl() {
    return this.exportsl;
  }

  public void setExportsl(String exportsl) {
    this.exportsl = exportsl;
  }

	public String getExportse() {
    return this.exportse;
  }

  public void setExportse(String exportse) {
    this.exportse = exportse;
  }

	public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

	public String getHtime() {
    return this.htime;
  }

  public void setHtime(String htime) {
    this.htime = htime;
  }

}
