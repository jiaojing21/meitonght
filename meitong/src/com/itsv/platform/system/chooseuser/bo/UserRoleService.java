package com.itsv.platform.system.chooseuser.bo;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.itsv.platform.system.chooseuser.dao.UserRoleDao;

import com.itsv.platform.system.chooseuser.vo.UserRole;

import com.itsv.gbp.core.admin.bo.*;

/**
 * 角色业务逻辑类。<br>
 * 既处理角色，也处理角色与菜单之间的关联关系。<br>
 * 如果角色－菜单关系发生变化，显式调用权限模块的RightProviderFactory类更新权限数据。
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午10:10:08
 * @version 1.0
 */
public class UserRoleService extends LoggedService implements
		ApplicationContextAware {

	/**
	 * Logger for this class
	 */

	private UserRoleDao roleDao;

	/**
	 * 检索出所有角色。
	 */
	public List<UserRole> queryAll() {
		return this.roleDao.getAll();
	}

	/** get,set */
	public void setRoleDao(UserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
	}
}
