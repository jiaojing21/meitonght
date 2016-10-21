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
 * 通过Service对象获得缓存列表数据。与 {@see ListDataProvider} 相同功能相同。<br>
 * 不过是通过名称(name)而不是引用(ref)指定数据源对象，为了避免数据源类和数据提供类的递归引用。
 * 
 * @see ListDataProvider
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-11 下午12:27:54
 * @version 1.0
 */
public class ServiceDataProvider extends AbstractListProvider implements ApplicationContextAware,
		AfterReturningAdvice {

	private ApplicationContext context; //环境对象

	private static final Logger logger = Logger.getLogger(ServiceDataProvider.class);
	/**
	 * 提供缓存数据源的对象
	 */
	private String sourceName;

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
		Object source = null;
		try {
			source = context.getBean(sourceName);
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
		this.logger.debug("==>开始刷新缓存<==");
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