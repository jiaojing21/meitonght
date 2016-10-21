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
 * 支持分页查询的hibernate基类
 * 
 */
public abstract class HibernatePagedDao<T> extends HibernateBaseDao<T> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HibernatePagedDao.class);

	/**
	 * HQL分页查询。首先使用select count(*) 获得总记录数，再分页查询。<br> 
	 * 查询的页数和每页的记录数在pagedList对象中获取
	 */
	public IPagedList pagedQuery(IPagedList pagedList, String hql, Object... args) throws OrmException {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		// 如果第一次是查询，先通过countStatementName计算数据集的大小
		if (pagedList.getTotalNum() == -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询记录总数,开始.");
			}

			String countQuery = HsqlHelper.createCountSql(hql);
			List countlist = getHibernateTemplate().find(countQuery, args);
			Long totalCount = (Long) getFirst(countlist);

			if (logger.isDebugEnabled()) {
				logger.debug("查询记录总数,结束.满足查询条件的记录总数为:" + totalCount);
			}

			pagedList.setTotalNum(totalCount.intValue());
		}

		// 查询指定范围的记录
		if (logger.isDebugEnabled()) {
			logger.debug("分页查询,开始.查询起始记录号:" + pagedList.getQueryStartNum() + ",拟查询至:"
					+ pagedList.getQueryEndNum());
		}

		int startIndex = pagedList.getQueryStartNum() - 1; //查询是从0开始的，而IpagedList的计算是从1开始
		int queryCount = pagedList.getQueryEndNum() - startIndex;
		List records = query.setFirstResult(startIndex).setMaxResults(queryCount).list();

		pagedList.setSource(records);
		pagedList.setStart(pagedList.getQueryStartNum());

		if (logger.isDebugEnabled()) {
			logger.debug("分页查询,结束.此次查询实际得到记录个数为:" + records.size());
		}
		return pagedList;

	}

	/**
	 * 根据DetachedCriteria对象进行分页查询
	 * 
	 * @param pagedList
	 * @param filterMap
	 * @return
	 */
	public IPagedList pagedQuery(IPagedList pagedList, DetachedCriteria detachedCriteria) throws OrmException {
		return pagedQuery(pagedList, detachedCriteria.getExecutableCriteria(getSession()));
	}

	/**
	 * criteria分页查询。<br>
	 */
	public IPagedList pagedQuery(IPagedList pagedList, Criteria criteria) throws OrmException {
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 如果第一次是查询，先通过countStatementName计算数据集的大小
		if (pagedList.getTotalNum() == -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询记录总数,开始.");
			}

			//先把Projection和OrderBy条件取出来,清空两者来执行Count操作
			Projection projection = impl.getProjection();
			List<OrderEntry> orderEntries;
			try {
				orderEntries = (List) getPrivateProperty(impl, "orderEntries");
				setPrivateProperty(impl, "orderEntries", new ArrayList());
			} catch (Exception e) {
				throw new InternalError(" Runtime Exception impossibility throw ");
			}
			
			//执行查询
			int totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult()==null ? "0" : criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
//			int totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());

			//将之前的Projection和OrderBy条件重新设回去
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
				logger.debug("查询记录总数,结束.满足查询条件的记录总数为:" + totalCount);
			}
		}

		// 查询指定范围的记录
		if (logger.isDebugEnabled()) {
			logger.debug("分页查询,开始.查询起始记录号:" + pagedList.getQueryStartNum() + ",拟查询至:"
					+ pagedList.getQueryEndNum());
		}

		int startIndex = pagedList.getQueryStartNum() - 1; //查询是从0开始的，而IpagedList的计算是从1开始
		int queryCount = pagedList.getQueryEndNum() - startIndex;

		List records = criteria.setFirstResult(startIndex).setMaxResults(queryCount).list();

		pagedList.setSource(records);
		pagedList.setStart(pagedList.getQueryStartNum());

		if (logger.isDebugEnabled()) {
			logger.debug("分页查询,结束.此次查询实际得到记录个数为:" + records.size());
		}
		return pagedList;

	}

	/**
	 * 获取当前类声明的private/protected变量
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
	 * 设置当前类声明的private/protected变量
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
