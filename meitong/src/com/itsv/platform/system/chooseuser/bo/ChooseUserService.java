package com.itsv.platform.system.chooseuser.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.gbp.core.admin.bo.LoggedService;
import com.itsv.platform.system.chooseuser.dao.ChooseUserDao;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;

/**
 * 处理对用户对象的业务操作。<br>
 * 
 * 该类主要演示用户与角色的级联查询，检索用户时会自动填充其对应的角色信息。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午04:14:24
 * @version 1.0
 */
public class ChooseUserService extends LoggedService {

	private ChooseUserDao userDao;

	/**
	 * 增加用户
	 */
	public void add(ChooseUser user) {

		this.userDao.save(user);

		//log
		writeLog("增加用户", "新增用户[" + user + "]");
	}

	/**
	 * 修改用户
	 */
	public void update(ChooseUser user) {
		this.userDao.update(user);

		//log
		writeLog("修改用户", "修改用户[" + user + "]");
	}

	/**
	 * 删除用户
	 */
	public void delete(Serializable userid) {
		this.userDao.removeById(userid);

		// log
		writeLog("修改用户", "删除用户[id=" + userid + "]");
	}

	/**
	 * 根据用户ID查询用户的详细信息。会级联查询到用户所具有的角色。
	 */
	public ChooseUser queryById(String userid) {
		return this.userDao.get(userid);
	}

	/**
	 * 根据用户名查询用户的详细信息。会级联查询到用户所具有的角色。
	 */
	public ChooseUser queryByName(String userName) {
		return this.userDao.findUniqueBy("userName", userName);
	}

	/**
	 * 根据单位编码，检索出该单位下的所有用户。会级联查询到用户所具有的角色。
	 */
	public List<ChooseUser> queryByUnitId(String unitid) {
		//return this.userDao.findBy("unitId", unitid);
		ChooseUser user = new ChooseUser();
		user.setUnitId(unitid);
		return this.userDao.queryByObject(user);
	}

	/**
	 * 获取所有的用户对象
	 */
	public List<ChooseUser> queryAll() {
		//return this.userDao.getAll();
		return this.userDao.queryByObject(new ChooseUser());
	}


	public void setUserDao(ChooseUserDao userDao) {
		this.userDao = userDao;
	}

}
