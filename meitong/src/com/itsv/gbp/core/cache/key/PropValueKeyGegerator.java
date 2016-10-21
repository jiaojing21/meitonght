package com.itsv.gbp.core.cache.key;

import org.apache.commons.beanutils.BeanUtils;

import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.util.StringTool;

/**
 * 将对象按指定属性的值生成key。<br>
 * 使用了beanutil组件，如果要求使用多个属性生成key，则会将每个属性的值转换为字符串，然后用连接符连接。<br>
 * 非字符串对象转换成字符串的规则见beanutil。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 下午12:08:03
 * @version 1.0
 */
public class PropValueKeyGegerator implements IKeyGenerator {

	//缺省分割符
	public static String DEFAULT_JOIN_TOKEN = "_";

	//如果指定了多个属性生成key，连接各属性值之间的分隔符
	private String joinToken = DEFAULT_JOIN_TOKEN;

	//生成key值的属性名数组
	private String[] propNames;

	/**
	 * 根据指定属性生成key
	 */
	public Object generateKey(Object cachingObject) throws MirrorCacheException {
		if (propNames == null) {
			throw new MirrorCacheException("未指定要生成key的属性");
		}

		try {
			if (this.propNames.length == 1) {
				//将属性值转换为字符串
				return BeanUtils.getProperty(cachingObject, propNames[0]);
			}

			StringBuffer key = new StringBuffer();
			for (int i = 0; i < this.propNames.length; i++) {
				String value = BeanUtils.getProperty(cachingObject, propNames[i]);
				if (i > 0) {
					key.append(getJoinToken());
				}
				if (value != null) {
					key.append(value);
				}
			}
			return key.toString();

		} catch (Exception e) {
			throw new MirrorCacheException("生成key时出错", e);
		}
	}

	/*
	 * 当前还不支持其他生成参数
	 * @see IKeyGenerator#generateKey(java.lang.Object, java.lang.Object)
	 */
	public Object generateKey(Object cachingObject, Object generateConfig) {
		return generateKey(cachingObject);
	}

	/** 以下为get,set方法 */
	public String[] getPropNames() {
		return propNames;
	}

	public void setPropNames(String propNames) {
		this.propNames = StringTool.split(propNames);
	}

	public String getJoinToken() {
		return joinToken;
	}

	public void setJoinToken(String joinToken) {
		this.joinToken = joinToken;
	}

}
