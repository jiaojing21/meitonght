package com.itsv.annotation.spec.dao;

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
import com.itsv.annotation.spec.vo.Spec;

/**
 * spec对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
 @Repository @Transactional
public class SpecDao extends HibernatePagedDao<Spec> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SpecDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Spec) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Spec) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Spec> queryByObject(Spec spec) {
		return find(buildCriteriaByVO(spec));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Spec spec) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(spec));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Spec spec){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (spec.getId() != null ) {
    	dc.add(Restrictions.eq("id", spec.getId()));
    }	

    //type
    if (spec.getType() != null && spec.getType().length() > 0) {
    	dc.add(Restrictions.like("type", spec.getType(), MatchMode.ANYWHERE));
    }	
    //specName
    if (spec.getSpecName() != null && spec.getSpecName().length() > 0) {
    	dc.add(Restrictions.like("specName", spec.getSpecName(), MatchMode.ANYWHERE));
    }	
    //brandid 
    if (spec.getRemark() != null && spec.getRemark().length() > 0) {
    	dc.add(Restrictions.like("remark", spec.getRemark(), MatchMode.ANYWHERE));
    }	
  //brandCode 
    if (spec.getBrandCode() != null && spec.getBrandCode().length() > 0) {
    	dc.add(Restrictions.in("brandCode", spec.getBcode()));
    }
    return dc;  
  }
  
  //数据校验
  private void check(Spec spec) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
