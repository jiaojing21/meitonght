package com.itsv.gbp.core.web;

import java.io.Serializable;

/**
 * ˵��������������Ϣ�ࡣ�������κζ���ָ��Ϊ����ʾ
 * 
 * @author admin 2005-1-27
 */
public class TreeConfig implements Serializable {

	private static final long serialVersionUID = 6627366711029134255L;

	private String title; // ������ʾ����

	private boolean showCode; // �Ƿ�����ʾ������ʾ�����

	private String codeProp; // ��Ϊ������������

	private String textProp; // ��Ϊ��ʾ���ֵ�������

	private String levelProp; // ��Ϊ���ε�������

	private String remark1Prop; // ��ע1��������

	private String remark2Prop; // ��ע2��������

	private String remark3Prop; // ��ע3��������

	private String remark4Prop; // ��ע4��������

	private String remark5Prop; // ��ע5��������

	private String statusProp; // ��Ϊ״̬(�Ƿ����)��������

	/**
	 * ��λ��ָ��ֵ�Ľڵ㡣�����������<br>
	 * <ul>
	 * <li>���ֵΪNull���򲻽��ж�λ</li>
	 * <li>���ֵΪ���ַ���""����λ����һ���׼��ڵ� </li>
	 * <li>���ֵΪ��������λ��ָ��ֵ�Ľڵ㡣</li>
	 * </ul>
	 */
	private boolean returnLeaf = false;// �Ƿ�ֻ��ѡ��׼��ڵ�

	/**
	 * @deprecated �ѷ�����Ϊ��ֱ��������ڵ�Ĵ�����д��ҳ����
	 */
	private String functionContext;

	/** ����Ϊget,set���� */
	public String getCodeProp() {
		return codeProp;
	}

	public void setCodeProp(String codeProp) {
		this.codeProp = codeProp;
	}

	/**
	 * @deprecated �ѷ�����Ϊ��ֱ��������ڵ�Ĵ�����д��ҳ����
	 */
	public String getFunctionContext() {
		return functionContext;
	}

	/**
	 * @deprecated �ѷ�����Ϊ��ֱ��������ڵ�Ĵ�����д��ҳ����
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