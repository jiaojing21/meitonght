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
 * 说明：处理对refund_goods的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class RefundGoodsService extends BaseService {

  //数据访问层对象
  @Autowired
  private RefundGoodsDao refundGoodsDao;

	/**
	 * 增加refund_goods
	 */
	public void add(RefundGoods refundGoods) {
		this.refundGoodsDao.save(refundGoods);
	}

	/**
	 * 修改refund_goods
	 */
	public void update(RefundGoods refundGoods) {
		this.refundGoodsDao.update(refundGoods);
	}

	/**
	 * 删除refund_goods
	 */
	public void delete(Serializable id) {
		this.refundGoodsDao.removeById(id);
	}

	/**
	 * 根据ID查询refund_goods的详细信息
	 */
	public RefundGoods queryById(Serializable refundGoodsid) {
		return this.refundGoodsDao.get(refundGoodsid);
	}

	/**
	 * 获取所有的refund_goods对象
	 */
	public List<RefundGoods> queryAll() {
		return this.refundGoodsDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<RefundGoods> queryByVO(RefundGoods refundGoods) {
		return this.refundGoodsDao.queryByObject(refundGoods);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, RefundGoods refundGoods) {
		return this.refundGoodsDao.queryByObject(records, refundGoods);
	}

}