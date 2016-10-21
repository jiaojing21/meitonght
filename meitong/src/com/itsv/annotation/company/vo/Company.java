package com.itsv.annotation.company.vo;

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

 @Entity
 @Table(name="COMPANY")
public class Company {
    @Id
    @GenericGenerator(name="idGenerator", strategy = "uuid")
    @GeneratedValue(generator="idGenerator")
    @Column(name="ID")
	private String id;

    @Column(name="COMPANYNAME",length=50  )
	private String companyname; //企业名

    @Column(name="COMPANYX",length=50  )
	private String companyx; //纬度

    @Column(name="COMPANYY",length=50  )
	private String companyy; //经度
    
    @Column(name="TYPE",length=1  )
	private String type; //类型 1企业 ，2海关
    @Column(name="CODE",length=10  )
	private String code; //指数类型编码
    
    @Transient
    private String temp_ptime_htime; //辅助字段 时间_年份,共用
    
    //辅助字段,聚乙烯产能表
    @Transient
    private String temp_capacity_exportsl; //辅助字段 产能_出口量,共用
    @Transient
    private String temp_production_exportse; //辅助字段 产量_出口额,共用
    
    //辅助字段,进出口表
    @Transient
    private String temp_importsL;//辅助字段 进口量
    @Transient
    private String temp_importsE;//辅助字段 进口额
    
    

	

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTemp_ptime_htime() {
		return temp_ptime_htime;
	}

	public void setTemp_ptime_htime(String temp_ptime_htime) {
		this.temp_ptime_htime = temp_ptime_htime;
	}

	public String getTemp_capacity_exportsl() {
		return temp_capacity_exportsl;
	}

	public void setTemp_capacity_exportsl(String temp_capacity_exportsl) {
		this.temp_capacity_exportsl = temp_capacity_exportsl;
	}

	public String getTemp_production_exportse() {
		return temp_production_exportse;
	}

	public void setTemp_production_exportse(String temp_production_exportse) {
		this.temp_production_exportse = temp_production_exportse;
	}

	public String getTemp_importsL() {
		return temp_importsL;
	}

	public void setTemp_importsL(String temp_importsL) {
		this.temp_importsL = temp_importsL;
	}

	public String getTemp_importsE() {
		return temp_importsE;
	}

	public void setTemp_importsE(String temp_importsE) {
		this.temp_importsE = temp_importsE;
	}

}
