package com.itsv.platform.common.messageMgr.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.platform.common.messageDetail.dao.Itsv_message_receiveDao;
import com.itsv.platform.common.messageDetail.vo.Itsv_message_receive;
import com.itsv.platform.common.messageMgr.dao.Itsv_messageDao;
import com.itsv.platform.common.messageMgr.vo.Itsv_message;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对消息的业务操作
 *
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_messageService extends BaseService {

  //数据访问层对象
  private Itsv_messageDao itsv_messageDao;
  private Itsv_message_receiveDao itsv_message_receiveDao;

    public String getMsgSenderId(){
    	return this.getUserId();
    }
	/**
	 * 增加消息
	 */
	public void add(Itsv_message itsv_message) {
		User user = this.getCurrentUser();
		itsv_message.setUserid((String)user.getId());
		itsv_message.setUsername(user.getRealName());
		this.itsv_messageDao.save(itsv_message);
	}

	public void addDetail(Itsv_message_receive itsv_message_receive) {
		itsv_message_receive.setSender_id(this.getUserId());
		itsv_message_receive.setSender_name(this.getCurrentUser().getRealName());
		this.itsv_message_receiveDao.save(itsv_message_receive);
	}

	/**
	 * 修改消息
	 */
	public void update(Itsv_message itsv_message) {
		this.itsv_messageDao.update(itsv_message);
	}

	/**
	 * 删除消息
	 */
	public void delete(Serializable id) {
		this.itsv_messageDao.removeById(id);
	}

	/**
	 * 根据ID查询消息的详细信息
	 */
	public Itsv_message queryById(Serializable itsv_messageid) {
		return this.itsv_messageDao.get(itsv_messageid);
	}

	/**
	 * 获取所有的消息对象
	 */
	public List<Itsv_message> queryAll() {
		return this.itsv_messageDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Itsv_message> queryByVO(Itsv_message itsv_message) {
		return this.itsv_messageDao.queryByObject(itsv_message);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Itsv_message itsv_message) {
		return this.itsv_messageDao.queryByObject(records, itsv_message);
	}

	public void setItsv_messageDao(Itsv_messageDao itsv_messageDao) {
		this.itsv_messageDao = itsv_messageDao;
	}

	public Itsv_message_receiveDao getItsv_message_receiveDao() {
		return itsv_message_receiveDao;
	}

	public void setItsv_message_receiveDao(
			Itsv_message_receiveDao itsv_message_receiveDao) {
		this.itsv_message_receiveDao = itsv_message_receiveDao;
	}
	
}