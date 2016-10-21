package com.itsv.gbp.core.orm.hibernate.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class HsqlHelper {

	private static final String countSql = " select count(*) ";

	/**
	 * ��ָ��sql��䣬���ɶ�Ӧ�Ĳ�ѯ��¼�����������
	 * @param sql
	 * @return
	 */
	public static String createCountSql(String sql) {
		return countSql + removeSelect(removeOrders(sql));
	}

	/**
	 * ȥ��select �Ӿ䣬δ����union�����
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * ȥ��orderby �Ӿ�
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
