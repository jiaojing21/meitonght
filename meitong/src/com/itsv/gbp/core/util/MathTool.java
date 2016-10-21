package com.itsv.gbp.core.util;

import java.math.BigDecimal;

/**
 * ��ѧ���㸨����
 * 
 * @author admin Ace8@sina.com �������ڣ� 2003.8.28
 */

public class MathTool {

	/**
	 * Ĭ�ϳ������㾫��
	 */
	private static final int DEF_DIV_SCALE = 10;

	private MathTool() {
	}

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������ĺ�
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������Ĳ�
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������Ļ�
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ������������
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v ��Ҫ�������������
	 * @param scale С���������λ
	 * @return ���������Ľ��
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �������ŵ��ַ���ת��Ϊdouble����
	 * 
	 * @param value ����ת�����ַ���
	 * @return ת����õ���double������
	 */
	public static double parse(String value) {
		value = StringUtil.replace(value, ",", "");
		;
		return Double.valueOf(value).doubleValue();
	}

	/**
	 * ��Ҫ���ܣ�Ϊ�˷�ֹ�������Կ�ѧ��������ʽ��ʾ����,ת��Ϊ��ͨ������,��ྫȷ��С�����4λ
	 * 
	 * @param value ����ת�����ַ���
	 * @param length ��ȷ��С������λ��
	 * @return ת����õ���double������
	 */
	public static String convert(double value, int length) {
		java.text.DecimalFormat df = (java.text.DecimalFormat) java.text.NumberFormat.getInstance();
		df.setMinimumFractionDigits(length);
		df.setMaximumFractionDigits(length);
		return df.format(value);
	}

	/**
	 * ��Ҫ���ܣ�Ϊ�˷�ֹ�������Կ�ѧ��������ʽ��ʾ����,ת��Ϊ��ͨ������,��ྫȷ��С�����4λ
	 * 
	 * @param value ����ת�����ַ���
	 * @param length ��ȷ��С������λ��
	 * @param bl true ���ش�","���ŵ��ַ���; false ���ز���","���ŵ��ַ���
	 * @return ת����õ���double������
	 */
	public static String convert(double value, int length, boolean bl) {
		java.text.DecimalFormat df = (java.text.DecimalFormat) java.text.NumberFormat.getInstance();
		df.setMinimumFractionDigits(length);
		df.setMaximumFractionDigits(length);
		if (bl != true) {
			return StringUtil.replace(df.format(value), ",", "");
		} else {
			return df.format(value);
		}
	}

	/**
	 * ��Ҫ���ܣ������ת���ɴ�д
	 * 
	 * @param money ��Ҫ��ת���Ľ������
	 * @return ��д���
	 */
	public static String convertMoneyToChinese(double money) {
		String b = "Ҽ��������½��ƾ�ʰ";
		String chinese = "";
		if (money < 0) {
			chinese = "��";
			money = 0 - money;
		}
		String moneySum = convert(money, 2, false);

		if (moneySum.substring(moneySum.indexOf("."), moneySum.length()).length() < 3) {
			moneySum = moneySum + "0";
			//�Ƚ�Ǯ������Ϊ15λ
		}
		if (moneySum.length() > 15) {
			return "����";
		}
		while (moneySum.length() < 15) {
			moneySum = " " + moneySum;
		}
		if (moneySum.length() > 15) {
			moneySum = moneySum.substring(moneySum.length() - 15, moneySum.length());
		}

		//��ø�λ����ֵ
		String x1 = moneySum.substring(0, 1);
		String x2 = moneySum.substring(1, 2);
		String x3 = moneySum.substring(2, 3);
		String x4 = moneySum.substring(3, 4);
		String x5 = moneySum.substring(4, 5);
		String x6 = moneySum.substring(5, 6);
		String x7 = moneySum.substring(6, 7);
		String x8 = moneySum.substring(7, 8);
		String x9 = moneySum.substring(8, 9);
		String x10 = moneySum.substring(9, 10);
		String x11 = moneySum.substring(10, 11);

		String x12 = moneySum.substring(11, 12);

		String x13 = moneySum.substring(13, 14);

		String x14 = moneySum.substring(14, 15);

		int temp = 0;

		if (!x1.equals(" ")) {
			temp = Integer.parseInt(x1);
			chinese = b.substring(temp - 1, temp) + "Ǫ";
		}
		if (!x2.equals(" ")) {
			temp = Integer.parseInt(x2);
			if (x2.equals("0") && !x3.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x2.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}
		if (!x3.equals(" ")) {
			temp = Integer.parseInt(x3);
			if (x3.equals("0") && !x4.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x3.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "ʰ";
			}
		}
		if (!x4.equals(" ")) {
			temp = Integer.parseInt(x4);
			if (x4.equals("0")) {
				chinese = chinese + "��";
			} else {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}

		if (!x5.equals(" ")) {
			temp = Integer.parseInt(x5);
			if (x5.equals("0") && !x6.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x5.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "Ǫ";

			}
		}
		if (!x6.equals(" ")) {
			temp = Integer.parseInt(x6);
			if (x6.equals("0") && !x7.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x6.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}
		if (!x7.equals(" ")) {
			temp = Integer.parseInt(x7);
			if (x7.equals("0") && !x8.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x7.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "ʰ";
			}
		}
		if (!x8.equals(" ")) {
			temp = Integer.parseInt(x8);
			if (x8.equals("0")) {
				chinese = chinese + "��";
			} else {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}
		if (!x9.equals(" ")) {
			temp = Integer.parseInt(x9);
			if (x9.equals("0") && !x10.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x9.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "Ǫ";
			}
		}
		if (!x10.equals(" ")) {
			temp = Integer.parseInt(x10);
			if (x10.equals("0") && !x11.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x10.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}
		if (!x11.equals(" ")) {
			temp = Integer.parseInt(x11);
			if (x11.equals("0") && !x12.equals("0")) {
				chinese = chinese + "��";
			}
			if (!x11.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "ʰ";
			}
		}
		if (!x12.equals(" ")) {
			temp = Integer.parseInt(x12);
			if (x12.equals("0")) {
				if (x11.equals(" ")) {
					if (x13.equals("0") && x14.equals("0")) {
						chinese = "��Բ";
					}
				} else {
					chinese += "Բ";
				}
			} else {
				chinese = chinese + b.substring(temp - 1, temp) + "Բ";
			}
			//			  if(x12.equals("0") && x11.equals(" ") && x13.equals("0") &&
			// x14.equals("0"))
			//				  chinese = "��Բ";
			//			  else if(x12.equals("0") && (!x13.equals("0") || !x14.equals("0"))){
			//				  chinese = chinese + "Բ";
			//			  }
			//			  else if (x12.equals("0"))
			//				  chinese = chinese + "Բ";
			//			  else
			//				  chinese = chinese + b.substring(temp - 1, temp) + "Բ";
		}
		if (!x13.equals(" ")) {
			temp = Integer.parseInt(x13);
			if ((chinese.length() > 0) && !chinese.equals("��") && x13.equals("0") && !x14.equals("0")) {
				chinese = chinese + "��";
			} else if (!x13.equals("0")) {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}
		if (!x14.equals(" ")) {
			temp = Integer.parseInt(x14);
			if (x14.equals("0")) {
				if (x13.equals("0")) {
					chinese = chinese + "��";
				}
			} else {
				chinese = chinese + b.substring(temp - 1, temp) + "��";
			}
		}
		return chinese;
	}

	public static void main(String[] args) {

	}

}