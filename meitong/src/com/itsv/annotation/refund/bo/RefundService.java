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
 * 说明：处理对refund的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class RefundService extends BaseService {

  //数据访问层对象
  @Autowired
  private RefundDao refundDao;

	/**
	 * 增加refund
	 */
	public void add(Refund refund) {
		this.refundDao.save(refund);
	}

	/**
	 * 修改refund
	 */
	public void update(Refund refund) {
		this.refundDao.update(refund);
	}

	/**
	 * 删除refund
	 */
	public void delete(Serializable id) {
		this.refundDao.removeById(id);
	}

	/**
	 * 根据ID查询refund的详细信息
	 */
	public Refund queryById(Serializable refundid) {
		return this.refundDao.get(refundid);
	}

	/**
	 * 获取所有的refund对象
	 */
	public List<Refund> queryAll() {
		return this.refundDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Refund> queryByVO(Refund refund) {
		return this.refundDao.queryByObject(refund);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Refund refund) {
		return this.refundDao.queryByObject(records, refund);
	}

}