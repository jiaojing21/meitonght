package com.itsv.gbp.core.web;

import java.io.Serializable;

/**
 * 说明：树的配置信息类。用来将任何对象指定为树显示
 * 
 * @author admin 2005-1-27
 */
public class TreeConfig implements Serializable {

	private static final long serialVersionUID = 6627366711029134255L;

	private String title; // 树的显示标题

	private boolean showCode; // 是否在显示项里显示层次码

	private String codeProp; // 做为层次码的属性名

	private String textProp; // 做为显示文字的属性名

	private String levelProp; // 做为级次的属性名

	private String remark1Prop; // 备注1的属性名

	private String remark2Prop; // 备注2的属性名

	private String remark3Prop; // 备注3的属性名

	private String remark4Prop; // 备注4的属性名

	private String remark5Prop; // 备注5的属性名

	private String statusProp; // 作为状态(是否可用)的属性名

	/**
	 * 定位到指定值的节点。有三种情况：<br>
	 * <ul>
	 * <li>如果值为Null，则不进行定位</li>
	 * <li>如果值为空字符串""，则定位到第一个底级节点 </li>
	 * <li>如果值为其他，则定位到指定值的节点。</li>
	 * </ul>
	 */
	private boolean returnLeaf = false;// 是否只能选择底级节点

	/**
	 * @deprecated 已放弃。为了直观起见，节点的处理函数写在页面中
	 */
	private String functionContext;

	/** 以下为get,set方法 */
	public String getCodeProp() {
		return codeProp;
	}

	public void setCodeProp(String codeProp) {
		this.codeProp = codeProp;
	}

	/**
	 * @deprecated 已放弃。为了直观起见，节点的处理函数写在页面中
	 */
	public String getFunctionContext() {
		return functionContext;
	}

	/**
	 * @deprecated 已放弃。为了直观起见，节点的处理函数写在页面中
	 */
	public void setFunctionContext(String functionContext) {
		this.functionContext = functionContext;
	}

	public String getLevelProp() {
		return levelProp;
	}

	public void setLevelProp(String levelProp) {
		this.levelProp = levelProp;
	}

	public String getRemark1Prop() {
		return remark1Prop;
	}

	public void setRemark1Prop(String remark1Prop) {
		this.remark1Prop = remark1Prop;
	}

	public String getRemark2Prop() {
		return remark2Prop;
	}

	public void setRemark2Prop(String remark2Prop) {
		this.remark2Prop = remark2Prop;
	}

	public String getRemark3Prop() {
		return remark3Prop;
	}

	public void setRemark3Prop(String remark3Prop) {
		this.remark3Prop = remark3Prop;
	}

	public String getRemark4Prop() {
		return remark4Prop;
	}

	public void setRemark4Prop(String remark4Prop) {
		this.remark4Prop = remark4Prop;
	}

	public String getRemark5Prop() {
		return remark5Prop;
	}

	public void setRemark5Prop(String remark5Prop) {
		this.remark5Prop = remark5Prop;
	}

	public boolean isShowCode() {
		return showCode;
	}

	public void setShowCode(boolean showCode) {
		this.showCode = showCode;
	}

	public String getTextProp() {
		return textProp;
	}

	public void setTextProp(String textProp) {
		this.textProp = textProp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatusProp() {
		return statusProp;
	}

	public void setStatusProp(String statusProp) {
		this.statusProp = statusProp;
	}

	public boolean isReturnLeaf() {
		return returnLeaf;
	}

	public void setReturnLeaf(boolean returnLeaf) {
		this.returnLeaf = returnLeaf;
	}

}