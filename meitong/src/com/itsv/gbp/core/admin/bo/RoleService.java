package com.itsv.gbp.core.admin.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionMap;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;
import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.dao.MenuDao;
import com.itsv.gbp.core.admin.dao.RoleDao;
import com.itsv.gbp.core.admin.dao.RoleMenuDao;
import com.itsv.gbp.core.admin.security.RightProviderFactory;
import com.itsv.gbp.core.admin.vo.Menu;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.RoleMenu;

/**
 * ��ɫҵ���߼��ࡣ<br>
 * 
 * �ȴ����ɫ��Ҳ�����ɫ��˵�֮��Ĺ�����ϵ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����10:10:08
 * @version 1.0
 */
public class RoleService extends LoggedService {
	private static final Logger logger = Logger.getLogger(RoleService.class);
	private RoleDao roleDao;
	private MenuDao menuDao;
	private RoleMenuDao roleMenuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/**
	 * ���ӽ�ɫ
	 */
	public void add(Role role, String[] menuIds) {
		this.roleDao.save(role);
		this.roleMenuDao.addMenu2Role(role.getId(), menuIds);

		// log
		writeLog("���ӽ�ɫ", "������ɫ[" + role + "]");
	}

	/**
	 * �޸Ľ�ɫ <br>
	 */
	public void update(Role role, String[] menuIds) {
		this.roleDao.update(role);
		this.roleMenuDao.removeByRoleId(role.getId());
		this.roleMenuDao.addMenu2Role(role.getId(), menuIds);

		// log
		writeLog("�޸Ľ�ɫ", "�޸Ľ�ɫ[" + role + "]");
	}

	/**
	 * ɾ����ɫ
	 */
	public void delete(String roleid) {
		this.roleDao.removeById(roleid);
		this.roleMenuDao.removeByRoleId(roleid);

		// log
		writeLog("ɾ����ɫ", "ɾ����ɫ[id=" + roleid + "]");
	}

	/**
	 * ���ݽ�ɫID��ѯ��ɫ����ϸ��Ϣ
	 */
	public Role queryById(String roleid) {
		return this.roleDao.get(roleid);
	}

	/**
	 * ���ݽ�ɫ����ѯ��ɫ����ϸ��Ϣ
	 */
	public Role queryByName(String name) {
		return this.roleDao.findUniqueBy("name", name);
	}

	/**
	 * ���ݽ�ɫID��ȡ��Ӧ�Ĳ˵�ID�б�
	 */
	public List<String> queryMenuIds(String roleid) {
		List<RoleMenu> result = this.roleMenuDao.findBy("roleId", roleid);
		List<String> ids = new ArrayList<String>();
		for (RoleMenu rm : result) {
			ids.add(rm.getMenuId());
		}
		return ids;
	}

	/**
	 * ���������н�ɫ��
	 */
	public List<Role> queryAll() {
		return this.roleDao.getAll();
	}

	/**get,set*/
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setRoleMenuDao(RoleMenuDao roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
	}

	public AbstractFilterInvocationDefinitionSource   getUpdatedSecurityPropertiesSet() throws Exception {
		//ָ��·��Ϊant��ʽ���Ҳ����ִ�Сд
		PathBasedFilterInvocationDefinitionMap result = new PathBasedFilterInvocationDefinitionMap();
		result.setConvertUrlToLowercaseBeforeComparison(true);

		//ת��Ȩ�����ã���ͬһ·����Ӧ�Ľ�ɫ�ϲ�����
		Map<String, ConfigAttributeDefinition> grants = new HashMap<String, ConfigAttributeDefinition>();
		for (RoleMenu roleMenu : roleMenuDao.getAll()) {
			Menu menu = this.menuDao.get(roleMenu.getMenuId());
			String url = menu.getAction();
			if (url == null || url.length() == 0) {
				continue;
			}
			//�Զ���urlǰ��/����Ϊacegi�ȶԵ�url������/��ͷ��
			if (!url.startsWith("/")) {
				url = "/" + url;
			}
			//�Զ���ÿ��url�󶼼���*����ֹ�û������ں���Ӳ����ƹ�Ȩ�޿���
			if (!url.endsWith("*")) {
				url += "*";
			}

			if (!grants.containsKey(url)) {
				grants.put(url, new ConfigAttributeDefinition());
			}
			ConfigAttributeDefinition configs = (ConfigAttributeDefinition) grants.get(url);
			configs.addConfigAttribute(new SecurityConfig(roleMenu.getRoleId().toString()));

			if (logger.isDebugEnabled()) {
				logger.debug("[" + menu.getAction() + "]��Ӧ��ɫ[" + configs + "]");
			}
		}

		for (String action : grants.keySet()) {
			result.addSecureUrl(action, grants.get(action));
		}
		return result;
	}
	
	
}
