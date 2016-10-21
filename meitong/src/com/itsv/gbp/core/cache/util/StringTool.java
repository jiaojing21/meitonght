package com.itsv.gbp.core.cache.util;

import java.util.StringTokenizer;

/**
 * 字符串工具类
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 上午11:20:41
 * @version 1.0
 */
public class StringTool {

	public static String COMMON_SEPERATORS = " ,;:";

	private StringTool() {
	}

	/**
	 * 根据给定前缀，给名称加上限定前缀
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
	 * 根据指定的分割符分割字符串。
	 * 这里使用了StringTokenizer，分割符必须是单个字符，但可以指定多个。
	 * <pre>
	 * " ,;" 表示空格，逗号，分号都作为分隔符<br>
	 * 例如：
	 * split(".,;","sun.com,goole;baidu")
	 * 结果为：
	 * ["sun","com","google","baidu"]
	 * </pre>
	 * 
	 * @param seperators 分隔符s
	 * @param list	待分割列表
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
	 * 按缺省分割符分割字符串。
	 * 
	 * @param seperators
	 * @param list
	 * @return
	 */
	public static String[] split(String list) {
		return split(list, COMMON_SEPERATORS);
	}
}
