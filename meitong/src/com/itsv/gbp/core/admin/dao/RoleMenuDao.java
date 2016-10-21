package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;

import com.itsv.gbp.core.admin.vo.RoleMenu;
import com.itsv.gbp.core.orm.hibernate.HibernateBaseDao;

/**
 * �����ɫ��˵���Ӧ��ϵ��Dao�ࡣ<br>
 * 
 * HSqlɾ����Ч�ʸߣ������ƻ�hibernate����������ԡ��ʽ��鲻ʹ�á�
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-7 ����03:27:07
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
