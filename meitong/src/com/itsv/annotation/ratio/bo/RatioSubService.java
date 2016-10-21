package com.itsv.annotation.ratio.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.ratio.dao.RatioSubDao;
import com.itsv.annotation.ratio.vo.RatioSub;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对比例图子表的业务操作
 *
 * @author quyf
 * @since 2014-12-30
 * @version 1.0
 */
 @Service @Transactional 
public class RatioSubService extends BaseService {

  //数据访问层对象
  @Autowired
  private RatioSubDao ratioSubDao;

	/**
	 * 增加比例图子表
	 */
	public void add(RatioSub ratioSub) {
		this.ratioSubDao.save(ratioSub);
	}

	/**
	 * 修改比例图子表
	 */
	public void update(RatioSub ratioSub) {
		this.ratioSubDao.update(ratioSub);
	}

	/**
	 * 删除比例图子表
	 */
	public void delete(Serializable id) {
		this.ratioSubDao.removeById(id);
	}

	/**
	 * 根据ID查询比例图子表的详细信息
	 */
	public RatioSub queryById(Serializable ratioSubid) {
		return this.ratioSubDao.get(ratioSubid);
	}

	/**
	 * 获取所有的比例图子表对象
	 */
	public List<RatioSub> queryAll() {
		return this.ratioSubDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<RatioSub> queryByVO(RatioSub ratioSub) {
		return this.ratioSubDao.queryByObject(ratioSub);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, RatioSub ratioSub) {
		return this.ratioSubDao.queryByObject(records, ratioSub);
	}

}