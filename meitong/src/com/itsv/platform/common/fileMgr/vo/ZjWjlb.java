package com.itsv.platform.common.fileMgr.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

public class ZjWjlb extends BaseEntity {

	private static final long serialVersionUID = -8800047787904997983L;

	private String cclbbhPk; /* 文件类别编号 */

	private String cclbmc; /* 存储类别名称 */

	private Long lbmcbb; /* 类别名称版本 */

	private String ccgml; /* 存储根目录 */

	private String ejmlgz; /* 二级目录规则 */

	private String gzfzzd; /* 规则辅助字段 */
	
	private Long iszip; /* 是否压缩 */

	private Long isencrypt; /* 是否加密 */

	private String zt; /* 状态 */

	private String bz; /* 备注 */

	private String xmFk; /* 冗余字段 */

	public ZjWjlb() {
	}

	public ZjWjlb(String cclbmc, Long lbmcbb, String ccgml, String ejmlgz,
			String gzfzzd, String zt, String bz, String xmFk) {
		this.cclbmc = cclbmc;
		this.lbmcbb = lbmcbb;
		this.ccgml = ccgml;
		this.ejmlgz = ejmlgz;
		this.gzfzzd = gzfzzd;
		this.zt = zt;
		this.bz = bz;
		this.xmFk = xmFk;
	}

	public String getCclbbhPk() {
		return this.cclbbhPk;
	}

	public void setCclbbhPk(String cclbbhPk) {
		this.cclbbhPk = cclbbhPk;
	}

	public String getCclbmc() {
		return this.cclbmc;
	}

	public void setCclbmc(String cclbmc) {
		this.cclbmc = cclbmc;
	}

	public Long getLbmcbb() {
		return this.lbmcbb;
	}

	public void setLbmcbb(Long lbmcbb) {
		this.lbmcbb = lbmcbb;
	}

	public String getCcgml() {
		return this.ccgml;
	}

	public void setCcgml(String ccgml) {
		this.ccgml = ccgml;
	}

	public String getEjmlgz() {
		return this.ejmlgz;
	}

	public void setEjmlgz(String ejmlgz) {
		this.ejmlgz = ejmlgz;
	}

	public String getGzfzzd() {
		return this.gzfzzd;
	}

	public void setGzfzzd(String gzfzzd) {
		this.gzfzzd = gzfzzd;
	}

	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	public Long getIszip() {
		return iszip;
	}

	public void setIszip(Long iszip) {
		this.iszip = iszip;
	}

	public Long getIsencrypt() {
		return isencrypt;
	}

	public void setIsencrypt(Long isencrypt) {
		this.isencrypt = isencrypt;
	}

}