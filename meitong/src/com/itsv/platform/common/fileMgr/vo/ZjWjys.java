package com.itsv.platform.common.fileMgr.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

public class ZjWjys extends BaseEntity {

	private static final long serialVersionUID = -8249918714114263007L;

	private String ysbhPk; /* ӳ���� */

	private String cclbbhPk; /* �ļ������ */

	private String wybs; /* Ψһ��ʶ */

	private String wjmc; /* �ļ����� */

	private String wjzt; /* �ļ�״̬ */

	private String cclj; /* �洢·�� */

	private String cjsj; /* ����ʱ�� */

	private String scxgsj; /* �ϴ��޸�ʱ�� */

	private Long iszip; /* �Ƿ�ѹ�� */

	private Long isencrypt; /* �Ƿ���� */

	private String bz; /* ��ע */

	private String ywbz; /* ҵ��ע */

	private String xmFk; /* �����ֶ� */

	public ZjWjys() {
	}

	public ZjWjys(String cclbbhPk, String wybs, String wjmc, String wjzt,
			String cclj, String cjsj, String scxgsj, String bz, String ywbz,
			String xmFk) {
		this.cclbbhPk = cclbbhPk;
		this.wybs = wybs;
		this.wjmc = wjmc;
		this.wjzt = wjzt;
		this.cclj = cclj;
		this.cjsj = cjsj;
		this.scxgsj = scxgsj;
		this.bz = bz;
		this.ywbz = ywbz;
		this.xmFk = xmFk;
	}

	public String getYsbhPk() {
		return this.ysbhPk;
	}

	public void setYsbhPk(String ysbhPk) {
		this.ysbhPk = ysbhPk;
	}

	public String getCclbbhPk() {
		return this.cclbbhPk;
	}

	public void setCclbbhPk(String cclbbhPk) {
		this.cclbbhPk = cclbbhPk;
	}

	public String getWybs() {
		return this.wybs;
	}

	public void setWybs(String wybs) {
		this.wybs = wybs;
	}

	public String getWjmc() {
		return this.wjmc;
	}

	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}

	public String getWjzt() {
		return this.wjzt;
	}

	public void setWjzt(String wjzt) {
		this.wjzt = wjzt;
	}

	public String getCclj() {
		return this.cclj;
	}

	public void setCclj(String cclj) {
		this.cclj = cclj;
	}

	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getScxgsj() {
		return this.scxgsj;
	}

	public void setScxgsj(String scxgsj) {
		this.scxgsj = scxgsj;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getYwbz() {
		return this.ywbz;
	}

	public void setYwbz(String ywbz) {
		this.ywbz = ywbz;
	}

	public String getXmFk() {
		return this.xmFk;
	}

	public void setXmFk(String xmFk) {
		this.xmFk = xmFk;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, TOSTRING_STYLE, false,
				BaseEntity.class);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public Long getIsencrypt() {
		return isencrypt;
	}

	public void setIsencrypt(Long isencrypt) {
		this.isencrypt = isencrypt;
	}

	public Long getIszip() {
		return iszip;
	}

	public void setIszip(Long iszip) {
		this.iszip = iszip;
	}

}