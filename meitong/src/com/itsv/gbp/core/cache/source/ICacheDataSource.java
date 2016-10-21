package com.itsv.gbp.core.cache.source;

import java.util.Map;

import com.itsv.gbp.core.cache.IMirrorCache;

/**
 * ��������Դ��Ϊ�˸������ṩ����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����12:00:13
 * @version 1.0
 */
public interface ICacheDataSource {

	/**
	 * �õ����������ݵ�key-value�б�
	 * @return
	 */
	public Map getKeyValues();

	/**
	 * �õ���������������
	 * @return
	 */
	public Class getObjectType();

	/**
	 * �����潫����ת�ṩ����������Դ
	 * 
	 * @param cache
	 */
	public void setCache(IMirrorCache cache);

}
