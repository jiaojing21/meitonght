package com.itsv.annotation.logistics.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.logistics.dao.LogisticsDao;
import com.itsv.annotation.logistics.vo.Logistics;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对logistics的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class LogisticsService extends BaseService {

  //数据访问层对象
  @Autowired
  private LogisticsDao logisticsDao;

	/**
	 * 增加logistics
	 */
	public void add(Logistics logistics) {
		this.logisticsDao.save(logistics);
	}

	/**
	 * 修改logistics
	 */
	public void update(Logistics logistics) {
		this.logisticsDao.update(logistics);
	}

	/**
	 * 删除logistics
	 */
	public void delete(Serializable id) {
		this.logisticsDao.removeById(id);
	}
	
	/**
	 * 根据ID查询logistics的详细信息
	 */
	public Logistics queryById(Serializable logisticsid) {
		return this.logisticsDao.get(logisticsid);
	}

	/**
	 * 获取所有的logistics对象
	 */
	public List<Logistics> queryAll() {
		return this.logisticsDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Logistics> queryByVO(Logistics logistics) {
		return this.logisticsDao.queryByObject(logistics);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Logistics logistics) {
		return this.logisticsDao.queryByObject(records, logistics);
	}

}