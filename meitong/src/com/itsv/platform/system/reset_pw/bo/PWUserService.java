package com.itsv.platform.system.reset_pw.bo;

import java.util.List;

import com.itsv.platform.system.reset_pw.dao.PWUserDao;
import com.itsv.platform.system.reset_pw.vo.PWUser;
import com.itsv.gbp.core.admin.bo.LoggedService;

;

/**
 * 处理对用户对象的业务操作。<br>
 * 
 * 该类主要演示用户与角色的级联查询，检索用户时会自动填充其对应的角色信息。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午04:14:24
 * @version 1.0
 */
public class PWUserService extends LoggedService {

	private PWUserDao userDao;

	public PWUser getUserInfo() {
		PWUser user = new PWUser();
		user.setId(this.getUserId());
		user.setRealName(this.getCurrentUser().getRealName());
		user.setPassword(this.getCurrentUser().getPassword());
		return user;
	}
	
	/**
	 * 修改用户
	 */
	public void update(PWUser user) {
		this.userDao.update(user);

		// log
		writeLog("修改用户", "修改用户[" + user + "]");
	}

	/**
	 * 根据用户ID查询用户的详细信息。会级联查询到用户所具有的角色。
	 */
	public PWUser queryById(String userid) {
		return this.userDao.get(userid);
	}

	/**
	 * 根据单位编码，检索出该单位下的所有用户。会级联查询到用户所具有的角色。
	 */
	public List<PWUser> queryByUnitId(String unitid) {
		return this.userDao.findBy("unitId", unitid);
	}

	/**
	 * 获取所有的用户对象
	 */
	public List<PWUser> queryAll() {
		return this.userDao.getAll();
	}

	public void setUserDao(PWUserDao userDao) {
		this.userDao = userDao;
	}

}
