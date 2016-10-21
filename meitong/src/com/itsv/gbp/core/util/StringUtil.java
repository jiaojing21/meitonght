package com.itsv.gbp.core.util;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * �ַ��������࣬�����õ��ַ�������
 * 
 * @author admin Ace8@sina.com �������ڣ� 2003.8.28
 */

public class StringUtil {

    /**
     * ȱʡ���ַ����ָ��
     */
    public static String DEFAULT_DELIM = "$*";

    /**
     * ˽�й��췽������ֹ���ʵ��������Ϊ�����಻��Ҫʵ������
     */
    private StringUtil() {
    }

    /**
     * ���ַ�������ʹ��ȱʡ�ķָ����ϲ���һ���ַ�����
     * 
     * @param array �ַ�������
     * @return �ϲ�����ַ���
     */
    public static String join(String[] array) {
        return join(array, DEFAULT_DELIM);
    }

    /**
     * ���ַ�������ʹ��ָ���ķָ����ϲ���һ���ַ�����
     * 
     * @param array �ַ�������
     * @param delim �ָ�����Ϊnull��ʱ��ʹ��ȱʡ�ָ�������ţ�
     * @return �ϲ�����ַ���
     */
    public static String join(String[] array, String delim) {
        int length = array.length - 1;
        if (delim == null) {
            delim = DEFAULT_DELIM;
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[length]);
        return result.toString();
    }

    /**
     * ���ַ���ʹ��ȱʡ�ָ�������ţ����ֵĵ������顣
     * 
     * @param source ��Ҫ���л��ֵ�ԭ�ַ���
     * @return �����Ժ�����飬���sourceΪnull��ʱ�򷵻���sourceΪΨһԪ�ص����顣
     */
    public static String[] split(String source) {
        return split(source, DEFAULT_DELIM);
    }

    /**
     * �˷������������ַ���sourceʹ��delim����Ϊ�������顣 ע�⣺�ָ��ַ�����ÿһ�� <b>(ANY) </b>���ַ�����Ϊ�����ķָ���� <br>
     * �ٸ����ӣ� <br>
     * "mofit.com.cn"��"com"�ָ��Ľ���������ַ���"fit."��"."��"n"��������"mofit."��".cn"��
     * 
     * @param source ��Ҫ���л��ֵ�ԭ�ַ���
     * @param delim ���ʵķָ��ַ���
     * @return �����Ժ�����飬���sourceΪnull��ʱ�򷵻���sourceΪΨһԪ�ص����飬 ���delimΪnull��ʹ�ö�����Ϊ�ָ��ַ�����
     */
    public static String[] split(String source, String delim) {
        String[] wordLists;
        if (source == null) {
            wordLists = new String[1];
            wordLists[0] = source;
            return wordLists;
        }
        if (delim == null) {
            delim = DEFAULT_DELIM;
        }
        StringTokenizer st = new StringTokenizer(source, delim);

        int total = st.countTokens();
        wordLists = new String[total];
        for (int i = 0; i < total; i++) {
            wordLists[i] = st.nextToken();
        }
        return wordLists;
    }

