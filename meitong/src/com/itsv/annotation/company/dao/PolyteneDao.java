package com.itsv.annotation.company.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import com.itsv.annotation.company.vo.Polytene;

/**
 * ����ϩ���ܱ��������ݷ�����
 * 
 * 
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */
 @Repository @Transactional
public class PolyteneDao extends HibernatePagedDao<Polytene> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PolyteneDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Polytene) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Polytene) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Polytene> queryByObject(Polytene polytene) {
		return find(buildCriteriaByVO(polytene));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Polytene polytene) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(polytene));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Polytene polytene){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (polytene.getId() != null ) {
    	dc.add(Restrictions.eq("id", polytene.getId()));
    }	

    //��ҵid 
    if (polytene.getCompanyid() != null && polytene.getCompanyid().length() > 0) {
    	dc.add(Restrictions.like("companyid", polytene.getCompanyid(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (polytene.getCapacity() != null && polytene.getCapacity().length() > 0) {
    	dc.add(Restrictions.like("capacity", polytene.getCapacity(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (polytene.getProduction() != null && polytene.getProduction().length() > 0) {
    	dc.add(Restrictions.like("production", polytene.getProduction(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (polytene.getType() != null && polytene.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", polytene.getType()));
    }	

    //ʱ�� 
    if (polytene.getPtime() != null && polytene.getPtime().length() > 0) {
    	dc.add(Restrictions.eq("ptime", polytene.getPtime()));
    }	

    return dc;  
  }
  /**
	 * ����������������ֵ��ѯ����.
	 *
	 * @return ����������Ψһ����
	 */
  public Polytene findUniqueBy(String name, Object value) throws OrmException {
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			return (Polytene) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("findUniqueByʱ����", e);
			throw new OrmException("findUniqueByʱ����", e);
		}

  }

  //����У��
  private void check(Polytene polytene) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
