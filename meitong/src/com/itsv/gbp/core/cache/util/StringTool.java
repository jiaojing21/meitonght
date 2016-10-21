package com.itsv.gbp.core.cache.util;

import java.util.StringTokenizer;

/**
 * �ַ���������
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����11:20:41
 * @version 1.0
 */
public class StringTool {

	public static String COMMON_SEPERATORS = " ,;:";

	private StringTool() {
	}

	/**
	 * ���ݸ���ǰ׺�������Ƽ����޶�ǰ׺
	 * 
	 * @param prefix
	 * @param name
	 * @return
	 */
	public static String qualify(String prefix, String name) {
		if (name == null || prefix == null) {
			throw new NullPointerException();
		}
		return new StringBuffer(prefix.length() + name.length() + 1).append(prefix).append('.').append(name)
				.toString();
	}

	/**
	 * ����ָ���ķָ���ָ��ַ�����
	 * ����ʹ����StringTokenizer���ָ�������ǵ����ַ���������ָ�������
	 * <pre>
	 * " ,;" ��ʾ�ո񣬶��ţ��ֺŶ���Ϊ�ָ���<br>
	 * ���磺
	 * split(".,;","sun.com,goole;baidu")
	 * ���Ϊ��
	 * ["sun","com","google","baidu"]
	 * </pre>
	 * 
	 * @param seperators �ָ���s
	 * @param list	���ָ��б�
	 * @return
	 */
	public static String[] split(String list, String seperators) {
		StringTokenizer tokens = new StringTokenizer(list, COMMON_SEPERATORS, false);
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}

	/**
	 * ��ȱʡ�ָ���ָ��ַ�����
	 * 
	 * @param seperators
	 * @param list
	 * @return
	 */
	public static String[] split(String list) {
		return split(list, COMMON_SEPERATORS);
	}
}
