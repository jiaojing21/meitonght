package com.itsv.gbp.core.util;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 说明：处理层次码的工具类
 * 
 * @author admin 2005-1-17
 */
public class TreeCodeTool implements Serializable {

	private static final long serialVersionUID = -8429160964040427247L;

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(TreeCodeTool.class);

	/**
	 * 判断该层次码是否符合相应的模式。这儿仅校验了位数是否符合规范。
	 * 
	 * @param code 要检验的层次码字符串
	 * @param pattern 编码格式。如3-3-3
	 * @return
	 */
	public static boolean isValidate(String code, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "-");
		int step = 0;
		while (st.hasMoreTokens()) {
			try {
				step = step + Integer.parseInt((String) st.nextElement());
			} catch (NumberFormatException e) {
				logger.error("处理异常：编码格式字符串错误。p=" + pattern);
				return false;
			}

			if (code.length() == step) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到该层次编码的父编码
	 * 
	 * @param code 要检验的层次码字符串
	 * @param pattern 编码格式。如3-3-3
	 * @return
	 */
	public static String getFatherCode(String code, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "-");
		int step = 0;//当前步长
		int position = 0; //当前位置
		while (st.hasMoreTokens()) {
			try {
				step = Integer.parseInt((String) st.nextElement());
			} catch (NumberFormatException e) {
				logger.error("处理异常：编码格式字符串错误。p=" + pattern);
				return "";
			}
			position += step;
			if (code.length() == position) {
				return code.substring(0, position - step);
			}
		}
		return "";
	}

	/**
	 * 该层次编码是第几级的
	 * 
	 * @param code 要检验的层次码字符串
	 * @param pattern 编码格式。如3-3-3
	 * @return
	 */
	public static int getLevel(String code, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "-");
		int level = 0;//当前级次
		int position = 0; //当前位置
		while (st.hasMoreTokens()) {
			level += 1;
			try {
				position += Integer.parseInt((String) st.nextElement());
			} catch (NumberFormatException e) {
				logger.error("处理异常：编码格式字符串错误。p=" + pattern);
				return 0;
			}

			if (code.length() == position) {
				return level;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		String p = "3-2-3";
		String code1 = "00102003";
		String code2 = "001";
		String code3 = "001020033";
	}
}