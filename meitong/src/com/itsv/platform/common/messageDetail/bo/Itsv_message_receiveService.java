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
 * ˵��������Խ�����Ϣ��ҵ�����
 *
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message_receiveService extends BaseService {

	// ���ݷ��ʲ����
	private Itsv_message_receiveDao itsv_message_receiveDao;

	private Itsv_messageDao itsv_messageDao;

	public String getCurUserID(){
		return this.getUserId();
	}
	/**
	 * �鿴������Ϣ����
	 */
	public Itsv_message queryMsg(Itsv_message itsv_message) {
		return this.itsv_messageDao.queryByObject(itsv_message).get(0);
	}

	/**
	 * ���ӽ�����Ϣ
	 */
	public void add(Itsv_message_receive itsv_message_receive) {
		this.itsv_message_receiveDao.save(itsv_message_receive);
	}

	/**
	 * �޸Ľ�����Ϣ
	 */
	public void update(Itsv_message_receive itsv_message_receive) {
		this.itsv_message_receiveDao.update(itsv_message_receive);
	}

	/**
	 * ɾ��������Ϣ
	 */
	public void delete(Serializable id) {
		this.itsv_message_receiveDao.removeById(id);
	}

	/**
	 * ����ID��ѯ������Ϣ����ϸ��Ϣ
	 */
	public Itsv_message_receive queryById(Serializable itsv_message_receiveid) {
		return this.itsv_message_receiveDao.get(itsv_message_receiveid);
	}

	/**
	 * ��ȡ���еĽ�����Ϣ����
	 */
	public List<Itsv_message_receive> queryAll() {
		return this.itsv_message_receiveDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Itsv_message_receive> queryByVO(
			Itsv_message_receive itsv_message_receive) {
		return this.itsv_message_receiveDao.queryByObject(itsv_message_receive);
	}

	/**
	 * ��������ķ�ҳ��ѯ
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