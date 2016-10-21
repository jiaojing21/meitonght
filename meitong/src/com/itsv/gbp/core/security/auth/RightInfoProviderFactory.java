package com.itsv.gbp.core.security.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionMap;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionSource;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * 权限信息提供类。安全管理器根据这里定义的功能权限判断用户是否能访问指定资源。<br>
 * 借助spring工厂类的形式，提供关于应用功能权限的定义信息。<br>
 * 
 * 注意：<b>所有需要被保护的URL都应当设置进功能权限，否则会被认为是公共资源，允许任何人访问</b>
 * 该类是演示类，权限信息目前是写死的，实际应用中需要从数据库动态获取。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 下午02:33:11
 * @version 1.0
 */
public class RightInfoProviderFactory implements FactoryBean {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RightInfoProviderFactory.class);

	private FilterInvocationDefinitionMap definitions;

	/**
	 * 初始化方法。
	 * 功能权限示例。实际中需要通过查询数据库得到相关信息。<br>
	 */
	public void init() throws Exception {
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "/security/index.jsp", "ROLE_A" });
		data.add(new String[] { "/security/index.jsp", "ROLE_B" });
		data.add(new String[] { "/services/**", "ROLE_B" });

		convertDefs(data);
	}

	/**
	 * 进行定义定义转换成Acegi可以识别的对象方式 <br/>
	 * 
	 * 我们在进行功能授权时，一般会以用户角色为中心，先选择角色，然后再给它赋予多个菜单项。
	 * 但是，acegi的权限控制是以path为核心的，只匹配找到的第一个路径。
	 * 举例如下：<br/>
	 * 我们有如下 角色－菜单 的对应关系： <br/>
	 * 录入 － 角色Ａ <br/>
	 * 审核 － 角色A <br/>
	 * 录入 － 角色B <br/>
	 * 当“角色B”访问“录入”功能时，由于前面已经有了对应关系，故系统只会取出第一条设置，并以此判断角色B没有权限 <br/>
	 * 
	 * 解决办法：启动时首先调用该方法，将权限的对应关系组织成Acegi适应的方式。
	 * 但这要求最好路径全部是详细路径，不要使用正则表达式。
	 * 
	 * @param map
	 */
	private void convertDefs(List<String[]> data) {
		//指定路径为ant格式
		definitions = new PathBasedFilterInvocationDefinitionMap();
		//不区分大小写
		definitions.setConvertUrlToLowercaseBeforeComparison(true);

		//转换权限设置，将同一路径对应的角色合并起来
		Map<String, ConfigAttributeDefinition> grants = new HashMap<String, ConfigAttributeDefinition>();
		for (Iterator<String[]> iter = data.iterator(); iter.hasNext();) {
			String[] element = iter.next();
			element[0] = element[0].trim();
			element[1] = element[1].trim();

			if (!grants.containsKey(element[0])) {
				grants.put(element[0], new ConfigAttributeDefinition());
			}
			ConfigAttributeDefinition configs = (ConfigAttributeDefinition) grants.get(element[0]);
			configs.addConfigAttribute(new SecurityConfig(element[1]));

			if (logger.isDebugEnabled()) {
				logger.debug("[" + element[0] + "]对应角色[" + configs + "]");
			}

		}

		for (Iterator<String> iter = grants.keySet().iterator(); iter.hasNext();) {
			String url = iter.next();

			definitions.addSecureUrl(url, grants.get(url));
		}
	}

	public Object getObject() throws Exception {
		return definitions;
	}

	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}

	public boolean isSingleton() {
		return false;
	}

}
