package com.itsv.gbp.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ����ʱ�乤���� <br>
 * �ṩһЩ���õ�����ʱ��������������з�����Ϊ��̬������ʵ�������༴��ʹ�á� <br>
 * <br>
 * ��Ϊ���ڸ�ʽ�ļ�����������ο�java API��java.text.SimpleDateFormat <br>
 * The following pattern letters are defined (all other characters from <code>'A'</code>
 * to <code>'Z'</code> and from <code>'a'</code> to <code>'z'</code> are reserved):
 * <blockquote><table border=0 cellspacing=3 cellpadding=0>
 * <tr bgcolor="#ccccff">
 * <th align=left>Letter
 * <th align=left>Date or Time Component
 * <th align=left>Presentation
 * <th align=left>Examples
 * <tr>
 * <td><code>G</code>
 * <td>Era designator
 * <td><a href="#text">Text </a>
 * <td><code>AD</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>y</code>
 * <td>Year
 * <td><a href="#year">Year </a>
 * <td><code>1996</code>;<code>96</code>
 * <tr>
 * <td><code>M</code>
 * <td>Month in year
 * <td><a href="#month">Month </a>
 * <td><code>July</code>;<code>Jul</code>;<code>07</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>w</code>
 * <td>Week in year
 * <td><a href="#number">Number </a>
 * <td><code>27</code>
 * <tr>
 * <td><code>W</code>
 * <td>Week in month
 * <td><a href="#number">Number </a>
 * <td><code>2</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>D</code>
 * <td>Day in year
 * <td><a href="#number">Number </a>
 * <td><code>189</code>
 * <tr>
 * <td><code>d</code>
 * <td>Day in month
 * <td><a href="#number">Number </a>
 * <td><code>10</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>F</code>
 * <td>Day of week in month
 * <td><a href="#number">Number </a>
 * <td><code>2</code>
 * <tr>
 * <td><code>E</code>
 * <td>Day in week
 * <td><a href="#text">Text </a>
 * <td><code>Tuesday</code>;<code>Tue</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>a</code>
 * <td>Am/pm marker
 * <td><a href="#text">Text </a>
 * <td><code>PM</code>
 * <tr>
 * <td><code>H</code>
 * <td>Hour in day (0-23)
 * <td><a href="#number">Number </a>
 * <td><code>0</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>k</code>
 * <td>Hour in day (1-24)
 * <td><a href="#number">Number </a>
 * <td><code>24</code>
 * <tr>
 * <td><code>K</code>
 * <td>Hour in am/pm (0-11)
 * <td><a href="#number">Number </a>
 * <td><code>0</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>h</code>
 * <td>Hour in am/pm (1-12)
 * <td><a href="#number">Number </a>
 * <td><code>12</code>
 * <tr>
 * <td><code>m</code>
 * <td>Minute in hour
 * <td><a href="#number">Number </a>
 * <td><code>30</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>s</code>
 * <td>Second in minute
 * <td><a href="#number">Number </a>
 * <td><code>55</code>
 * <tr>
 * <td><code>S</code>
 * <td>Millisecond
 * <td><a href="#number">Number </a>
 * <td><code>978</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>z</code>
 * <td>Time zone
 * <td><a href="#timezone">General time zone </a>
 * <td><code>Pacific Standard Time</code>;<code>PST</code>;<code>GMT-08:00</code>
 * <tr>
 * <td><code>Z</code>
 * <td>Time zone
 * <td><a href="#rfc822timezone">RFC 822 time zone </a>
 * <td><code>-0800</code> </table> </blockquote>
 * <h4>Examples</h4>
 * 
 * The following examples show how date and time patterns are interpreted in the U.S.
 * locale. The given date and time are 2001-07-04 12:08:56 local time in the U.S. Pacific
 * Time time zone. <blockquote><table border=0 cellspacing=3 cellpadding=0>
 * <tr bgcolor="#ccccff">
 * <th align=left>Date and Time Pattern
 * <th align=left>Result
 * <tr>
 * <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
 * <td><code>2001.07.04 AD at 12:08:56 PDT</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>"EEE, MMM d, ''yy"</code>
 * <td><code>Wed, Jul 4, '01</code>
 * <tr>
 * <td><code>"h:mm a"</code>
 * <td><code>12:08 PM</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>"hh 'o''clock' a, zzzz"</code>
 * <td><code>12 o'clock PM, Pacific Daylight Time</code>
 * <tr>
 * <td><code>"K:mm a, z"</code>
 * <td><code>0:08 PM, PDT</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
 * <td><code>02001.July.04 AD 12:08 PM</code>
 * <tr>
 * <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
 * <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
 * <tr bgcolor="#eeeeff">
 * <td><code>"yyMMddHHmmssZ"</code>
 * <td><code>010704120856-0700</code> </table> </blockquote>
 * 
 * @author admin Ace8@sina.com �������ڣ� 2003.8.28
 */

public class DateTool {

