package com.itsv.annotation.pictext.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.pictext.dao.PictextDao;
import com.itsv.annotation.pictext.vo.Pictext;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对pictext的业务操作
 *
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */
 @Service @Transactional 
public class PictextService extends BaseService {

  //数据访问层对象
  @Autowired
  private PictextDao pictextDao;

	/**
	 * 增加pictext
	 */
	public void add(Pictext pictext) {
		this.pictextDao.save(pictext);
	}

	/**
	 * 修改pictext
	 */
	public void update(Pictext pictext) {
		this.pictextDao.update(pictext);
	}

	/**
	 * 删除pictext
	 */
	public void delete(Serializable id) {
		this.pictextDao.removeById(id);
	}

	/**
	 * 根据ID查询pictext的详细信息
	 */
	public Pictext queryById(Serializable pictextid) {
		return this.pictextDao.get(pictextid);
	}

	/**
	 * 获取所有的pictext对象
	 */
	public List<Pictext> queryAll() {
		return this.pictextDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Pictext> queryByVO(Pictext pictext) {
		return this.pictextDao.queryByObject(pictext);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Pictext pictext) {
		return this.pictextDao.queryByObject(records, pictext);
	}

}