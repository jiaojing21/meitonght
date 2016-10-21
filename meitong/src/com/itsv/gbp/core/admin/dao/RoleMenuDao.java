package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;

import com.itsv.gbp.core.admin.vo.RoleMenu;
import com.itsv.gbp.core.orm.hibernate.HibernateBaseDao;

/**
 * 处理角色与菜单对应关系的Dao类。<br>
 * 
 * HSql删除的效率高，但会破坏hibernate缓存的完整性。故建议不使用。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-7 下午03:27:07
 * @version 1.0
 */
public class RoleMenuDao extends HibernateBaseDao<RoleMenu> {

	public void removeByRoleId(String roleId) {
		for (RoleMenu each : findBy("roleId", roleId)) {
			remove(each);
		}
	}

	public void removeByMenuId(Serializable menuId) {
		for (RoleMenu each : findBy("menuId", menuId)) {
			remove(each);
		}
	}

	public void addMenu2Role(String roleId, String[] menuIds) {
		for (String menuId : menuIds) {
			save(new RoleMenu(roleId, menuId));
		}
	}
}
