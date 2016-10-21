package com.itsv.gbp.core.group.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.gbp.core.group.dao.Gbp_groupuserDao;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对gbp_groupuser的业务操作
 *
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupuserService extends BaseService {

  //数据访问层对象
  private Gbp_groupuserDao gbp_groupuserDao;

	/**
	 * 增加gbp_groupuser
	 */
	public void add(Gbp_groupuser gbp_groupuser) {
		this.gbp_groupuserDao.save(gbp_groupuser);
	}

	/**
	 * 修改gbp_groupuser
	 */
	public void update(Gbp_groupuser gbp_groupuser) {
		this.gbp_groupuserDao.update(gbp_groupuser);
	}

	/**
	 * 删除gbp_groupuser
	 */
	public void delete(Serializable id) {
		this.gbp_groupuserDao.removeById(id);
	}

	/**
	 * 根据ID查询gbp_groupuser的详细信息
	 */
	public Gbp_groupuser queryById(Serializable gbp_groupuserid) {
		return this.gbp_groupuserDao.get(gbp_groupuserid);
	}

	/**
	 * 获取所有的gbp_groupuser对象
	 */
	public List<Gbp_groupuser> queryAll() {
		return this.gbp_groupuserDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Gbp_groupuser> queryByVO(Gbp_groupuser gbp_groupuser) {
		return this.gbp_groupuserDao.queryByObject(gbp_groupuser);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Gbp_groupuser gbp_groupuser) {
		return this.gbp_groupuserDao.queryByObject(records, gbp_groupuser);
	}

	public void setGbp_groupuserDao(Gbp_groupuserDao gbp_groupuserDao) {
		this.gbp_groupuserDao = gbp_groupuserDao;
	}

}