package com.itsv.annotation.kefu.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.kefu.dao.KefuDao;
import com.itsv.annotation.kefu.vo.Kefu;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对kefu的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class KefuService extends BaseService {

  //数据访问层对象
  @Autowired
  private KefuDao kefuDao;

	/**
	 * 增加kefu
	 */
	public void add(Kefu kefu) {
		this.kefuDao.save(kefu);
	}

	/**
	 * 修改kefu
	 */
	public void update(Kefu kefu) {
		this.kefuDao.update(kefu);
	}

	/**
	 * 删除kefu
	 */
	public void delete(Serializable id) {
		this.kefuDao.removeById(id);
	}

	/**
	 * 根据ID查询kefu的详细信息
	 */
	public Kefu queryById(Serializable kefuid) {
		return this.kefuDao.get(kefuid);
	}

	/**
	 * 获取所有的kefu对象
	 */
	public List<Kefu> queryAll() {
		return this.kefuDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Kefu> queryByVO(Kefu kefu) {
		return this.kefuDao.queryByObject(kefu);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Kefu kefu) {
		return this.kefuDao.queryByObject(records, kefu);
	}

}