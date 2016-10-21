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
 * Ȩ����Ϣ�ṩ�ࡣ�����ݿ����ѯ���йؽ�ɫ��˵��Ķ�Ӧ��ϵ��Ϣ��<br>
 * 
 * TODO:���ֱ������Dao�࣬���޸Ľ�ɫ���˵���ϵ���޷���������ݣ���Ҫ�޸ġ�
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 ����02:33:11
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

	//����ʱ��Ԥ�ȼ���Ȩ����Ϣ
	public void afterPropertiesSet() throws Exception {
		//ָ��·��Ϊant��ʽ���Ҳ����ִ�Сд
		result = new PathBasedFilterInvocationDefinitionMap();
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
