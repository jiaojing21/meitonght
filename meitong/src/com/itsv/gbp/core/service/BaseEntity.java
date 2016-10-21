package com.itsv.gbp.core.service;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ʵ�������ࡣ�ṩ��һ��id���ԣ�����ͳһ�������Ψһ��ʶ��<br>
 * 
 * ���⣬��toString,equals,hashCode������������Ϊ���󷽷���ǿ���������ʵ�֡�<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-5 ����03:23:04
 * @version 1.0
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4328144574403963854L;

	protected static final ToStringStyle TOSTRING_STYLE = ToStringStyle.SIMPLE_STYLE;

	private String id;

	private AppendParam others = new AppendParam(); //��������

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AppendParam getOthers() {
		return others;
	}

	public void setOthers(AppendParam others) {
		this.others = others;
	}

	public abstract String toString();

	public abstract boolean equals(Object o);

	public abstract int hashCode();
}
