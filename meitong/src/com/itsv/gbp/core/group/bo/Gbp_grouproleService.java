package com.itsv.gbp.core.group.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.group.dao.Gbp_grouproleDao;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * ˵���������gbp_grouprole��ҵ�����
 *
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_grouproleService extends BaseService {

	// ���ݷ��ʲ����
	private Gbp_grouproleDao gbp_grouproleDao;

	/**
	 * ����gbp_grouprole
	 */
	public void add(Gbp_grouprole gbp_grouprole) {
		this.gbp_grouproleDao.save(gbp_grouprole);
	}

	/**
	 * �޸�gbp_grouprole
	 */
	public void update(Gbp_grouprole gbp_grouprole) {
		this.gbp_grouproleDao.update(gbp_grouprole);
	}

	/**
	 * ɾ��gbp_grouprole
	 */
	public void delete(Serializable id) {
		this.gbp_grouproleDao.removeById(id);
	}

	/**
	 * ����ID��ѯgbp_grouprole����ϸ��Ϣ
	 */
	public Gbp_grouprole queryById(Serializable gbp_grouproleid) {
		return this.gbp_grouproleDao.get(gbp_grouproleid);
	}

	/**
	 * ��ȡ���е�gbp_grouprole����
	 */
	public List<Gbp_grouprole> queryAll() {
		return this.gbp_grouproleDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Gbp_grouprole> queryByVO(Gbp_grouprole gbp_grouprole) {
		return this.gbp_grouproleDao.queryByObject(gbp_grouprole);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Gbp_grouprole gbp_grouprole) {
		return this.gbp_grouproleDao.queryByObject(records, gbp_grouprole);
	}

	public void setGbp_grouproleDao(Gbp_grouproleDao gbp_grouproleDao) {
		this.gbp_grouproleDao = gbp_grouproleDao;
	}

}