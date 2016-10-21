package com.itsv.platform.common.messageDetail.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.itsv.gbp.core.service.BaseEntity;

/**
 * ˵���� ������Ϣ ֵ������
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message_receive extends BaseEntity {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String msg_id; //��Ϣ���ݱ��

	private String msg_title; //������Ϣ����
	
	private String receiver_id; //������Ա���
	
	private String receiver_name; //������Ա����

	private Long receive_status; //����״̬

	private String receive_time; //����ʱ��

	private String last_view_time; //���鿴ʱ��

	private Long view_times; //�鿴����

	private String sender_id; //������Ա���
	
	private String sender_name; //������Ա����

    /** ����Ϊget,set���� */

	public String getMsg_id() {
    return this.msg_id;
  }

  public void setMsg_id(String msg_id) {
    this.msg_id = msg_id;
  }

	public String getReceiver_id() {
    return this.receiver_id;
  }

  public void setReceiver_id(String receiver_id) {
    this.receiver_id = receiver_id;
  }

	public Long getReceive_status() {
    return this.receive_status;
  }

  public void setReceive_status(Long receive_status) {
    this.receive_status = receive_status;
  }

	public String getReceive_time() {
    return this.receive_time;
  }

  public void setReceive_time(String receive_time) {
    this.receive_time = receive_time;
  }

	public String getLast_view_time() {
    return this.last_view_time;
  }

  public void setLast_view_time(String last_view_time) {
    this.last_view_time = last_view_time;
  }

	public Long getView_times() {
    return this.view_times;
  }

  public void setView_times(Long view_times) {
    this.view_times = view_times;
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

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getSender_name() {
		return sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

}
