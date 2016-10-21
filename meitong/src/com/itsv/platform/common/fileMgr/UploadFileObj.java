package com.itsv.platform.common.fileMgr;

/**
 * ˵�����������Ӧ�����ö���һ��Ӧ����Ҫ����һ������
 * 
 * @author milu
 * @since 2007-07-14
 * @version 1.0
 */
public class UploadFileObj {

	// �ļ��洢����Ŀ¼
	private String root;

	// �洢���������
	private String cclbbh_pk;

	// ����Ŀ¼����
	private String ejmlgz;

	// �Ƿ����
	private Long isencrypt;

	// �Ƿ�ѹ��
	private Long iszip;

	// Ĭ�Ϲ��췽��
	public UploadFileObj() {
	}
	
	//��չ���췽��
	public UploadFileObj(String root, String ejmlgz, String cclbbh_pk, Long iszip, Long isencrypt) {
		this.root = root;
		this.cclbbh_pk = cclbbh_pk;
		this.ejmlgz = ejmlgz;
		this.iszip = iszip;
		this.isencrypt = isencrypt;
	}

	/** ����Ϊset,get���� */
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