package com.itsv.gbp.core.security.channel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.ConfigAttributeEditor;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionMap;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionSource;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;
import org.springframework.beans.factory.FactoryBean;

/**
 * 需要SSL安全通道保障的信息提供类。<br>
 * 利用spring的factory类机制，返回哪些通道需要安全传输，哪些不需要。<br>
 * 
 * acegi的通道设置和权限都存在同样的问题：<br>
 * 只能按定义的URL顺序匹配，没有一个匹配度的做法，例如，对于/a/b.do, /a/b.*应该比/a/**更优先匹配。<br>
 * 
 * 关于安全通道的设置，需要注意下面几个问题：
 * <ol>
 * 	<li>url可以使用通配符，而且查找时按照其顺序进行，一旦找到匹配就不再向下查找。在系统设置时应该注意</li>
 *  <li>如果需要保障用户登录过程加密，那么，不仅登录提交URL要设置为需要安全传输，而且用户登录的显示页面也需要设置为安全。</li>
 *  <li>最后一个应该设置/**路径为不需要安全传输，以便在https后，访问其他页面时，又可以自动转为http正常访问</li>
 *  <li>系统默认控制了每个用户名只能同时登录一次，若用户名和密码都正确，但还是无法登录，就可能是这个原因。（也可放开该限制）</li>
 * <ol>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-17 下午05:30:31
 * @version 1.0
 */
public class ChannelInfoProviderFactory implements FactoryBean {

	//判断安全通道和非安全通道的关键字，不要修改
	private static String SECURE_KEYWORD = "REQUIRES_SECURE_CHANNEL";

	private static String INSECURE_KEYWORD = "REQUIRES_INSECURE_CHANNEL";

	private FilterInvocationDefinitionMap definitions;

	/**
	 * 这儿示例模拟数据，实际需要从数据库里取。只需配置需要安全传输的路径
	 */
	public void init() {
		List<String> urls = new ArrayList<String>();
		urls.add("/security/**");
		urls.add("/j_acegi_security_check");

		convertDefs(urls);
	}

	/**
	 * 将Map类型的定义转换成Acegi可以识别的对象方式
	 * 
	 * @param map
	 */
	private void convertDefs(List<String> list) {
		//指定路径为ant格式
		definitions = new PathBasedFilterInvocationDefinitionMap();
		//不区分大小写
		definitions.setConvertUrlToLowercaseBeforeComparison(true);

		ConfigAttributeEditor configAttribEd = new ConfigAttributeEditor();
		configAttribEd.setAsText(SECURE_KEYWORD);
		ConfigAttributeDefinition secureAttr = (ConfigAttributeDefinition) configAttribEd.getValue();

		for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
			definitions.addSecureUrl(iter.next(), secureAttr);
		}

		//最后，默认其他路径为不需要安全处理
		configAttribEd.setAsText(INSECURE_KEYWORD);
		ConfigAttributeDefinition insecureAttr = (ConfigAttributeDefinition) configAttribEd.getValue();
		definitions.addSecureUrl("/**", insecureAttr);
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