    /**
     * ȱʡ��������ʾ��ʽ�� yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * ȱʡ������ʱ����ʾ��ʽ��yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * ˽�й��췽������ֹ�Ը������ʵ����
     */
    private DateTool() {
    }

    /**
     * �õ�ϵͳ��ǰ����ʱ��
     * 
     * @return ��ǰ����ʱ��
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ����
     * 
     * @return ��ǰ����
     */
    public static String getDate() {
        return getDateTime(DEFAULT_DATE_FORMAT);
    }

    /**
     * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ���ڼ�ʱ��
     * 
     * @return ��ǰ���ڼ�ʱ��
     */
    public static String getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * �õ�ϵͳ��ǰ���ڼ�ʱ�䣬����ָ���ķ�ʽ��ʽ��
     * 
     * @param pattern ��ʾ��ʽ
     * @return ��ǰ���ڼ�ʱ��
     */
    public static String getDateTime(String pattern) {
        Date datetime = Calendar.getInstance().getTime();
        return getDateTime(datetime, pattern);
    }

    /**
     * �õ���ָ����ʽ��ʽ��������
     * 
     * @param date ��Ҫ���и�ʽ��������
     * @param pattern ��ʾ��ʽ
     * @return ����ʱ���ַ���
     */
    public static String getDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * �õ���ǰ���
     * 
     * @return ��ǰ���
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * �õ���ǰ�·�
     * 
     * @return ��ǰ�·�
     */
    public static int getCurrentMonth() {
        //��get�õ����·�����ʵ�ʵ�С1����Ҫ����
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * �õ���ǰ��
     * 
     * @return ��ǰ��
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * ȡ�õ�ǰ�����Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø����� ����Ҫ�õ�������ͬһ������ڣ�������Ϊ-7
     * 
     * @param days ���ӵ�������
     * @return �����Ժ������
     */
    public static Date addDays(int days) {
        return add(getNow(), days, Calendar.DATE);
    }

    /**
     * ȡ��ָ�������Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø�����
     * 
     * @param date ��׼����
     * @param days ���ӵ�������
     * @return �����Ժ������
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * ȡ�õ�ǰ�����Ժ�ĳ�µ����ڡ����Ҫ�õ���ǰ�·ݵ����ڣ������ø�����
     * 
     * @param months ���ӵ��·���
     * @return �����Ժ������
     */
    public static Date addMonths(int months) {
        return add(getNow(), months, Calendar.MONTH);
    }

    /**
     * ȡ��ָ�������Ժ�ĳ�µ����ڡ����Ҫ�õ���ǰ�·ݵ����ڣ������ø����� ע�⣬���ܲ���ͬһ���ӣ�����2003-1-31����һ������2003-2-28
     * 
     * @param date ��׼����
     * @param months ���ӵ��·���
     * @return �����Ժ������
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * �ڲ�������Ϊָ������������Ӧ������������
     * 
     * @param date ��׼����
     * @param amount ���ӵ�����
     * @param field ���ӵĵ�λ���꣬�»�����
     * @return �����Ժ������
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * ��������������������� �õ�һ�����ڼ�ȥ�ڶ��������ǰһ������С�ں�һ�����ڣ��򷵻ظ���
     * 
     * @param one ��һ������������Ϊ��׼
     * @param two �ڶ�������������Ϊ�Ƚ�
     * @return ���������������
     */
    public static long diffDays(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * ����������������·��� ���ǰһ������С�ں�һ�����ڣ��򷵻ظ���
     * 
     * @param one ��һ������������Ϊ��׼
     * @param two �ڶ�������������Ϊ�Ƚ�
     * @return ������������·���
     */
    public static int diffMonths(Date one, Date two) {

        Calendar calendar = Calendar.getInstance();

        //�õ���һ�����ڵ���ֺ��·���
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONDAY);

        //�õ��ڶ������ڵ���ݺ��·�
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONDAY);

        return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
    }

    /**
     * ��һ���ַ����ø����ĸ�ʽת��Ϊ�������͡� <br>
     * ע�⣺�������null�����ʾ����ʧ��
     * 
     * @param datestr ��Ҫ�����������ַ���
     * @param pattern �����ַ����ĸ�ʽ��Ĭ��Ϊ��yyyy-MM-dd������ʽ
     * @return �����������
     */
    public static Date parse(String datestr, String pattern) {
        Date date = null;

        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            //
        }

        return date;
    }

    /**
     * ���ر��µ����һ��
     * 
     * @return �������һ�������
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(getNow());
    }

    /**
     * ���ظ��������е��·��е����һ��
     * 
     * @param date ��׼����
     * @return �������һ�������
     */
    public static Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //����������Ϊ��һ�µ�һ��
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        //��ȥ1�죬�õ��ļ����µ����һ��
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }
    public static Date objToDate(Object value) {
		if(null != value){
			//String str_date = (String) value;
			//System.out.println(str_date);
			java.sql.Date date = (java.sql.Date) value;
			java.util.Date d=new java.util.Date (date.getTime());
			return d;
		}
		return null;
		
	}
    public static void main(String[] args) {
    }

}