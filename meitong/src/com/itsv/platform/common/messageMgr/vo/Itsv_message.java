package com.itsv.platform.common.messageMgr.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵���� ��Ϣ ֵ������
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message extends BaseEntity {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String create_time; // ����ʱ��

	private String sent_time; // ����ʱ��

	private Long msg_status; // ��Ϣ״̬

	private Long msg_type; // ��Ϣ����

	private String parent_msg_id; // ������Ϣ���

	private String msg_title; // ��Ϣ����

	private String msg_content; // ��Ϣ����

	private String file_ids; // ��������б�

	private String file_names; // ���������б�

	private String userid; // ������Ϣ��Ա���

	private String username; // ������Ϣ��Ա����

	private String userids; // �����ֶ�

	private String usernames; // �����ֶ�

	/** ����Ϊget,set���� */

	public String getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSent_time() {
		return this.sent_time;
	}

	public void setSent_time(String sent_time) {
		this.sent_time = sent_time;
	}

	public Long getMsg_status() {
		return this.msg_status;
	}

	public void setMsg_status(Long msg_status) {
		this.msg_status = msg_status;
	}

	public Long getMsg_type() {
		return this.msg_type;
	}

	public void setMsg_type(Long msg_type) {
		this.msg_type = msg_type;
	}

	public String getParent_msg_id() {
		return this.parent_msg_id;
	}

	public void setParent_msg_id(String parent_msg_id) {
		this.parent_msg_id = parent_msg_id;
	}

	public String getMsg_title() {
		return this.msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public String getMsg_content() {
		return this.msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public String getFile_ids() {
		return this.file_ids;
	}

	public void setFile_ids(String file_ids) {
		this.file_ids = file_ids;
	}

	public String getFile_names() {
		return this.file_names;
	}

	public void setFile_names(String file_names) {
		this.file_names = file_names;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getuserids() {
		return userids;
	}

	public void setuserids(String userids) {
		this.userids = userids;
	}

	public String getusernames() {
		return usernames;
	}

	public void setusernames(String usernames) {
		this.usernames = usernames;
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

}
