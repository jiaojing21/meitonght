package com.itsv.annotation.company.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� ������ ֵ������
 * 
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */

public class ImpaexpConver implements Serializable {

	private String id;
	
	private String companyname; //����id
	
	private String companyid; //����id

	private String importsl; //������

	private String importse; //���ڶ�

	private String exportsl; //������

	private String exportse; //���ڶ�

	private String type; //����

	private String htime; //���

    

	/** ����Ϊget,set���� */
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

public String getCompanyname() {
	return companyname;
}

public void setCompanyname(String companyname) {
	this.companyname = companyname;
}
  

}
