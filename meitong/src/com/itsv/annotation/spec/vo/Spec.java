package com.itsv.annotation.spec.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： spec 值对象类
 * 
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */

@Entity
@Table(name = "spec")
public class Spec {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID")
	private String id;

    
    @Column(name="TYPE",length=200  )
	private String type; //产品类型（美瞳；隐形眼镜；护理液；配件）
    
    @Column(name="REMARK",length=200  )
	private String remark; //商品介绍
    
    @Column(name="SPECNAME",length=200  )
	private String specName; //商品名称
    
    @Column(name="BRANDCODE",length=32  )
   	private String brandCode; //品牌编码
    @Transient
    private String[] bcode;//品牌编码集合
    
	@Transient
    private String brand;//品牌
	@Transient
	private String sum;//同类商品的数量
	@Transient
	private String price;//价格
   
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** 以下为get,set方法 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String[] getBcode() {
		return bcode;
	}

	public void setBcode(String[] bcode) {
		this.bcode = bcode;
	}



}