    /**
     * �ַ����������Ƿ����ָ�����ַ����� ע�⣺׼ȷ��˵Ӧ����ƥ�䣬�����ǰ����� <br>
     * �ٸ����ӣ��ַ�������"mofit.com.cn","neusoft.com"�� <b>������ </b>"com"�� <br>
     * ���� <b>���� </b>"mofti.com.cn"��
     * 
     * @param strings �ַ�������
     * @param string �ַ���
     * @param caseSensitive �Ƿ��Сд����
     * @return ����ʱ����true�����򷵻�false
     */
    public static boolean contains(String[] strings, String string, boolean caseSensitive) {
        for (int i = 0; i < strings.length; i++) {
            if (caseSensitive == true) {
                if (strings[i].equals(string)) {
                    return true;
                }
            } else {
                if (strings[i].equalsIgnoreCase(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * �ַ����������Ƿ����ָ�����ַ�������Сд���С� <br>
     * ע�⣺׼ȷ��˵Ӧ����ƥ�䣬�����ǰ����� <br>
     * �ٸ����ӣ��ַ�������"mofit.com.cn","neusoft.com"�� <b>������ </b>"com"�� <br>
     * ���� <b>���� </b>"mofti.com.cn"��
     * 
     * @param strings �ַ�������
     * @param string �ַ���
     * @return ����ʱ����true�����򷵻�false
     */
    public static boolean contains(String[] strings, String string) {
        return contains(strings, string, true);
    }

    /**
     * ȥ����߶���Ŀո�
     * 
     * @param value ��ȥ��߿ո���ַ���
     * @return ȥ����߿ո����ַ���
     */
    public static String trimLeft(String value) {
        String result = value;
        if (result == null) {
            return result;
        }
        char ch[] = result.toCharArray();
        int index = -1;
        for (int i = 0; i < ch.length; i++) {
            if (Character.isWhitespace(ch[i])) {
                index = i;
            } else {
                break;
            }
        }
        if (index != -1) {
            result = result.substring(index + 1);
        }
        return result;
    }

    /**
     * ȥ���ұ߶���Ŀո�
     * 
     * @param value ��ȥ�ұ߿ո���ַ���
     * @return ȥ���ұ߿ո����ַ���
     */
    public static String trimRight(String value) {
        String result = value;
        if (result == null) {
            return result;
        }
        char ch[] = result.toCharArray();
        int endIndex = -1;
        for (int i = ch.length - 1; i > -1; i--) {
            if (Character.isWhitespace(ch[i])) {
                endIndex = i;
            } else {
                break;
            }
        }
        if (endIndex != -1) {
            result = result.substring(0, endIndex);
        }
        return result;
    }

    /**
     * �õ��ַ������ֽڳ��ȡ�����ռ�����ֽڣ���ĸռһ���ֽ�
     * 
     * @param source �ַ���
     * @return �ַ������ֽڳ���
     */
    public static int getLength(String source) {
        int len = 0;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            int highByte = c >>> 8;
            len += highByte == 0 ? 1 : 2;
        }
        return len;
    }

    /**
     * ʹ�ø������ִ��滻Դ�ַ�����ָ�����ִ���
     * 
     * @param mainString Դ�ַ���
     * @param oldString ���滻���ִ�
     * @param newString �滻�ִ�
     * @return �滻����ַ���
     */
    public final static String replace(String mainString, String oldString, String newString) {
        if (mainString == null) {
            return null;
        }
        int i = mainString.lastIndexOf(oldString);
        if (i < 0) {
            return mainString;
        }
        StringBuffer mainSb = new StringBuffer(mainString);
        while (i >= 0) {
            mainSb.replace(i, i + oldString.length(), newString);
            i = mainString.lastIndexOf(oldString, i - 1);
        }
        return mainSb.toString();
    }

    /**
     * ���������ַ���ת��Ϊ����GBK������ַ�����
     * 
     * @param str �����ַ���
     * @return ��GBK�������ַ�����������쳣���򷵻�ԭ�����ַ���
     */
    public final static String toChinese(final String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        String retVal = str;
        try {
            retVal = new String(str.getBytes("ISO8859_1"), "GBK");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return retVal;
    }

    /**
     * ������������GBK����ת��ΪUNICODE������ַ�����
     * 
     * @param str �����ַ���
     * @return ��GBK�������ַ�����������쳣���򷵻�ԭ�����ַ���
     */
    public final static String toUNICODE(final String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        String retVal = str;
        try {
            retVal = new String(str.getBytes("ZHS16GBK"), "GBK");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return retVal;
    }

    /**
     * �����ַ�����ʾ����html���еļ����š����š����Ӻŵ���ת�������� <br>
     * �����÷����ڽ��յ��ͻ��˴������ַ���ʱ��������ת����ֱ�Ӵ������ݿ⣻ <br>
     * �ڴ����ݿ���ȡ���������ͻ�����html��ʾʱ����ת����
     * 
     * @param input ��Ҫ�����ַ���
     * @return ת������ִ�
     */
    public final static String convertToHTML(String input) {
        if (null == input || "".equals(input)) {
            return input;
        }

        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == '&') {
                buf.append("&amp;");
            } else if (ch == '"') {
                buf.append("&quot;");
            } else if (ch == '\n') {
                buf.append("<BR/>");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * �����ַ�������ʱ��Ҫ�õ��ı���ַ���
     */
    private static String ENCRYPT_IN = "YN8K1JOZVURB3MDETS5GPL27AXW`IHQ94C6F0~qwert!@yuiop#$asdfghj%kl^&*zxc vbn(m)_+|{}:\"<>?-=\\[];,./'";

    /**
     * �����ַ�������ʱ��Ҫ�õ���ת���ַ���
     */
    private static String ENCRYPT_OUT = "qazwsxcderfvbgtyhnmjuiklop~!@#$%^&*()_+|{ }:\"<>?-=\\[];,./'ABCDE`FGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * �Ը����ַ������м��ܲ���
     * 
     * @param inPass �����ܵ��ַ���
     * @return ���ܺ���ַ���
     */
    public static String encrypt(String inPass) {
        String stringIn = ENCRYPT_IN;
        String stringOut = ENCRYPT_OUT;
        int time1 = Calendar.getInstance().get(Calendar.MINUTE);
        int time2 = Calendar.getInstance().get(Calendar.SECOND);
        int offset = (time1 + time2) % 95;
        String outPass = stringIn.substring(offset, offset + 1);
        stringIn = stringIn + stringIn;
        stringIn = stringIn.substring(offset, offset + 95);
        String temp = "";
        for (int i = 0; i <= inPass.length() - 1; i++) {
            temp = temp + stringOut.charAt(stringIn.indexOf(inPass.charAt(i)));

        }
        outPass = outPass + temp;
        return outPass;
    }

    /**
     * �Ը����ַ������н��ܲ���
     * 
     * @param outPass �����ܵ��ַ���
     * @return ���ܻ�ԭ����ַ���
     */
    public static String decrypt(String outPass) {
        String stringIn = ENCRYPT_IN;
        String stringOut = ENCRYPT_OUT;
        int offset = stringIn.indexOf(outPass.charAt(0));
        stringIn = stringIn + stringIn;
        stringIn = stringIn.substring(offset, offset + 95);
        outPass = outPass.substring(1);
        String inPass = "";
        for (int i = 0; i <= outPass.length() - 1; i++) {
            inPass = inPass + stringIn.charAt(stringOut.indexOf(outPass.charAt(i)));

        }
        return inPass;
    }

    //ָ�����ַ����ۼ�
    public static String strAdd(String chr, int len) {
        if (len > 0) {
            StringBuffer ret = new StringBuffer(len);
            for (int i = 0; i < len; i++) {
                ret.append(chr);
            }
            return (ret.toString());
        } else {
            return "";
        }
    }

    //���ַ������㵽ָ���ĳ��ȣ�����߲���chrָ�����ַ�
    public static String lPad(String source, String chr, int len) {
        int lenleft = len - source.length();
        if (lenleft < 0) {
            lenleft = 0;
        }
        return (strAdd(chr, lenleft) + source);
    }

    //���ַ������㵽ָ���ĳ��ȣ����ұ߲���chrָ�����ַ�
    public static String rPad(String source, String chr, int len) {
        int lenleft = len - source.length();
        if (lenleft < 0) {
            lenleft = 0;
        }
        return (source + strAdd(chr, lenleft));
    }

    public static void main(String[] args) {
    }
}