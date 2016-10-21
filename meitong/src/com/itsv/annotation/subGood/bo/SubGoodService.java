package com.itsv.annotation.subGood.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.subGood.dao.SubGoodDao;
import com.itsv.annotation.subGood.vo.SubGood;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对subgood的业务操作
 *
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubGoodService extends BaseService {

  //数据访问层对象
  @Autowired
  private SubGoodDao subGoodDao;

	/**
	 * 增加subgood
	 */
	public void add(SubGood subGood) {
		this.subGoodDao.save(subGood);
	}

	/**
	 * 修改subgood
	 */
	public void update(SubGood subGood) {
		this.subGoodDao.update(subGood);
	}

	/**
	 * 删除subgood
	 */
	public void delete(Serializable id) {
		this.subGoodDao.removeById(id);
	}

	/**
	 * 根据ID查询subgood的详细信息
	 */
	public SubGood queryById(Serializable subGoodid) {
		return this.subGoodDao.get(subGoodid);
	}

	/**
	 * 获取所有的subgood对象
	 */
	public List<SubGood> queryAll() {
		return this.subGoodDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<SubGood> queryByVO(SubGood subGood) {
		return this.subGoodDao.queryByObject(subGood);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, SubGood subGood) {
		return this.subGoodDao.queryByObject(records, subGood);
	}

}