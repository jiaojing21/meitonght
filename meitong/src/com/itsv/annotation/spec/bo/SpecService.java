package com.itsv.annotation.spec.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.spec.dao.SpecDao;
import com.itsv.annotation.spec.vo.Spec;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对spec的业务操作
 *
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
 @Service @Transactional 
public class SpecService extends BaseService {

  //数据访问层对象
  @Autowired
  private SpecDao specDao;

	/**
	 * 增加spec
	 */
	public void add(Spec spec) {
		this.specDao.save(spec);
	}

	/**
	 * 修改spec
	 */
	public void update(Spec spec) {
		this.specDao.update(spec);
	}

	/**
	 * 删除spec
	 */
	public void delete(Serializable id) {
		this.specDao.removeById(id);
	}

	/**
	 * 根据ID查询spec的详细信息
	 */
	public Spec queryById(Serializable specid) {
		return this.specDao.get(specid);
	}

	/**
	 * 获取所有的spec对象
	 */
	public List<Spec> queryAll() {
		return this.specDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Spec> queryByVO(Spec spec) {
		return this.specDao.queryByObject(spec);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Spec spec) {
		return this.specDao.queryByObject(records, spec);
	}
	
}