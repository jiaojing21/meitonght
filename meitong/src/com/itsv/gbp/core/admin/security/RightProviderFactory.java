package com.itsv.gbp.core.admin.security;

import java.util.HashMap;
import java.util.Map;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionMap;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionSource;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.itsv.gbp.core.admin.dao.MenuDao;
import com.itsv.gbp.core.admin.dao.RoleMenuDao;
import com.itsv.gbp.core.admin.vo.Menu;
import com.itsv.gbp.core.admin.vo.RoleMenu;

/**
 * 权限信息提供类。从数据库里查询出有关角色与菜单的对应关系信息。<br>
 * 
 * TODO:这儿直接用了Dao类，且修改角色－菜单关系后无法获得新数据，需要修改。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 下午02:33:11
 * @version 1.0
 */
public class RightProviderFactory implements FactoryBean, InitializingBean {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RightProviderFactory.class);

	private RoleMenuDao roleMenuDao;

	private MenuDao menuDao;

	FilterInvocationDefinitionMap result;

	public Object getObject() throws Exception {
		return this.result;
	}

	//启动时就预先加载权限信息
	public void afterPropertiesSet() throws Exception {
		//指定路径为ant格式，且不区分大小写
		result = new PathBasedFilterInvocationDefinitionMap();
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
	}

	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}

	public boolean isSingleton() {
		return false;
	}

	/**get,set*/
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void setRoleMenuDao(RoleMenuDao roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
	}

}
