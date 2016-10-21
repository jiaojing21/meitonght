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

public class PolyDate implements Serializable {

	private String id;

	private String datetime; //��ҵ��

	private String bysj; //��������

	private String ljsj; //�ۼ�����
    

	/** ����Ϊget,set���� */
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getBysj() {
		return bysj;
	}

	public void setBysj(String bysj) {
		this.bysj = bysj;
	}

	public String getLjsj() {
		return ljsj;
	}

	public void setLjsj(String ljsj) {
		this.ljsj = ljsj;
	}


	
}
