package com.itsv.annotation.company.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 企业 值对象类
 * 
 * @author quyf
 * @since 2014-10-23
 * @version 1.0
 */

public class CompanyConver implements Serializable {

	private String id;

	private String companyname; //企业名

	private String companyx; //纬度

	private String companyy; //经度
    
	private String type; //类型 1企业 ，2海关
	
	private String LDPEtype;
	
	private String HDPEtype;
	
	private String LLDPEtype;

	/** 以下为get,set方法 */
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyx() {
		return this.companyx;
	}

	public void setCompanyx(String companyx) {
    this.companyx = companyx;
	}

	public String getCompanyy() {
		return this.companyy;
	}

	public void setCompanyy(String companyy) {
		this.companyy = companyy;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLDPEtype() {
		return LDPEtype;
	}

	public void setLDPEtype(String lDPEtype) {
		LDPEtype = lDPEtype;
	}

	public String getHDPEtype() {
		return HDPEtype;
	}

	public void setHDPEtype(String hDPEtype) {
		HDPEtype = hDPEtype;
	}

	public String getLLDPEtype() {
		return LLDPEtype;
	}

	public void setLLDPEtype(String lLDPEtype) {
		LLDPEtype = lLDPEtype;
	}

	
	
}
