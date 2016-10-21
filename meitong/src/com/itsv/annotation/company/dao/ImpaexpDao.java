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
 * 进出口对象的数据访问类
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
		//进行基本校验
		check((Impaexp) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Impaexp) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Impaexp> queryByObject(Impaexp impaexp) {
		return find(buildCriteriaByVO(impaexp));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Impaexp impaexp) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
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

    //海关id 
    if (impaexp.getCompanyid() != null && impaexp.getCompanyid().length() > 0) {
    	dc.add(Restrictions.like("companyid", impaexp.getCompanyid(), MatchMode.ANYWHERE));
    }	

    //进口量 
    if (impaexp.getImportsl() != null && impaexp.getImportsl().length() > 0) {
    	dc.add(Restrictions.like("importsl", impaexp.getImportsl(), MatchMode.ANYWHERE));
    }	

    //进口额 
    if (impaexp.getImportse() != null && impaexp.getImportse().length() > 0) {
    	dc.add(Restrictions.like("importse", impaexp.getImportse(), MatchMode.ANYWHERE));
    }	

    //出口量 
    if (impaexp.getExportsl() != null && impaexp.getExportsl().length() > 0) {
    	dc.add(Restrictions.like("exportsl", impaexp.getExportsl(), MatchMode.ANYWHERE));
    }	

    //出口额 
    if (impaexp.getExportse() != null && impaexp.getExportse().length() > 0) {
    	dc.add(Restrictions.like("exportse", impaexp.getExportse(), MatchMode.ANYWHERE));
    }	

    //类型 
    if (impaexp.getType() != null && impaexp.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", impaexp.getType()));
    }	

    //年份 
    if (impaexp.getHtime() != null && impaexp.getHtime().length() > 0) {
    	dc.add(Restrictions.eq("htime", impaexp.getHtime()));
    }	
    dc.addOrder(Order.desc("htime"));
    return dc;  
  }

  /**
 	 * 根据属性名和属性值查询对象.
 	 *
 	 * @return 符合条件的唯一对象
 	 */
   public Impaexp findUniqueBy(String name, Object value) throws OrmException {
 		try {
 			Criteria criteria = getSession().createCriteria(getEntityClass());
 			criteria.add(Restrictions.eq(name, value));
 			return (Impaexp) criteria.uniqueResult();
 		} catch (Exception e) {
 			logger.error("findUniqueBy时出错", e);
 			throw new OrmException("findUniqueBy时出错", e);
 		}

   }
  
  //数据校验
  private void check(Impaexp impaexp) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
