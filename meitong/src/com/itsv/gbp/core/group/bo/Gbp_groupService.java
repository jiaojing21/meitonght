package com.itsv.gbp.core.group.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.group.dao.Gbp_groupDao;
import com.itsv.gbp.core.group.dao.Gbp_grouproleDao;
import com.itsv.gbp.core.group.dao.Gbp_groupuserDao;
import com.itsv.gbp.core.group.vo.Gbp_group;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;
import com.itsv.gbp.core.orm.paged.IPagedList;
import com.itsv.platform.common.dictionary.dao.Itsv_dictionaryDao;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
import com.itsv.platform.system.chooseunit.dao.ChooseUnitDao;
import com.itsv.platform.system.chooseuser.dao.ChooseUserDao;
import com.itsv.platform.system.chooseuser.dao.UserRoleDao;

/*
 * 说明：处理对组的业务操作
 *
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupService extends BaseService {

	// 数据访问层对象
	private Gbp_groupDao gbp_groupDao;

	private Itsv_dictionaryDao itsv_dictionaryDao;

	private ChooseUserDao userDao;; // 

	private UserRoleDao roleDao;

	private ChooseUnitDao unitDao;

	private Gbp_groupuserDao gbp_groupuserDao;
	private Gbp_grouproleDao gbp_grouproleDao;

	public void setItsv_dictionaryDao(Itsv_dictionaryDao itsv_dictionaryDao) {
		this.itsv_dictionaryDao = itsv_dictionaryDao;
	}

	public void setRoleDao(UserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUnitDao(ChooseUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public void setUserDao(ChooseUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 增加组
	 */
	public void add(Gbp_group gbp_group) {
		this.gbp_groupDao.save(gbp_group);
	}

	/**
	 * 修改组
	 */
	public void update(Gbp_group gbp_group) {
		this.gbp_groupDao.update(gbp_group);
	}

	/**
	 * 删除组
	 */
	public void delete(Serializable id) {
		this.gbp_groupDao.removeById(id);
	}

	/**
	 * 根据ID查询组的详细信息
	 */
	public Gbp_group queryById(Serializable gbp_groupid) {
		return this.gbp_groupDao.get(gbp_groupid);
	}

	/**
	 * 获取所有的组对象
	 */
	public List<Gbp_group> queryAll() {
		return this.gbp_groupDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Gbp_group> queryByVO(Gbp_group gbp_group) {
		return this.gbp_groupDao.queryByObject(gbp_group);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Gbp_group gbp_group) {
		return this.gbp_groupDao.queryByObject(records, gbp_group);
	}

	public void setGbp_groupDao(Gbp_groupDao gbp_groupDao) {
		this.gbp_groupDao = gbp_groupDao;
	}

	public List queryAllUnit() {
		return this.unitDao.getAll();
	}

	public List queryAllUser() {
		return this.userDao.getAll();
	}

	public List queryAllRole() {
		return this.roleDao.getAll();
	}

	public List<Itsv_dictionary> queryDictByVO(Itsv_dictionary itsv_dictionary) {
		return this.itsv_dictionaryDao.queryByObject(itsv_dictionary);
	}

	/**
	 * 取得当前用户组下，所有用户的id
	 * @param groupId
	 * @return
	 */
	public List queryUserIdByGroupId(String groupId) {
		//
		Gbp_groupuser gbp_groupuser = new Gbp_groupuser();
		gbp_groupuser.setGroupid(groupId);
		List<Gbp_groupuser> tmp = this.gbp_groupuserDao.queryByObject(gbp_groupuser);
		List<String>ret = new ArrayList<String>();
		if(tmp!=null){
			for (Gbp_groupuser gu : tmp) {
				ret.add(gu.getUserid());
			}
		}
		return ret;
	}

	public void setGbp_groupuserDao(Gbp_groupuserDao gbp_groupuserDao) {
		this.gbp_groupuserDao = gbp_groupuserDao;
	}

	public void deleteAllGroupUserByGroupId(String groupId) {
		this.gbp_groupuserDao.removeByGroupId(groupId);
		
	}

	public void addGroupUser(Gbp_groupuser o) {
		this.gbp_groupuserDao.save(o);
		
	}

	
	/**
	 * 由组id删除本组关联所有的权限id
	 * @param groupId
	 * @return
	 */
	public List queryRoleIdsByGroupId(String groupId) {
		Gbp_grouprole gbp_grouprole = new Gbp_grouprole();
		gbp_grouprole.setGroupid(groupId);
		List<Gbp_grouprole> tmp = this.gbp_grouproleDao.queryByObject(gbp_grouprole);
		List<String>ret = new ArrayList<String>();
		if(tmp!=null){
			for (Gbp_grouprole gr : tmp) {
				ret.add(gr.getRoleid());
			}
		}
		return ret;
	}

	public Gbp_grouproleDao getGbp_grouproleDao() {
		return gbp_grouproleDao;
	}

	public void setGbp_grouproleDao(Gbp_grouproleDao gbp_grouproleDao) {
		this.gbp_grouproleDao = gbp_grouproleDao;
	}

	public void deleteAllGroupRoleByGroupId(String groupId) {
		this.gbp_grouproleDao.removeAllByGroupId(groupId);
		
	}

	public void addGroupRole(Gbp_grouprole o) {
		this.gbp_grouproleDao.save(o);
	}

}
