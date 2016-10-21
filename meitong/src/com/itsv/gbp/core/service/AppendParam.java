package com.itsv.gbp.core.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 额外的参数对象。<br>
 * VO对象除了基本属性，还需要一些辅助的属性，如更新对象时的旧主键值，查询时的起始和截至日期等。都可以放在这儿。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-5 下午04:40:36
 * @version 1.0
 */
public class AppendParam implements Serializable {

	private static final long serialVersionUID = 2848489515101422916L;

	private Integer intParam1;

	private Integer intParam2;

	private Double doubleParam1;

	private Double doubleParam2;

	private BigDecimal decimalParam1;

	private BigDecimal decimalParam2;

	private String strParam1;

	private String strParam2;

	private Date dateParam1;

	private Date dateParam2;

	public BigDecimal getDecimalParam1() {
		return decimalParam1;
	}

	public void setDecimalParam1(BigDecimal decimalParam1) {
		this.decimalParam1 = decimalParam1;
	}

	public BigDecimal getDecimalParam2() {
		return decimalParam2;
	}

	public void setDecimalParam2(BigDecimal decimalParam2) {
		this.decimalParam2 = decimalParam2;
	}

	public Double getDoubleParam1() {
		return doubleParam1;
	}

	public void setDoubleParam1(Double doubleParam1) {
		this.doubleParam1 = doubleParam1;
	}

	public Double getDoubleParam2() {
		return doubleParam2;
	}

	public void setDoubleParam2(Double doubleParam2) {
		this.doubleParam2 = doubleParam2;
	}

	public Integer getIntParam1() {
		return intParam1;
	}

	public void setIntParam1(Integer intParam1) {
		this.intParam1 = intParam1;
	}

	public Integer getIntParam2() {
		return intParam2;
	}

	public void setIntParam2(Integer intParam2) {
		this.intParam2 = intParam2;
	}

	public String getStrParam1() {
		return strParam1;
	}

	public void setStrParam1(String strParam1) {
		this.strParam1 = strParam1;
	}

	public String getStrParam2() {
		return strParam2;
	}

	public void setStrParam2(String strParam2) {
		this.strParam2 = strParam2;
	}

	public Date getDateParam1() {
		return dateParam1;
	}

	public void setDateParam1(Date dateParam1) {
		this.dateParam1 = dateParam1;
	}

	public Date getDateParam2() {
		return dateParam2;
	}

	public void setDateParam2(Date dateParam2) {
		this.dateParam2 = dateParam2;
	}
}
