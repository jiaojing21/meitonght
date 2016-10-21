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
 * ��ҪSSL��ȫͨ�����ϵ���Ϣ�ṩ�ࡣ<br>
 * ����spring��factory����ƣ�������Щͨ����Ҫ��ȫ���䣬��Щ����Ҫ��<br>
 * 
 * acegi��ͨ�����ú�Ȩ�޶�����ͬ�������⣺<br>
 * ֻ�ܰ������URL˳��ƥ�䣬û��һ��ƥ��ȵ����������磬����/a/b.do, /a/b.*Ӧ�ñ�/a/**������ƥ�䡣<br>
 * 
 * ���ڰ�ȫͨ�������ã���Ҫע�����漸�����⣺
 * <ol>
 * 	<li>url����ʹ��ͨ��������Ҳ���ʱ������˳����У�һ���ҵ�ƥ��Ͳ������²��ҡ���ϵͳ����ʱӦ��ע��</li>
 *  <li>�����Ҫ�����û���¼���̼��ܣ���ô��������¼�ύURLҪ����Ϊ��Ҫ��ȫ���䣬�����û���¼����ʾҳ��Ҳ��Ҫ����Ϊ��ȫ��</li>
 *  <li>���һ��Ӧ������/**·��Ϊ����Ҫ��ȫ���䣬�Ա���https�󣬷�������ҳ��ʱ���ֿ����Զ�תΪhttp��������</li>
 *  <li>ϵͳĬ�Ͽ�����ÿ���û���ֻ��ͬʱ��¼һ�Σ����û��������붼��ȷ���������޷���¼���Ϳ��������ԭ�򡣣�Ҳ�ɷſ������ƣ�</li>
 * <ol>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-17 ����05:30:31
 * @version 1.0
 */
public class ChannelInfoProviderFactory implements FactoryBean {

	//�жϰ�ȫͨ���ͷǰ�ȫͨ���Ĺؼ��֣���Ҫ�޸�
	private static String SECURE_KEYWORD = "REQUIRES_SECURE_CHANNEL";

	private static String INSECURE_KEYWORD = "REQUIRES_INSECURE_CHANNEL";

	private FilterInvocationDefinitionMap definitions;

	/**
	 * ���ʾ��ģ�����ݣ�ʵ����Ҫ�����ݿ���ȡ��ֻ��������Ҫ��ȫ�����·��
	 */
	public void init() {
		List<String> urls = new ArrayList<String>();
		urls.add("/security/**");
		urls.add("/j_acegi_security_check");

		convertDefs(urls);
	}

	/**
	 * ��Map���͵Ķ���ת����Acegi����ʶ��Ķ���ʽ
	 * 
	 * @param map
	 */
	private void convertDefs(List<String> list) {
		//ָ��·��Ϊant��ʽ
		definitions = new PathBasedFilterInvocationDefinitionMap();
		//�����ִ�Сд
		definitions.setConvertUrlToLowercaseBeforeComparison(true);

		ConfigAttributeEditor configAttribEd = new ConfigAttributeEditor();
		configAttribEd.setAsText(SECURE_KEYWORD);
		ConfigAttributeDefinition secureAttr = (ConfigAttributeDefinition) configAttribEd.getValue();

		for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
			definitions.addSecureUrl(iter.next(), secureAttr);
		}

		//���Ĭ������·��Ϊ����Ҫ��ȫ����
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
