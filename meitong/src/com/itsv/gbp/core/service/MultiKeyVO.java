package com.itsv.gbp.core.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * 多主键情况下的VO基类，子类必须实现getKeyPropNames方法，以便根据多个属性值生成主键对象。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-5 下午03:47:53
 * @version 1.0
 */
public abstract class MultiKeyVO {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseEntity.class);

	// 默认连接/分开多个key字段的字符串
	public static String DEFAULT_SPLIT_WORD = "&@#";

	/**
	 * 子类必须覆盖的抽象方法，返回VO类主键属性名数组
	 * 
	 * @return
	 */
	protected abstract String[] getKeyPropNames();

	/**
	 * 获得key类型的属性值，多个属性值之间用默认的连接字符串进行连接。<br>
	 * 哪些属性是key(主键)属性需要子类进行指定。
	 */
	public String getKey() {
		return getKey(DEFAULT_SPLIT_WORD);
	}

	/**
	 * 获得key类型的属性值。<br>
	 * 哪些属性是key(主键)属性需要子类进行指定。
	 * 
	 * @param join_word
	 *            多个属性值之间的连接字符串
	 * @return
	 */
	public String getKey(String join_word) {

		String[] keys = getKeyPropNames();
		if (keys == null || keys.length == 0) {
			return "";
		}

		StringBuffer result = new StringBuffer();
		for (int n = 0; n < keys.length; n++) {
			if (n > 0) {
				result.append(join_word);
			}
			try {
				Object val = BeanUtils.getSimpleProperty(this, keys[n]);
				result.append(val == null ? "" : val);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
		return result.toString();
	}

	/**
	 * 通过传入的key值，设置相应的属性值
	 * 
	 * @param value
	 */
	public void setKey(String value) {
		setKey(value, DEFAULT_SPLIT_WORD);
	}

	/**
	 * 通过传入的key值，设置相应的属性值
	 * 
	 * @param value
	 */
	public void setKey(String value, String split_word) {

		if (logger.isDebugEnabled()) {
			logger.debug("split_word=" + split_word);
			logger.debug("value=" + value);
		}
		String[] keyValues = value.split(split_word);
		String[] keyNames = getKeyPropNames();

		// 为调试,输出log
		if (keyNames != null && logger.isDebugEnabled()) {
			StringBuffer str = new StringBuffer();
			str.append("key names=[");
			for (int i = 0; i < keyNames.length; i++) {
				str.append(keyNames[i]);
				if (i != keyNames.length - 1) {
					str.append(",");
				}
			}
			str.append("]");
			logger.debug(str.toString());
		}
		// 为调试,输出log
		if (keyValues != null && logger.isDebugEnabled()) {
			StringBuffer str = new StringBuffer();
			str.append("key values=[");
			for (int i = 0; i < keyValues.length; i++) {
				str.append(keyValues[i]);
				if (i != keyValues.length - 1) {
					str.append(",");
				}
			}
			str.append("]");
			logger.debug(str.toString());
		}

		if (keyValues == null || keyNames == null || keyValues.length != keyNames.length) {
			throw new RuntimeException("传来的值为空或个数与要设置的属性个数不匹配");
		}
		for (int i = 0; i < keyNames.length; i++) {
			try {
				BeanUtils.setProperty(this, keyNames[i], keyValues[i]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
