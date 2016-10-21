package com.itsv.platform.common.fileMgr.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CalendarUtil {

	public static SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy");

	public static SimpleDateFormat FORMAT2 = new SimpleDateFormat("MM");

	public static SimpleDateFormat FORMAT3 = new SimpleDateFormat("dd");

	public static SimpleDateFormat FORMAT4 = new SimpleDateFormat("HH");

	public static SimpleDateFormat FORMAT5 = new SimpleDateFormat("mm");

	public static SimpleDateFormat FORMAT6 = new SimpleDateFormat("s");

	public static SimpleDateFormat FORMAT7 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat FORMAT8 = new SimpleDateFormat("yyyy/MM/dd");

	public static SimpleDateFormat FORMAT9 = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static SimpleDateFormat FORMAT10 = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static SimpleDateFormat FORMAT11 = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat FORMAT12 = new SimpleDateFormat(
			"yyyyMMddHHmmssSS");

	public static SimpleDateFormat FORMAT13 = new SimpleDateFormat(
			"yyyyMMddHHmmssSS");

	public static SimpleDateFormat FORMAT14 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat FORMAT15 = new SimpleDateFormat(
			"yyyy��MM��dd��");

	public static SimpleDateFormat FORMAT16 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.M");

	/**
	 * ת��Ӣ�����ڵ���������
	 */
	public static String changeENCalendarToCNCalendar(String enCalendar) {
		return FORMAT15.format(Timestamp.valueOf(enCalendar + " 00:00:00"));
	}

	/**
	 * ȡ����ǰ�����
	 */
	public static String getCNCalendarByFormat() {
		return FORMAT15.format(getCurrentTime());
	}

	/**
	 * ȡ����ǰ�����
	 */
	public static String getCurrYear() {
		return FORMAT1.format(getCurrentTime());
	}

	/**
	 * ȡ����ǰ���·�
	 */
	public static String getCurrMonth() {
		return FORMAT2.format(getCurrentTime());
	}

	/**
	 * ȡ����ǰ����
	 */
	public static String getCurrDay() {
		return FORMAT3.format(getCurrentTime());
	}

	/**
	 * ȡ����ǰ��ʱ���Сʱ
	 */
	public static String getCurrHour() {
		return FORMAT4.format(getCurrentTime());
	}

	/**
	 * ȡ����ǰ��ʱ��ķ���
	 */
	public static String getCurrMinute() {
		return FORMAT5.format(getCurrentTime());
	}

	/**
	 * ȡ����ǰ��ʱ�����
	 */
	public static String getCurrSecond() {
		return FORMAT6.format(getCurrentTime());
	}

	/**
	 * ���ݸ�ʽ������ȡ����ǰ��ϵͳʱ��
	 */
	public static String getCalendarByFormat(SimpleDateFormat sdf) {
		return sdf.format(getCurrentTime());
	}

	/**
	 * ���ݸ�ʽ������ȡ����ǰ��ϵͳʱ��
	 */
	public synchronized static String getSynchronizedCalendarByFormat(
			SimpleDateFormat sdf) {
		return sdf.format(getCurrentTime());
	}

	/**
	 * ���ݸ�ʽ������ȡ����ǰ��ϵͳʱ��
	 */
	public static String getCalendarByFormat(String str) {
		SimpleDateFormat format = new SimpleDateFormat(str);
		return format.format(getCurrentTime());
	}

	/**
	 * ϵͳ��ǰʱ��
	 */
	public static String getCurrentDateTime() {
		return FORMAT7.format(getCurrentTime());
	}

	public static Timestamp getCurrentTime() {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		return t;
	}

	/**
	 * ��ǰ���ڵ�ǰ������ʱ��
	 */
	public static String getBeforeDateBySecond(SimpleDateFormat sdf, long second) {
		Timestamp t = new Timestamp(System.currentTimeMillis() - second * 1000);
		return sdf.format(t);
	}

	/**
	 * ��ǰ���ڵ�ǰ������ʱ��
	 */
	public static String getBeforeDateBySecond(String format, long second) {
		Timestamp t = new Timestamp(System.currentTimeMillis() - second * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(t);
	}

	/**
	 * ��ǰ���ڵĺ������ʱ��
	 */
	public static String getAfterDateBySecond(SimpleDateFormat sdf, long second) {
		Timestamp t = new Timestamp(System.currentTimeMillis() + second * 1000);
		return sdf.format(t);
	}

	/**
	 * ��ǰ���ڵĺ������ʱ��
	 */
	public static String getAfterDateBySecond(String format, long second) {
		Timestamp t = new Timestamp(System.currentTimeMillis() + second * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(t);
	}
}
