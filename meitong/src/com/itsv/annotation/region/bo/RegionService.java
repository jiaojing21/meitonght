package com.itsv.annotation.region.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.region.dao.RegionDao;
import com.itsv.annotation.region.vo.Region;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对region的业务操作
 *
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
 @Service @Transactional 
public class RegionService extends BaseService {

  //数据访问层对象
  @Autowired
  private RegionDao regionDao;

	/**
	 * 增加region
	 */
	public void add(Region region) {
		this.regionDao.save(region);
	}

	/**
	 * 修改region
	 */
	public void update(Region region) {
		this.regionDao.update(region);
	}

	/**
	 * 删除region
	 */
	public void delete(Serializable id) {
		this.regionDao.removeById(id);
	}

	/**
	 * 根据ID查询region的详细信息
	 */
	public Region queryById(Serializable regionid) {
		return this.regionDao.get(regionid);
	}

	/**
	 * 获取所有的region对象
	 */
	public List<Region> queryAll() {
		return this.regionDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Region> queryByVO(Region region) {
		return this.regionDao.queryByObject(region);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Region region) {
		return this.regionDao.queryByObject(records, region);
	}

}