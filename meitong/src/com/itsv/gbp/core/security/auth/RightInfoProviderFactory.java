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
 * Ȩ����Ϣ�ṩ�ࡣ��ȫ�������������ﶨ��Ĺ���Ȩ���ж��û��Ƿ��ܷ���ָ����Դ��<br>
 * ����spring���������ʽ���ṩ����Ӧ�ù���Ȩ�޵Ķ�����Ϣ��<br>
 * 
 * ע�⣺<b>������Ҫ��������URL��Ӧ�����ý�����Ȩ�ޣ�����ᱻ��Ϊ�ǹ�����Դ�������κ��˷���</b>
 * ��������ʾ�࣬Ȩ����ϢĿǰ��д���ģ�ʵ��Ӧ������Ҫ�����ݿ⶯̬��ȡ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 ����02:33:11
 * @version 1.0
 */
public class RightInfoProviderFactory implements FactoryBean {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RightInfoProviderFactory.class);

	private FilterInvocationDefinitionMap definitions;

	/**
	 * ��ʼ��������
	 * ����Ȩ��ʾ����ʵ������Ҫͨ����ѯ���ݿ�õ������Ϣ��<br>
	 */
	public void init() throws Exception {
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "/security/index.jsp", "ROLE_A" });
		data.add(new String[] { "/security/index.jsp", "ROLE_B" });
		data.add(new String[] { "/services/**", "ROLE_B" });

		convertDefs(data);
	}

	/**
	 * ���ж��嶨��ת����Acegi����ʶ��Ķ���ʽ <br/>
	 * 
	 * �����ڽ��й�����Ȩʱ��һ������û���ɫΪ���ģ���ѡ���ɫ��Ȼ���ٸ����������˵��
	 * ���ǣ�acegi��Ȩ�޿�������pathΪ���ĵģ�ֻƥ���ҵ��ĵ�һ��·����
	 * �������£�<br/>
	 * ���������� ��ɫ���˵� �Ķ�Ӧ��ϵ�� <br/>
	 * ¼�� �� ��ɫ�� <br/>
	 * ��� �� ��ɫA <br/>
	 * ¼�� �� ��ɫB <br/>
	 * ������ɫB�����ʡ�¼�롱����ʱ������ǰ���Ѿ����˶�Ӧ��ϵ����ϵͳֻ��ȡ����һ�����ã����Դ��жϽ�ɫBû��Ȩ�� <br/>
	 * 
	 * ����취������ʱ���ȵ��ø÷�������Ȩ�޵Ķ�Ӧ��ϵ��֯��Acegi��Ӧ�ķ�ʽ��
	 * ����Ҫ�����·��ȫ������ϸ·������Ҫʹ��������ʽ��
	 * 
	 * @param map
	 */
	private void convertDefs(List<String[]> data) {
		//ָ��·��Ϊant��ʽ
		definitions = new PathBasedFilterInvocationDefinitionMap();
		//�����ִ�Сд
		definitions.setConvertUrlToLowercaseBeforeComparison(true);

		//ת��Ȩ�����ã���ͬһ·����Ӧ�Ľ�ɫ�ϲ�����
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
				logger.debug("[" + element[0] + "]��Ӧ��ɫ[" + configs + "]");
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
