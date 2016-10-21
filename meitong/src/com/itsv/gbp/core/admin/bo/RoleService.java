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
 * 角色业务逻辑类。<br>
 * 
 * 既处理角色，也处理角色与菜单之间的关联关系。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午10:10:08
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
	 * 增加角色
	 */
	public void add(Role role, String[] menuIds) {
		this.roleDao.save(role);
		this.roleMenuDao.addMenu2Role(role.getId(), menuIds);

		// log
		writeLog("增加角色", "新增角色[" + role + "]");
	}

	/**
	 * 修改角色 <br>
	 */
	public void update(Role role, String[] menuIds) {
		this.roleDao.update(role);
		this.roleMenuDao.removeByRoleId(role.getId());
		this.roleMenuDao.addMenu2Role(role.getId(), menuIds);

		// log
		writeLog("修改角色", "修改角色[" + role + "]");
	}

	/**
	 * 删除角色
	 */
	public void delete(String roleid) {
		this.roleDao.removeById(roleid);
		this.roleMenuDao.removeByRoleId(roleid);

		// log
		writeLog("删除角色", "删除角色[id=" + roleid + "]");
	}

	/**
	 * 根据角色ID查询角色的详细信息
	 */
	public Role queryById(String roleid) {
		return this.roleDao.get(roleid);
	}

	/**
	 * 根据角色名查询角色的详细信息
	 */
	public Role queryByName(String name) {
		return this.roleDao.findUniqueBy("name", name);
	}

	/**
	 * 根据角色ID获取对应的菜单ID列表
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
	 * 检索出所有角色。
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
		//指定路径为ant格式，且不区分大小写
		PathBasedFilterInvocationDefinitionMap result = new PathBasedFilterInvocationDefinitionMap();
		result.setConvertUrlToLowercaseBeforeComparison(true);

		//转换权限设置，将同一路径对应的角色合并起来
		Map<String, ConfigAttributeDefinition> grants = new HashMap<String, ConfigAttributeDefinition>();
		for (RoleMenu roleMenu : roleMenuDao.getAll()) {
			Menu menu = this.menuDao.get(roleMenu.getMenuId());
			String url = menu.getAction();
			if (url == null || url.length() == 0) {
				continue;
			}
			//自动给url前加/，因为acegi比对的url都是以/开头的
			if (!url.startsWith("/")) {
				url = "/" + url;
			}
			//自动给每个url后都加上*，防止用户随意在后面加参数绕过权限控制
			if (!url.endsWith("*")) {
				url += "*";
			}

			if (!grants.containsKey(url)) {
				grants.put(url, new ConfigAttributeDefinition());
			}
			ConfigAttributeDefinition configs = (ConfigAttributeDefinition) grants.get(url);
			configs.addConfigAttribute(new SecurityConfig(roleMenu.getRoleId().toString()));

			if (logger.isDebugEnabled()) {
				logger.debug("[" + menu.getAction() + "]对应角色[" + configs + "]");
			}
		}

		for (String action : grants.keySet()) {
			result.addSecureUrl(action, grants.get(action));
		}
		return result;
	}
	
	
}
