package com.itsv.annotation.region.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： region 值对象类
 * 
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */

 @Entity
 @Table(name="region")
public class Region {
                    @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="REGION_CODE",nullable=true ,length=100  )
	private String regionCode; //region_code

    @Column(name="REGION_NAME",nullable=true ,length=100  )
	private String regionName; //region_name

    @Column(name="PARENT_ID",nullable=true ,length=22  )
	private String parentId; //parent_id

    @Column(name="REGION_LEVEL",nullable=true ,length=22  )
	private String regionLevel; //region_level

    @Column(name="REGION_ORDER",nullable=true ,length=22  )
	private String regionOrder; //region_order

    @Column(name="REGION_NAME_EN",nullable=true ,length=100  )
	private String regionNameEn; //region_name_en

    @Column(name="REGION_SHORTNAME_EN",nullable=true ,length=10  )
	private String regionShortnameEn; //region_shortname_en

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegionCode() {
    return this.regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

	public String getRegionName() {
    return this.regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

	

	public String getParentId() {
	return parentId;
}

public void setParentId(String parentId) {
	this.parentId = parentId;
}

public String getRegionLevel() {
	return regionLevel;
}

public void setRegionLevel(String regionLevel) {
	this.regionLevel = regionLevel;
}

public String getRegionOrder() {
	return regionOrder;
}

public void setRegionOrder(String regionOrder) {
	this.regionOrder = regionOrder;
}

	public String getRegionNameEn() {
    return this.regionNameEn;
  }

  public void setRegionNameEn(String regionNameEn) {
    this.regionNameEn = regionNameEn;
  }

	public String getRegionShortnameEn() {
    return this.regionShortnameEn;
  }

  public void setRegionShortnameEn(String regionShortnameEn) {
    this.regionShortnameEn = regionShortnameEn;
  }

}
