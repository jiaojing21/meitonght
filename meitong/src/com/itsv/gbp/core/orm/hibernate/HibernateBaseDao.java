package com.itsv.gbp.core.orm.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.util.GenericsUtils;

/**
 * 纯Hibernate Entity DAO基类.
 * 通过泛型，子类无需扩展任何函数即拥有完整的CRUD操作.
 */

public abstract class HibernateBaseDao<T> extends HibernateDaoSupport {

	/**
	 * Dao所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 取得entityClass的函数.
	 * JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重新实现此函数达到相同效果。
	 */
	protected Class getEntityClass() {
		return entityClass;
	}

	/**
	 * 在构造函数中将泛型T.class赋给entityClass
	 */
	public HibernateBaseDao() {
		entityClass = GenericsUtils.getGenericClass(getClass());
	}

	//获取对象，如果没有找到指定的对象，则返回null
	@Transactional(readOnly = true)
	public T get(Serializable id) throws OrmException {
		try {
			return (T) getHibernateTemplate().get(getEntityClass(), id);
		} catch (Exception e) {
			logger.error("获取指定对象时出错", e);
			throw new OrmException("获取指定对象时出错", e);
		}
	}
	@Transactional(readOnly = true)
	public List<T> getAll() throws OrmException {
		try {
			return getHibernateTemplate().loadAll(getEntityClass());
		} catch (Exception e) {
			logger.error("获取所有对象时出错", e);
			throw new OrmException("获取所有对象时出错", e);
		}
	}
	@Transactional
	public void save(Object o) throws OrmException {
		try {
			getHibernateTemplate().save(o);
		} catch (Exception e) {
			logger.error("保存指定对象[" + o + "]时出错", e);
			throw new OrmException("保存指定对象[" + o + "]时出错", e);
		}

	}
	@Transactional
	public void update(Object o) throws OrmException {
		try {
			getHibernateTemplate().update(o);
		} catch (Exception e) {
			logger.error("更新指定对象[" + o + "]时出错", e);
			throw new OrmException("更新指定对象[" + o + "]时出错", e);
		}

	}
	@Transactional
	public void removeById(Serializable id) throws OrmException {
		remove(get(id));
	}
	@Transactional
	public void remove(Object o) throws OrmException {
		try {
			getHibernateTemplate().delete(o);
		} catch (Exception e) {
			logger.error("删除指定对象[" + o + "]时出错", e);
			throw new OrmException("删除指定对象[" + o + "]时出错", e);
		}

	}
	@Transactional(readOnly = true)
	public List find(String hsql, Object... values) throws OrmException {
		try {
			if (values.length == 0)
				return getHibernateTemplate().find(hsql);
			else
				return getHibernateTemplate().find(hsql, values);
		} catch (Exception e) {
			logger.error("执行hsql[" + hsql + "]时出错", e);
			throw new OrmException("执行hsql[" + hsql + "]时出错", e);
		}

	}

	//根据传来的构造条件进行检索
	@Transactional(readOnly = true)
	public List find(DetachedCriteria detachedCriteria) {
		return detachedCriteria.getExecutableCriteria(getSession()).list();
	}

	//创建缺省的离线查询对象
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(getEntityClass());
	}

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的唯一对象
	 */
	@Transactional(readOnly = true)
	public T findUniqueBy(String name, Object value) throws OrmException {
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			return (T) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("findUniqueBy时出错", e);
			throw new OrmException("findUniqueBy时出错", e);
		}

	}

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的对象列表
	 */
	@Transactional(readOnly = true)
	public List<T> findBy(String name, Object value) throws OrmException {
		Assert.hasText(name);
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			return criteria.list();
		} catch (Exception e) {
			logger.error("进行精确条件查询时出错", e);
			throw new OrmException("进行精确条件查询时出错", e);
		}

	}

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象.
	 */
	@Transactional(readOnly = true)
	public List<T> findByLike(String name, String value) throws OrmException {
		Assert.hasText(name);
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));
			return criteria.list();
		} catch (Exception e) {
			logger.error("进行like条件查询时出错", e);
			throw new OrmException("进行like条件查询时出错", e);
		}

	}

	/**
	 * 取得Entity的Criteria.
	 */
	protected Criteria getEntityCriteria() {
		return getSession().createCriteria(getEntityClass());
	}

	/**
	 * 判断对象某列的值在数据库中不存在重复
	 *
	 * @param names 在POJO里相对应的属性名,列组合时以逗号分割<br>
	 *              如"name,loginid,password"
	 */
	public boolean isNotUnique(Object entity, String names) {
		Assert.hasText(names);
		Criteria criteria = getSession().createCriteria(entity.getClass()).setProjection(
				Projections.rowCount());
		String[] nameList = names.split(",");
		try {
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}

			String keyName = getSessionFactory().getClassMetadata(entity.getClass())
					.getIdentifierPropertyName();
			if (keyName != null) {
				Object id = PropertyUtils.getProperty(entity, keyName);
				//如果是update,排除自身
				if (id != null)
					criteria.add(Restrictions.not(Restrictions.eq(keyName, id)));
			}
		} catch (Exception e) {
			logger.error("判断重复时出错", e);
			return false;
		}
		return ((Long) criteria.uniqueResult()) > 0;
	}

	/**
	 * 得到列表数据的第一条记录。<br>
	 * 一些计算总数之类的查询只关心返回的第一条记录。
	 * 
	 * @param list
	 * @return
	 */
	public Object getFirst(List list) {
		return (null != list && !list.isEmpty()) ? list.get(0) : null;
	}
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
}
