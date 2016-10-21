package com.itsv.gbp.core.orm.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.springframework.util.Assert;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.support.HsqlHelper;
import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * ֧�ַ�ҳ��ѯ��hibernate����
 * 
 */
public abstract class HibernatePagedDao<T> extends HibernateBaseDao<T> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HibernatePagedDao.class);

	/**
	 * HQL��ҳ��ѯ������ʹ��select count(*) ����ܼ�¼�����ٷ�ҳ��ѯ��<br> 
	 * ��ѯ��ҳ����ÿҳ�ļ�¼����pagedList�����л�ȡ
	 */
	public IPagedList pagedQuery(IPagedList pagedList, String hql, Object... args) throws OrmException {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		// �����һ���ǲ�ѯ����ͨ��countStatementName�������ݼ��Ĵ�С
		if (pagedList.getTotalNum() == -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("��ѯ��¼����,��ʼ.");
			}

			String countQuery = HsqlHelper.createCountSql(hql);
			List countlist = getHibernateTemplate().find(countQuery, args);
			Long totalCount = (Long) getFirst(countlist);

			if (logger.isDebugEnabled()) {
				logger.debug("��ѯ��¼����,����.�����ѯ�����ļ�¼����Ϊ:" + totalCount);
			}

			pagedList.setTotalNum(totalCount.intValue());
		}

		// ��ѯָ����Χ�ļ�¼
		if (logger.isDebugEnabled()) {
			logger.debug("��ҳ��ѯ,��ʼ.��ѯ��ʼ��¼��:" + pagedList.getQueryStartNum() + ",���ѯ��:"
					+ pagedList.getQueryEndNum());
		}

		int startIndex = pagedList.getQueryStartNum() - 1; //��ѯ�Ǵ�0��ʼ�ģ���IpagedList�ļ����Ǵ�1��ʼ
		int queryCount = pagedList.getQueryEndNum() - startIndex;
		List records = query.setFirstResult(startIndex).setMaxResults(queryCount).list();

		pagedList.setSource(records);
		pagedList.setStart(pagedList.getQueryStartNum());

		if (logger.isDebugEnabled()) {
			logger.debug("��ҳ��ѯ,����.�˴β�ѯʵ�ʵõ���¼����Ϊ:" + records.size());
		}
		return pagedList;

	}

	/**
	 * ����DetachedCriteria������з�ҳ��ѯ
	 * 
	 * @param pagedList
	 * @param filterMap
	 * @return
	 */
	public IPagedList pagedQuery(IPagedList pagedList, DetachedCriteria detachedCriteria) throws OrmException {
		return pagedQuery(pagedList, detachedCriteria.getExecutableCriteria(getSession()));
	}

	/**
	 * criteria��ҳ��ѯ��<br>
	 */
	public IPagedList pagedQuery(IPagedList pagedList, Criteria criteria) throws OrmException {
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// �����һ���ǲ�ѯ����ͨ��countStatementName�������ݼ��Ĵ�С
		if (pagedList.getTotalNum() == -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("��ѯ��¼����,��ʼ.");
			}

			//�Ȱ�Projection��OrderBy����ȡ����,���������ִ��Count����
			Projection projection = impl.getProjection();
			List<OrderEntry> orderEntries;
			try {
				orderEntries = (List) getPrivateProperty(impl, "orderEntries");
				setPrivateProperty(impl, "orderEntries", new ArrayList());
			} catch (Exception e) {
				throw new InternalError(" Runtime Exception impossibility throw ");
			}
			
			//ִ�в�ѯ
			int totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult()==null ? "0" : criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
//			int totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());

			//��֮ǰ��Projection��OrderBy�����������ȥ
			criteria.setProjection(projection);
			if (projection == null) {
				criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			}

			try {
				setPrivateProperty(impl, "orderEntries", orderEntries);
			} catch (Exception e) {
				throw new InternalError(" Runtime Exception impossibility throw ");
			}

			pagedList.setTotalNum(totalCount);

			if (logger.isDebugEnabled()) {
				logger.debug("��ѯ��¼����,����.�����ѯ�����ļ�¼����Ϊ:" + totalCount);
			}
		}

		// ��ѯָ����Χ�ļ�¼
		if (logger.isDebugEnabled()) {
			logger.debug("��ҳ��ѯ,��ʼ.��ѯ��ʼ��¼��:" + pagedList.getQueryStartNum() + ",���ѯ��:"
					+ pagedList.getQueryEndNum());
		}

		int startIndex = pagedList.getQueryStartNum() - 1; //��ѯ�Ǵ�0��ʼ�ģ���IpagedList�ļ����Ǵ�1��ʼ
		int queryCount = pagedList.getQueryEndNum() - startIndex;

		List records = criteria.setFirstResult(startIndex).setMaxResults(queryCount).list();

		pagedList.setSource(records);
		pagedList.setStart(pagedList.getQueryStartNum());

		if (logger.isDebugEnabled()) {
			logger.debug("��ҳ��ѯ,����.�˴β�ѯʵ�ʵõ���¼����Ϊ:" + records.size());
		}
		return pagedList;

	}

	/**
	 * ��ȡ��ǰ��������private/protected����
	 */
	private static Object getPrivateProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * ���õ�ǰ��������private/protected����
	 */
	private static void setPrivateProperty(Object object, String propertyName, Object newValue)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}
}
