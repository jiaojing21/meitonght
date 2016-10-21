package com.itsv.annotation.company.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import com.itsv.annotation.company.vo.Impaexp;
import com.itsv.annotation.company.vo.Polytene;

/**
 * �����ڶ�������ݷ�����
 * 
 * 
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */
 @Repository @Transactional
public class ImpaexpDao extends HibernatePagedDao<Impaexp> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImpaexpDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Impaexp) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Impaexp) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Impaexp> queryByObject(Impaexp impaexp) {
		return find(buildCriteriaByVO(impaexp));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Impaexp impaexp) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(impaexp));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Impaexp impaexp){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (impaexp.getId() != null ) {
    	dc.add(Restrictions.eq("id", impaexp.getId()));
    }	

    //����id 
    if (impaexp.getCompanyid() != null && impaexp.getCompanyid().length() > 0) {
    	dc.add(Restrictions.like("companyid", impaexp.getCompanyid(), MatchMode.ANYWHERE));
    }	

    //������ 
    if (impaexp.getImportsl() != null && impaexp.getImportsl().length() > 0) {
    	dc.add(Restrictions.like("importsl", impaexp.getImportsl(), MatchMode.ANYWHERE));
    }	

    //���ڶ� 
    if (impaexp.getImportse() != null && impaexp.getImportse().length() > 0) {
    	dc.add(Restrictions.like("importse", impaexp.getImportse(), MatchMode.ANYWHERE));
    }	

    //������ 
    if (impaexp.getExportsl() != null && impaexp.getExportsl().length() > 0) {
    	dc.add(Restrictions.like("exportsl", impaexp.getExportsl(), MatchMode.ANYWHERE));
    }	

    //���ڶ� 
    if (impaexp.getExportse() != null && impaexp.getExportse().length() > 0) {
    	dc.add(Restrictions.like("exportse", impaexp.getExportse(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (impaexp.getType() != null && impaexp.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", impaexp.getType()));
    }	

    //��� 
    if (impaexp.getHtime() != null && impaexp.getHtime().length() > 0) {
    	dc.add(Restrictions.eq("htime", impaexp.getHtime()));
    }	
    dc.addOrder(Order.desc("htime"));
    return dc;  
  }

  /**
 	 * ����������������ֵ��ѯ����.
 	 *
 	 * @return ����������Ψһ����
 	 */
   public Impaexp findUniqueBy(String name, Object value) throws OrmException {
 		try {
 			Criteria criteria = getSession().createCriteria(getEntityClass());
 			criteria.add(Restrictions.eq(name, value));
 			return (Impaexp) criteria.uniqueResult();
 		} catch (Exception e) {
 			logger.error("findUniqueByʱ����", e);
 			throw new OrmException("findUniqueByʱ����", e);
 		}

   }
  
  //����У��
  private void check(Impaexp impaexp) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
