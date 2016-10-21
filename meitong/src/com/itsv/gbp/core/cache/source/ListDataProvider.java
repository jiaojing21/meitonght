package com.itsv.gbp.core.cache.source;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.util.PatternMatchUtils;

import com.itsv.gbp.core.cache.IMirrorCache;
import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.util.StringTool;

/**
 * ���ṩList�������ݵĻ�����Դ��������<br>
 * ͨ��ָ����ȡ���ݵ�Դ�����Լ���Ӧ�ķ������������ͨ������ִ����Ӧ�������������ݡ�<br>
 * 
 * ���໹ʵ����spring AOP��AfterReturningAdvice�ӿڣ����Է���ؽ�����AOP���ƣ���Դ���ݸı��ˢ�»��档
 * 
 * refreshedMethods��ignoredMethods�ķ�����ʹ�� PatternMatchUtils ����ƥ�䣬��֧��ǰ��*��ʽ��<br>�磺
 * <pre>
 * refreshedMethods=add*,update*,delete*
 * ignoredMethods=query*,*select
 * </pre>
 * @see PatternMatchUtils
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����03:20:31
 * @version 1.0
 */
public class ListDataProvider extends AbstractListProvider implements AfterReturningAdvice {

	/**
	 * �ṩ��������Դ�Ķ���
	 */
	private Object source;

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
		try {
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
		this.cache.refresh();
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

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
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

}
