package com.itsv.annotation.message.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.message.dao.MessageDao;
import com.itsv.annotation.message.vo.Message;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������message��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class MessageService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private MessageDao messageDao;

	/**
	 * ����message
	 */
	public void add(Message message) {
		this.messageDao.save(message);
	}

	/**
	 * �޸�message
	 */
	public void update(Message message) {
		this.messageDao.update(message);
	}

	/**
	 * ɾ��message
	 */
	public void delete(Serializable id) {
		this.messageDao.removeById(id);
	}

	/**
	 * ����ID��ѯmessage����ϸ��Ϣ
	 */
	public Message queryById(Serializable messageid) {
		return this.messageDao.get(messageid);
	}

	/**
	 * ��ȡ���е�message����
	 */
	public List<Message> queryAll() {
		return this.messageDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Message> queryByVO(Message message) {
		return this.messageDao.queryByObject(message);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Message message) {
		return this.messageDao.queryByObject(records, message);
	}

}