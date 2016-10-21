package com.itsv.gbp.core.group.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.gbp.core.group.dao.Gbp_groupuserDao;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * ˵���������gbp_groupuser��ҵ�����
 *
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupuserService extends BaseService {

  //���ݷ��ʲ����
  private Gbp_groupuserDao gbp_groupuserDao;

	/**
	 * ����gbp_groupuser
	 */
	public void add(Gbp_groupuser gbp_groupuser) {
		this.gbp_groupuserDao.save(gbp_groupuser);
	}

	/**
	 * �޸�gbp_groupuser
	 */
	public void update(Gbp_groupuser gbp_groupuser) {
		this.gbp_groupuserDao.update(gbp_groupuser);
	}

	/**
	 * ɾ��gbp_groupuser
	 */
	public void delete(Serializable id) {
		this.gbp_groupuserDao.removeById(id);
	}

	/**
	 * ����ID��ѯgbp_groupuser����ϸ��Ϣ
	 */
	public Gbp_groupuser queryById(Serializable gbp_groupuserid) {
		return this.gbp_groupuserDao.get(gbp_groupuserid);
	}

	/**
	 * ��ȡ���е�gbp_groupuser����
	 */
	public List<Gbp_groupuser> queryAll() {
		return this.gbp_groupuserDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Gbp_groupuser> queryByVO(Gbp_groupuser gbp_groupuser) {
		return this.gbp_groupuserDao.queryByObject(gbp_groupuser);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Gbp_groupuser gbp_groupuser) {
		return this.gbp_groupuserDao.queryByObject(records, gbp_groupuser);
	}

	public void setGbp_groupuserDao(Gbp_groupuserDao gbp_groupuserDao) {
		this.gbp_groupuserDao = gbp_groupuserDao;
	}

}