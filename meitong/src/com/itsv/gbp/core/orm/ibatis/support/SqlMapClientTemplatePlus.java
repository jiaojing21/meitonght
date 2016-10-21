package com.itsv.gbp.core.orm.ibatis.support;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * ˵����spring�ṩ��SqlMapClientTemplate����ǿ��<br>
 * ������һ��query���������ؾ�����ҳ�ļ�¼�� <br>
 * 
 * 
 * Ϊ����Ч�ʵط�ҳ����ѯǰ����Ҫ��select count(1) from ... ������ò�ѯ�ܷ��صļ�¼�� 
 * ��ô������Ҫ��������䣺
 * һ���������Ĳ�ѯ��䣬��: select * from users 
 * ��һ�������׵ļ����¼������䣬��: select count(1) from users 
 * �����ڽ��з�ҳ��ѯʱ����Ҫͬʱָ����������ѯ��sqlmap�ļ�������ơ�
 * 
 * @author admin 2004-9-24
 */
public class SqlMapClientTemplatePlus extends SqlMapClientTemplate {

	// log����
	private static Log log = LogFactory.getLog(SqlMapClientTemplatePlus.class);

	/**
	 * ��ѯ���ݿ⣬���ط�ҳ��ָ��ҳ��ļ�¼��
	 * 
	 * @param queryStatementName ��ѯstatement����id
	 * @param countStatementName ��ò�ѯ���׵ļ����ܼ�¼����select���
	 * @param limitedList LimitedList�ӿڶ���
	 */
	public IPagedList query(final String queryStatementName, final String countStatementName,
			final IPagedList limitedList) throws DataAccessException {
		// �����һ���ǲ�ѯ����ͨ��countStatementName�������ݼ��Ĵ�С
		if (limitedList.getTotalNum() == -1) {
			if (log.isDebugEnabled()) {
				log.debug("��ѯ��¼����,��ʼ.");
			}

			Integer total = (Integer) queryForObject(countStatementName, limitedList.getParam());

			if (log.isDebugEnabled()) {
				log.debug("��ѯ��¼����,����.�����ѯ�����ļ�¼����Ϊ:" + total);
			}

			limitedList.setTotalNum(total.intValue());
		}

		// ��ѯָ����Χ�ļ�¼
		if (log.isDebugEnabled()) {
			log.debug("��ҳ��ѯ,��ʼ.��ѯ��ʼ��¼��:" + limitedList.getQueryStartNum() + ",���ѯ��:"
					+ limitedList.getQueryEndNum());
		}

		List records = queryForList(queryStatementName, limitedList.getParam(), limitedList
				.getQueryStartNum() - 1, limitedList.getQueryEndNum());

		if (log.isDebugEnabled()) {
			log.debug("��ҳ��ѯ,����.�˴β�ѯʵ�ʵõ���¼����Ϊ:" + records.size());
		}

		limitedList.setSource(records);
		limitedList.setStart(limitedList.getQueryStartNum());

		return limitedList;
	}

}