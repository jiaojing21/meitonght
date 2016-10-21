package com.itsv.gbp.core.util;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ˵������������Ĺ�����
 * 
 * @author admin 2005-1-17
 */
public class TreeCodeTool implements Serializable {

	private static final long serialVersionUID = -8429160964040427247L;

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(TreeCodeTool.class);

	/**
	 * �жϸò�����Ƿ������Ӧ��ģʽ�������У����λ���Ƿ���Ϲ淶��
	 * 
	 * @param code Ҫ����Ĳ�����ַ���
	 * @param pattern �����ʽ����3-3-3
	 * @return
	 */
	public static boolean isValidate(String code, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "-");
		int step = 0;
		while (st.hasMoreTokens()) {
			try {
				step = step + Integer.parseInt((String) st.nextElement());
			} catch (NumberFormatException e) {
				logger.error("�����쳣�������ʽ�ַ�������p=" + pattern);
				return false;
			}

			if (code.length() == step) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �õ��ò�α���ĸ�����
	 * 
	 * @param code Ҫ����Ĳ�����ַ���
	 * @param pattern �����ʽ����3-3-3
	 * @return
	 */
	public static String getFatherCode(String code, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "-");
		int step = 0;//��ǰ����
		int position = 0; //��ǰλ��
		while (st.hasMoreTokens()) {
			try {
				step = Integer.parseInt((String) st.nextElement());
			} catch (NumberFormatException e) {
				logger.error("�����쳣�������ʽ�ַ�������p=" + pattern);
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
	 * �ò�α����ǵڼ�����
	 * 
	 * @param code Ҫ����Ĳ�����ַ���
	 * @param pattern �����ʽ����3-3-3
	 * @return
	 */
	public static int getLevel(String code, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "-");
		int level = 0;//��ǰ����
		int position = 0; //��ǰλ��
		while (st.hasMoreTokens()) {
			level += 1;
			try {
				position += Integer.parseInt((String) st.nextElement());
			} catch (NumberFormatException e) {
				logger.error("�����쳣�������ʽ�ַ�������p=" + pattern);
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