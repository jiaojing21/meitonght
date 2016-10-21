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
 * ��Hibernate Entity DAO����.
 * ͨ�����ͣ�����������չ�κκ�����ӵ��������CRUD����.
 */

public abstract class HibernateBaseDao<T> extends HibernateDaoSupport {

	/**
	 * Dao�������Entity����.
	 */
	protected Class<T> entityClass;

	/**
	 * ȡ��entityClass�ĺ���.
	 * JDK1.4��֧�ַ��͵���������׿�Class<T> entityClass,����ʵ�ִ˺����ﵽ��ͬЧ����
	 */
	protected Class getEntityClass() {
		return entityClass;
	}

	/**
	 * �ڹ��캯���н�����T.class����entityClass
	 */
	public HibernateBaseDao() {
		entityClass = GenericsUtils.getGenericClass(getClass());
	}

	//��ȡ�������û���ҵ�ָ���Ķ����򷵻�null
	@Transactional(readOnly = true)
	public T get(Serializable id) throws OrmException {
		try {
			return (T) getHibernateTemplate().get(getEntityClass(), id);
		} catch (Exception e) {
			logger.error("��ȡָ������ʱ����", e);
			throw new OrmException("��ȡָ������ʱ����", e);
		}
	}
	@Transactional(readOnly = true)
	public List<T> getAll() throws OrmException {
		try {
			return getHibernateTemplate().loadAll(getEntityClass());
		} catch (Exception e) {
			logger.error("��ȡ���ж���ʱ����", e);
			throw new OrmException("��ȡ���ж���ʱ����", e);
		}
	}
	@Transactional
	public void save(Object o) throws OrmException {
		try {
			getHibernateTemplate().save(o);
		} catch (Exception e) {
			logger.error("����ָ������[" + o + "]ʱ����", e);
			throw new OrmException("����ָ������[" + o + "]ʱ����", e);
		}

	}
	@Transactional
	public void update(Object o) throws OrmException {
		try {
			getHibernateTemplate().update(o);
		} catch (Exception e) {
			logger.error("����ָ������[" + o + "]ʱ����", e);
			throw new OrmException("����ָ������[" + o + "]ʱ����", e);
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
			logger.error("ɾ��ָ������[" + o + "]ʱ����", e);
			throw new OrmException("ɾ��ָ������[" + o + "]ʱ����", e);
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
			logger.error("ִ��hsql[" + hsql + "]ʱ����", e);
			throw new OrmException("ִ��hsql[" + hsql + "]ʱ����", e);
		}

	}

	//���ݴ����Ĺ����������м���
	@Transactional(readOnly = true)
	public List find(DetachedCriteria detachedCriteria) {
		return detachedCriteria.getExecutableCriteria(getSession()).list();
	}

	//����ȱʡ�����߲�ѯ����
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(getEntityClass());
	}

	/**
	 * ����������������ֵ��ѯ����.
	 *
	 * @return ����������Ψһ����
	 */
	@Transactional(readOnly = true)
	public T findUniqueBy(String name, Object value) throws OrmException {
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			return (T) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("findUniqueByʱ����", e);
			throw new OrmException("findUniqueByʱ����", e);
		}

	}

	/**
	 * ����������������ֵ��ѯ����.
	 *
	 * @return ���������Ķ����б�
	 */
	@Transactional(readOnly = true)
	public List<T> findBy(String name, Object value) throws OrmException {
		Assert.hasText(name);
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			return criteria.list();
		} catch (Exception e) {
			logger.error("���о�ȷ������ѯʱ����", e);
			throw new OrmException("���о�ȷ������ѯʱ����", e);
		}

	}

	/**
	 * ����������������ֵ��Like AnyWhere��ʽ��ѯ����.
	 */
	@Transactional(readOnly = true)
	public List<T> findByLike(String name, String value) throws OrmException {
		Assert.hasText(name);
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));
			return criteria.list();
		} catch (Exception e) {
			logger.error("����like������ѯʱ����", e);
			throw new OrmException("����like������ѯʱ����", e);
		}

	}

	/**
	 * ȡ��Entity��Criteria.
	 */
	protected Criteria getEntityCriteria() {
		return getSession().createCriteria(getEntityClass());
	}

	/**
	 * �ж϶���ĳ�е�ֵ�����ݿ��в������ظ�
	 *
	 * @param names ��POJO�����Ӧ��������,�����ʱ�Զ��ŷָ�<br>
	 *              ��"name,loginid,password"
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
				//�����update,�ų�����
				if (id != null)
					criteria.add(Restrictions.not(Restrictions.eq(keyName, id)));
			}
		} catch (Exception e) {
			logger.error("�ж��ظ�ʱ����", e);
			return false;
		}
		return ((Long) criteria.uniqueResult()) > 0;
	}

	/**
	 * �õ��б����ݵĵ�һ����¼��<br>
	 * һЩ��������֮��Ĳ�ѯֻ���ķ��صĵ�һ����¼��
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
