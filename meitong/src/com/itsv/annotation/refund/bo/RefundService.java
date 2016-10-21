package com.itsv.annotation.refund.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.refund.dao.RefundDao;
import com.itsv.annotation.refund.vo.Refund;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������refund��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class RefundService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private RefundDao refundDao;

	/**
	 * ����refund
	 */
	public void add(Refund refund) {
		this.refundDao.save(refund);
	}

	/**
	 * �޸�refund
	 */
	public void update(Refund refund) {
		this.refundDao.update(refund);
	}

	/**
	 * ɾ��refund
	 */
	public void delete(Serializable id) {
		this.refundDao.removeById(id);
	}

	/**
	 * ����ID��ѯrefund����ϸ��Ϣ
	 */
	public Refund queryById(Serializable refundid) {
		return this.refundDao.get(refundid);
	}

	/**
	 * ��ȡ���е�refund����
	 */
	public List<Refund> queryAll() {
		return this.refundDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Refund> queryByVO(Refund refund) {
		return this.refundDao.queryByObject(refund);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Refund refund) {
		return this.refundDao.queryByObject(records, refund);
	}

}