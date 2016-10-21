package com.itsv.annotation.refundGoods.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.refundGoods.dao.RefundGoodsDao;
import com.itsv.annotation.refundGoods.vo.RefundGoods;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������refund_goods��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class RefundGoodsService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private RefundGoodsDao refundGoodsDao;

	/**
	 * ����refund_goods
	 */
	public void add(RefundGoods refundGoods) {
		this.refundGoodsDao.save(refundGoods);
	}

	/**
	 * �޸�refund_goods
	 */
	public void update(RefundGoods refundGoods) {
		this.refundGoodsDao.update(refundGoods);
	}

	/**
	 * ɾ��refund_goods
	 */
	public void delete(Serializable id) {
		this.refundGoodsDao.removeById(id);
	}

	/**
	 * ����ID��ѯrefund_goods����ϸ��Ϣ
	 */
	public RefundGoods queryById(Serializable refundGoodsid) {
		return this.refundGoodsDao.get(refundGoodsid);
	}

	/**
	 * ��ȡ���е�refund_goods����
	 */
	public List<RefundGoods> queryAll() {
		return this.refundGoodsDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<RefundGoods> queryByVO(RefundGoods refundGoods) {
		return this.refundGoodsDao.queryByObject(refundGoods);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, RefundGoods refundGoods) {
		return this.refundGoodsDao.queryByObject(records, refundGoods);
	}

}