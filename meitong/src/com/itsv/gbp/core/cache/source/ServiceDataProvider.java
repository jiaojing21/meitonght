package com.itsv.gbp.core.cache.source;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.PatternMatchUtils;

import com.itsv.gbp.core.admin.security.RightProviderFactory;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.cache.IMirrorCache;
import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.cache.util.StringTool;

/**
 * ͨ��Service�����û����б����ݡ��� {@see ListDataProvider} ��ͬ������ͬ��<br>
 * ������ͨ������(name)����������(ref)ָ������Դ����Ϊ�˱�������Դ��������ṩ��ĵݹ����á�
 * 
 * @see ListDataProvider
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-11 ����12:27:54
 * @version 1.0
 */
public class ServiceDataProvider extends AbstractListProvider implements ApplicationContextAware,
		AfterReturningAdvice {

	private ApplicationContext context; //��������

	private static final Logger logger = Logger.getLogger(ServiceDataProvider.class);
	/**
	 * �ṩ��������Դ�Ķ���
	 */
	private String sourceName;

	/**
	 * ������������������
	 */
	private Class objectType;

	/**
	 * ��ȡ���ݵķ�������
	 * ע�⣺�÷�������û�в�����������List�����
	 */
	private String methodName;

	/**
	 * ������Դ��Ӧ�Ļ����������������Դ�����䶯ʱ���û���ˢ��
	 */
	private IMirrorCache cache;

	/**
	 * source��������𻺴�ˢ�µķ���
	 */
	private String[] refreshedMethods = new String[] {};

	/**
	 * source������⻺��ˢ�µķ���
	 */
	private String[] ignoredMethods = new String[] {};

	/**
	 * ͨ��������ƣ����ö����ָ����������û���������б�����
	 */
	List getListData() {
		Method method = null;
		List result = null;
		Object source = null;
		try {
			source = context.getBean(sourceName);
			method = source.getClass().getMethod(this.methodName, new Class[] {});
			result = (List) method.invoke(source, new Object[] {});
		} catch (Exception e) {
			throw new MirrorCacheException("�޷���ȡ���ݣ�����ָ������[" + source.getClass().getName() + "]�ķ���["
					+ this.methodName + "]ʱ����", e);
		}

		return result;
	}

	/**
	 * ˢ�»��淽��
	 */
	public void refreshCache() {
		this.logger.debug("==>��ʼˢ�»���<==");
		this.cache.refresh();
		
		if(this.logger.isDebugEnabled()){
			this.logger.debug("=============================");
			List <Role>listr =MirrorCacheTool.getAll("role");
			for(Role ro:listr){
				int i=0;
				this.logger.debug("role:"+i+"is :"+ro.getName());
			}
			this.logger.debug("------------------------------");
			List <User>listu =MirrorCacheTool.getAll("user");
			for(User ro:listu){
				int i=0;
				this.logger.debug("role:"+i+"is :"+ro.getRealName());
			}
			this.logger.debug("=============================");
		}
		
		
		
	}

	/**
	 * AOP���ط���������Դ����source�ķ���ʱ����ø÷���
	 */
	public void afterReturning(Object arg0, Method method, Object[] arg2, Object arg3) throws Throwable {
		//��ȡ�б����ݵķ����϶�Ҫ���Ե�
		if (PatternMatchUtils.simpleMatch(methodName, method.getName())) {
			return;
		}

		//�����ˢ���б���Ҳ��ں����б����ˢ�»���
		if (PatternMatchUtils.simpleMatch(refreshedMethods, method.getName())
				&& !PatternMatchUtils.simpleMatch(ignoredMethods, method.getName())) {
			refreshCache();
		}

	}

	/** ����Ϊget,set ����*/

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setCache(IMirrorCache cache) {
		this.cache = cache;
	}

	public Class getObjectType() {
		return objectType;
	}

	public void setObjectType(Class objectType) {
		this.objectType = objectType;
	}

	public void setIgnoredMethods(String ignoredMethods) {
		this.ignoredMethods = StringTool.split(ignoredMethods);
	}

	public void setRefreshedMethods(String refreshedMethods) {
		this.refreshedMethods = StringTool.split(refreshedMethods);
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}