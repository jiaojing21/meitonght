package com.itsv.annotation.voucherwith.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.voucherwith.dao.VoucherWithDao;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对代金券详细的业务操作
 *
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Service @Transactional 
public class VoucherWithService extends BaseService {

  //数据访问层对象
  @Autowired
  private VoucherWithDao voucherWithDao;

	/**
	 * 增加代金券详细
	 */
	public void add(VoucherWith voucherWith) {
		this.voucherWithDao.save(voucherWith);
	}

	/**
	 * 修改代金券详细
	 */
	public void update(VoucherWith voucherWith) {
		this.voucherWithDao.update(voucherWith);
	}

	/**
	 * 删除代金券详细
	 */
	public void delete(Serializable id) {
		this.voucherWithDao.removeById(id);
	}

	/**
	 * 根据ID查询代金券详细的详细信息
	 */
	public VoucherWith queryById(Serializable voucherWithid) {
		return this.voucherWithDao.get(voucherWithid);
	}

	/**
	 * 获取所有的代金券详细对象
	 */
	public List<VoucherWith> queryAll() {
		return this.voucherWithDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<VoucherWith> queryByVO(VoucherWith voucherWith) {
		return this.voucherWithDao.queryByObject(voucherWith);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, VoucherWith voucherWith) {
		return this.voucherWithDao.queryByObject(records, voucherWith);
	}

}