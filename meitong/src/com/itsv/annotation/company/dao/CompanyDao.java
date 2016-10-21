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
 * 企业对象的数据访问类
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
		//进行基本校验
		check((Company) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Company) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Company> queryByObject(Company company) {
		return find(buildCriteriaByVO(company));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Company company) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
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

    //企业名 
    if (company.getCompanyname() != null && company.getCompanyname().length() > 0) {
    	dc.add(Restrictions.like("companyname", company.getCompanyname(), MatchMode.ANYWHERE));
    }	

    //纬度 
    if (company.getCompanyx() != null && company.getCompanyx().length() > 0) {
    	dc.add(Restrictions.like("companyx", company.getCompanyx(), MatchMode.ANYWHERE));
    }	

    //经度 
    if (company.getCompanyy() != null && company.getCompanyy().length() > 0) {
    	dc.add(Restrictions.like("companyy", company.getCompanyy(), MatchMode.ANYWHERE));
    }
    //类型 1企业 ，2海关
    if (company.getType() != null && company.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", company.getType()));
    }
    if (company.getCode() != null && company.getCode().length() > 0) {
    	dc.add(Restrictions.eq("code", company.getCode()));
    }

    return dc;  
  }

  //数据校验
  private void check(Company company) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
