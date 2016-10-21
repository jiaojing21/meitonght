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
import org.hibernate.SessionFactory;
import com.itsv.annotation.company.vo.Company;

/**
 * ��ҵ��������ݷ�����
 * 
 * 
 * @author quyf
 * @since 2014-10-23
 * @version 1.0
 */
 @Repository @Transactional
public class CompanyDao extends HibernatePagedDao<Company> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CompanyDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Company) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Company) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Company> queryByObject(Company company) {
		return find(buildCriteriaByVO(company));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Company company) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(company));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Company company){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (company.getId() != null ) {
    	dc.add(Restrictions.eq("id", company.getId()));
    }	

    //��ҵ�� 
    if (company.getCompanyname() != null && company.getCompanyname().length() > 0) {
    	dc.add(Restrictions.like("companyname", company.getCompanyname(), MatchMode.ANYWHERE));
    }	

    //γ�� 
    if (company.getCompanyx() != null && company.getCompanyx().length() > 0) {
    	dc.add(Restrictions.like("companyx", company.getCompanyx(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (company.getCompanyy() != null && company.getCompanyy().length() > 0) {
    	dc.add(Restrictions.like("companyy", company.getCompanyy(), MatchMode.ANYWHERE));
    }
    //���� 1��ҵ ��2����
    if (company.getType() != null && company.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", company.getType()));
    }
    if (company.getCode() != null && company.getCode().length() > 0) {
    	dc.add(Restrictions.eq("code", company.getCode()));
    }

    return dc;  
  }

  //����У��
  private void check(Company company) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
