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
 * 说明：处理对message的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class MessageService extends BaseService {

  //数据访问层对象
  @Autowired
  private MessageDao messageDao;

	/**
	 * 增加message
	 */
	public void add(Message message) {
		this.messageDao.save(message);
	}

	/**
	 * 修改message
	 */
	public void update(Message message) {
		this.messageDao.update(message);
	}

	/**
	 * 删除message
	 */
	public void delete(Serializable id) {
		this.messageDao.removeById(id);
	}

	/**
	 * 根据ID查询message的详细信息
	 */
	public Message queryById(Serializable messageid) {
		return this.messageDao.get(messageid);
	}

	/**
	 * 获取所有的message对象
	 */
	public List<Message> queryAll() {
		return this.messageDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Message> queryByVO(Message message) {
		return this.messageDao.queryByObject(message);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Message message) {
		return this.messageDao.queryByObject(records, message);
	}

}