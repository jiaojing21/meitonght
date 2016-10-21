package com.itsv.gbp.core.admin.vo;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵����Ӧ����־
 * 
 * @author admin
 *  
 */
public class AppLog extends BaseEntity {

	private static final long serialVersionUID = -64041960935694823L;

	private String userId; //�����û��ı��

	private String type;//��־����

	private Date time; //��¼��־ʱ��

	private String info; //��־����

	/**
	 * �չ��췽��
	 */
	public AppLog() {
	}

	/**
	 * ���ڿ��ټ�¼��־�ķ���
	 */
	public AppLog(String userid, String type, String info) {
		this.userId = userid;
		this.type = type;
		this.info = info;
		this.time = Calendar.getInstance().getTime(); //�Զ���ȡʱ��
	}

	/** ����Ϊget,set���� */

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, TOSTRING_STYLE, false, BaseEntity.class);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}