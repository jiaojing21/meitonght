package com.itsv.gbp.core.cache.key;

/**
 * ��������key�������ӿڡ�<br> 
 * ����Ҫ��Ž����棬������һ����Ӧ��key��һ�㣬���key��ֵȡ���ڶ����ĳЩ���ԡ�<br>
 * ������ṩ��һ�������key���ɹ��ܣ��û�����key�������Զ��ö������������key��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-9 ����01:04:59
 * @version 1.0
 */
public interface IKeyGenerator {

	/**
	 * ���ݸ�����������ɲ���������keyֵ
	 * 
	 * @param cachingObject
	 * @return
	 */
	public Object generateKey(Object cachingObject);

	/**
	 * ���ݸ�����������ɲ���������keyֵ
	 * 
	 * @param cachingObject ������Ķ���
	 * @param generateConfig ������Ҫ�Ķ������
	 * @return
	 */
	public Object generateKey(Object cachingObject, Object generateConfig);
}
