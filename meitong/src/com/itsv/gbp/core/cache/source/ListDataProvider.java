package com.itsv.gbp.core.cache.source;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.util.PatternMatchUtils;

import com.itsv.gbp.core.cache.IMirrorCache;
import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.util.StringTool;

/**
 * 可提供List类型数据的缓存来源适配器。<br>
 * 通过指定获取数据的源对象，以及相应的方法名，该类会通过反射执行相应方法，返回数据。<br>
 * 
 * 该类还实现了spring AOP的AfterReturningAdvice接口，可以方便地将利用AOP机制，在源数据改变后，刷新缓存。
 * 
 * refreshedMethods和ignoredMethods的方法名使用 PatternMatchUtils 进行匹配，仅支持前后*形式。<br>如：
 * <pre>
 * refreshedMethods=add*,update*,delete*
 * ignoredMethods=query*,*select
 * </pre>
 * @see PatternMatchUtils
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 下午03:20:31
 * @version 1.0
 */
public class ListDataProvider extends AbstractListProvider implements AfterReturningAdvice {

	/**
	 * 提供缓存数据源的对象
	 */
	private Object source;

	/**
	 * 待缓存对象的数据类型
	 */
	private Class objectType;

	/**
	 * 获取数据的方法名。
	 * 注意：该方法必须没有参数，并返回List结果。
	 */
	private String methodName;

	/**
	 * 该数据源对应的缓存对象。用来在数据源发生变动时调用缓存刷新
	 */
	private IMirrorCache cache;

	/**
	 * source对象会引起缓存刷新的方法
	 */
	private String[] refreshedMethods = new String[] {};

	/**
	 * source对象避免缓存刷新的方法
	 */
	private String[] ignoredMethods = new String[] {};

	/**
	 * 通过反射机制，调用对象的指定方法，获得缓存所需的列表数据
	 */
	List getListData() {
		Method method = null;
		List result = null;
		try {
			method = source.getClass().getMethod(this.methodName, new Class[] {});
			result = (List) method.invoke(source, new Object[] {});
		} catch (Exception e) {
			throw new MirrorCacheException("无法获取数据：调用指定对象[" + source.getClass().getName() + "]的方法["
					+ this.methodName + "]时出错", e);
		}

		return result;
	}

	/**
	 * 刷新缓存方法
	 */
	public void refreshCache() {
		this.cache.refresh();
	}

	/**
	 * AOP拦截方法，调用源对象source的方法时会调用该方法
	 */
	public void afterReturning(Object arg0, Method method, Object[] arg2, Object arg3) throws Throwable {
		//获取列表数据的方法肯定要忽略掉
		if (PatternMatchUtils.simpleMatch(methodName, method.getName())) {
			return;
		}

		//如果在刷新列表里，且不在忽略列表里，则刷新缓存
		if (PatternMatchUtils.simpleMatch(refreshedMethods, method.getName())
				&& !PatternMatchUtils.simpleMatch(ignoredMethods, method.getName())) {
			refreshCache();
		}

	}

	/** 以下为get,set 方法*/

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
