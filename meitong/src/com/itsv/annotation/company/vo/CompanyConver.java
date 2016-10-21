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
 * ˵���� ��ҵ ֵ������
 * 
 * @author quyf
 * @since 2014-10-23
 * @version 1.0
 */

public class CompanyConver implements Serializable {

	private String id;

	private String companyname; //��ҵ��

	private String companyx; //γ��

	private String companyy; //����
    
	private String type; //���� 1��ҵ ��2����
	
	private String LDPEtype;
	
	private String HDPEtype;
	
	private String LLDPEtype;

	/** ����Ϊget,set���� */
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
