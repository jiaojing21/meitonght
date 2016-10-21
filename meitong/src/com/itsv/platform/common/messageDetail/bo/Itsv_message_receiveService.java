package com.itsv.platform.common.messageDetail.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.platform.common.messageDetail.dao.Itsv_message_receiveDao;
import com.itsv.platform.common.messageDetail.vo.Itsv_message_receive;
import com.itsv.platform.common.messageMgr.dao.Itsv_messageDao;
import com.itsv.platform.common.messageMgr.vo.Itsv_message;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对接收消息的业务操作
 *
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message_receiveService extends BaseService {

	// 数据访问层对象
	private Itsv_message_receiveDao itsv_message_receiveDao;

	private Itsv_messageDao itsv_messageDao;

	public String getCurUserID(){
		return this.getUserId();
	}
	/**
	 * 查看接收消息内容
	 */
	public Itsv_message queryMsg(Itsv_message itsv_message) {
		return this.itsv_messageDao.queryByObject(itsv_message).get(0);
	}

	/**
	 * 增加接收消息
	 */
	public void add(Itsv_message_receive itsv_message_receive) {
		this.itsv_message_receiveDao.save(itsv_message_receive);
	}

	/**
	 * 修改接收消息
	 */
	public void update(Itsv_message_receive itsv_message_receive) {
		this.itsv_message_receiveDao.update(itsv_message_receive);
	}

	/**
	 * 删除接收消息
	 */
	public void delete(Serializable id) {
		this.itsv_message_receiveDao.removeById(id);
	}

	/**
	 * 根据ID查询接收消息的详细信息
	 */
	public Itsv_message_receive queryById(Serializable itsv_message_receiveid) {
		return this.itsv_message_receiveDao.get(itsv_message_receiveid);
	}

	/**
	 * 获取所有的接收消息对象
	 */
	public List<Itsv_message_receive> queryAll() {
		return this.itsv_message_receiveDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Itsv_message_receive> queryByVO(
			Itsv_message_receive itsv_message_receive) {
		return this.itsv_message_receiveDao.queryByObject(itsv_message_receive);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records,
			Itsv_message_receive itsv_message_receive) {
		return this.itsv_message_receiveDao.queryByObject(records,
				itsv_message_receive);
	}

	public void setItsv_message_receiveDao(
			Itsv_message_receiveDao itsv_message_receiveDao) {
		this.itsv_message_receiveDao = itsv_message_receiveDao;
	}

	public Itsv_messageDao getItsv_messageDao() {
		return itsv_messageDao;
	}

	public void setItsv_messageDao(Itsv_messageDao itsv_messageDao) {
		this.itsv_messageDao = itsv_messageDao;
	}

}