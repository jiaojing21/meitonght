package com.itsv.gbp.core.cache;

import java.util.List;

/**
 * �����Ļ���ӿڡ���Ҫ��������������������<br>
 * getAll()	- �õ����л������
 * getCachedObjectClass()	- �õ����������д洢���������
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����10:38:08
 * @version 1.0
 */
public interface IBaseCache {

	/**
	 * �õ�������ڲ�����
	 * @return
	 */
	public String getName();

	/**
	 * Ϊ�����������ӻ������
	 * 
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value);

	/**
	 * �õ�ָ��key��Ӧ�Ļ������
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key);

	/**
	 * �Ƴ�ָ���Ļ������
	 * 
	 * @param key
	 */
	public void remove(Object key);

	/**
	 * �Ƴ���ǰ������������ж���
	 *
	 */
	public void removeAll();

	/**
	 * �õ���ǰ�������򻺴�����ж���
	 * 
	 * @return
	 */
	public List getAll();

	/**
	 * �õ����������д洢���������
	 * 
	 * @return
	 */
	public Class getCachedObjectClass();

}
