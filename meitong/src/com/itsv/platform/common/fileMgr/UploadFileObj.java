package com.itsv.platform.common.fileMgr;

/**
 * 说明：附件组件应用配置对象，一个应用需要创建一个对象。
 * 
 * @author milu
 * @since 2007-07-14
 * @version 1.0
 */
public class UploadFileObj {

	// 文件存储根级目录
	private String root;

	// 存储类别编号主键
	private String cclbbh_pk;

	// 二级目录规则
	private String ejmlgz;

	// 是否加密
	private Long isencrypt;

	// 是否压缩
	private Long iszip;

	// 默认构造方法
	public UploadFileObj() {
	}
	
	//扩展构造方法
	public UploadFileObj(String root, String ejmlgz, String cclbbh_pk, Long iszip, Long isencrypt) {
		this.root = root;
		this.cclbbh_pk = cclbbh_pk;
		this.ejmlgz = ejmlgz;
		this.iszip = iszip;
		this.isencrypt = isencrypt;
	}

	/** 以下为set,get方法 */
	public String getCclbbh_pk() {
		return cclbbh_pk;
	}

	public void setCclbbh_pk(String cclbbh_pk) {
		this.cclbbh_pk = cclbbh_pk;
	}

	public String getEjmlgz() {
		return ejmlgz;
	}

	public void setEjmlgz(String ejmlgz) {
		this.ejmlgz = ejmlgz;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
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