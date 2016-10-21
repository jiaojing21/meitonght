package com.itsv.gbp.core.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * ����������µ�VO���࣬�������ʵ��getKeyPropNames�������Ա���ݶ������ֵ������������
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-5 ����03:47:53
 * @version 1.0
 */
public abstract class MultiKeyVO {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseEntity.class);

	// Ĭ������/�ֿ����key�ֶε��ַ���
	public static String DEFAULT_SPLIT_WORD = "&@#";

	/**
	 * ������븲�ǵĳ��󷽷�������VO����������������
	 * 
	 * @return
	 */
	protected abstract String[] getKeyPropNames();

	/**
	 * ���key���͵�����ֵ���������ֵ֮����Ĭ�ϵ������ַ����������ӡ�<br>
	 * ��Щ������key(����)������Ҫ�������ָ����
	 */
	public String getKey() {
		return getKey(DEFAULT_SPLIT_WORD);
	}

	/**
	 * ���key���͵�����ֵ��<br>
	 * ��Щ������key(����)������Ҫ�������ָ����
	 * 
	 * @param join_word
	 *            �������ֵ֮��������ַ���
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
	 * ͨ�������keyֵ��������Ӧ������ֵ
	 * 
	 * @param value
	 */
	public void setKey(String value) {
		setKey(value, DEFAULT_SPLIT_WORD);
	}

	/**
	 * ͨ�������keyֵ��������Ӧ������ֵ
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

		// Ϊ����,���log
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
		// Ϊ����,���log
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
			throw new RuntimeException("������ֵΪ�ջ������Ҫ���õ����Ը�����ƥ��");
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
