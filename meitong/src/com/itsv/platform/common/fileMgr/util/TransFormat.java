package com.itsv.platform.common.fileMgr.util;

import java.io.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransFormat {
	public static String unicodeToGB(String strIn) {
		byte[] b;
		String strOut = null;
		if (strIn == null || (strIn.trim()).equals(""))
			return strIn;
		try {
			b = strIn.getBytes("GB2312");
			strOut = new String(b, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		return strOut;
	}

	public static String GBToUnicode(String strIn) {
		String strOut = null;
		if (strIn == null || (strIn.trim()).equals("")) {
			return strIn;
		}
		try {
			byte[] b = strIn.getBytes("ISO-8859-1");
			strOut = new String(b, "GB2312");
		} catch (Exception e) {
		}
		return strOut;
	}

	public static String print(String str, int count) {
		String sResult = "";
		for (int i = 0; i < count; i++) {
			sResult = sResult + str;
		}
		return sResult;
	}

	/**
	 * 替换字符串
	 */
	public static String replace(String line, String oldString, String newString) {
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public String toNull(String strIn) {
		String strOut = strIn;
		if (strIn != null && (strIn.trim()).equals("")) {
			strOut = null;
		}
		return strOut;
	}

	/**
	 * n是你想保留小数点后几位
	 */
	public static float FloatOnlyN(float x, int n) {
		if (x != 0.0) {
			String a = Float.toString(x);
			int m = a.indexOf(".");
			int temps = a.length() - m - 1;
			if (temps >= n) {
				String y = a.substring(0, m + n + 1);
				x = Float.parseFloat(y);
				return x;
			}
		} else {
			return x;
		}
		return x;
	}

	/**
	 * 获得当前日期
	 */
	public static Date getCurDate() {
		Date tjDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-mm-dd hh:mm:ss", java.util.Locale.US);
			Calendar calendar = Calendar.getInstance();
			String strdate = calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ (calendar.get(Calendar.DAY_OF_MONTH)) + " "
					+ (calendar.get(Calendar.HOUR)) + ":"
					+ (calendar.get(Calendar.MINUTE)) + ":"
					+ (calendar.get(Calendar.SECOND));
			tjDate = format.parse(strdate);
		} catch (Exception e) {
		}
		return tjDate;
	}
}
