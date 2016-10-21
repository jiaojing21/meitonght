package com.itsv.gbp.core.group.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.group.dao.Gbp_grouproleDao;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对gbp_grouprole的业务操作
 *
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_grouproleService extends BaseService {

	// 数据访问层对象
	private Gbp_grouproleDao gbp_grouproleDao;

	/**
	 * 增加gbp_grouprole
	 */
	public void add(Gbp_grouprole gbp_grouprole) {
		this.gbp_grouproleDao.save(gbp_grouprole);
	}

	/**
	 * 修改gbp_grouprole
	 */
	public void update(Gbp_grouprole gbp_grouprole) {
		this.gbp_grouproleDao.update(gbp_grouprole);
	}

	/**
	 * 删除gbp_grouprole
	 */
	public void delete(Serializable id) {
		this.gbp_grouproleDao.removeById(id);
	}

	/**
	 * 根据ID查询gbp_grouprole的详细信息
	 */
	public Gbp_grouprole queryById(Serializable gbp_grouproleid) {
		return this.gbp_grouproleDao.get(gbp_grouproleid);
	}

	/**
	 * 获取所有的gbp_grouprole对象
	 */
	public List<Gbp_grouprole> queryAll() {
		return this.gbp_grouproleDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Gbp_grouprole> queryByVO(Gbp_grouprole gbp_grouprole) {
		return this.gbp_grouproleDao.queryByObject(gbp_grouprole);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Gbp_grouprole gbp_grouprole) {
		return this.gbp_grouproleDao.queryByObject(records, gbp_grouprole);
	}

	public void setGbp_grouproleDao(Gbp_grouproleDao gbp_grouproleDao) {
		this.gbp_grouproleDao = gbp_grouproleDao;
	}

}