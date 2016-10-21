package com.itsv.annotation.ratio.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.ratio.dao.RatioDao;
import com.itsv.annotation.ratio.vo.Ratio;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对比例表的业务操作
 *
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */
 @Service @Transactional 
public class RatioService extends BaseService {

  //数据访问层对象
  @Autowired
  private RatioDao ratioDao;

	/**
	 * 增加比例表
	 */
	public void add(Ratio ratio) {
		this.ratioDao.save(ratio);
	}

	/**
	 * 修改比例表
	 */
	public void update(Ratio ratio) {
		this.ratioDao.update(ratio);
	}

	/**
	 * 删除比例表
	 */
	public void delete(Serializable id) {
		this.ratioDao.removeById(id);
	}

	/**
	 * 根据ID查询比例表的详细信息
	 */
	public Ratio queryById(Serializable ratioid) {
		return this.ratioDao.get(ratioid);
	}

	/**
	 * 获取所有的比例表对象
	 */
	public List<Ratio> queryAll() {
		return this.ratioDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Ratio> queryByVO(Ratio ratio) {
		return this.ratioDao.queryByObject(ratio);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Ratio ratio) {
		return this.ratioDao.queryByObject(records, ratio);
	}

}