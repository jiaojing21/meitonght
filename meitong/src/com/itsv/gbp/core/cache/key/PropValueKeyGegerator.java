package com.itsv.gbp.core.cache.key;

import org.apache.commons.beanutils.BeanUtils;

import com.itsv.gbp.core.cache.MirrorCacheException;
import com.itsv.gbp.core.cache.util.StringTool;

/**
 * ������ָ�����Ե�ֵ����key��<br>
 * ʹ����beanutil��������Ҫ��ʹ�ö����������key����Ὣÿ�����Ե�ֵת��Ϊ�ַ�����Ȼ�������ӷ����ӡ�<br>
 * ���ַ�������ת�����ַ����Ĺ����beanutil��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����12:08:03
 * @version 1.0
 */
public class PropValueKeyGegerator implements IKeyGenerator {

	//ȱʡ�ָ��
	public static String DEFAULT_JOIN_TOKEN = "_";

	//���ָ���˶����������key�����Ӹ�����ֵ֮��ķָ���
	private String joinToken = DEFAULT_JOIN_TOKEN;

	//����keyֵ������������
	private String[] propNames;

	/**
	 * ����ָ����������key
	 */
	public Object generateKey(Object cachingObject) throws MirrorCacheException {
		if (propNames == null) {
			throw new MirrorCacheException("δָ��Ҫ����key������");
		}

		try {
			if (this.propNames.length == 1) {
				//������ֵת��Ϊ�ַ���
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
			throw new MirrorCacheException("����keyʱ����", e);
		}
	}

	/*
	 * ��ǰ����֧���������ɲ���
	 * @see IKeyGenerator#generateKey(java.lang.Object, java.lang.Object)
	 */
	public Object generateKey(Object cachingObject, Object generateConfig) {
		return generateKey(cachingObject);
	}

	/** ����Ϊget,set���� */
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
