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
 * 聚乙烯产能表对象的数据访问类
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
		//进行基本校验
		check((Polytene) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Polytene) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Polytene> queryByObject(Polytene polytene) {
		return find(buildCriteriaByVO(polytene));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Polytene polytene) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
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

    //企业id 
    if (polytene.getCompanyid() != null && polytene.getCompanyid().length() > 0) {
    	dc.add(Restrictions.like("companyid", polytene.getCompanyid(), MatchMode.ANYWHERE));
    }	

    //产能 
    if (polytene.getCapacity() != null && polytene.getCapacity().length() > 0) {
    	dc.add(Restrictions.like("capacity", polytene.getCapacity(), MatchMode.ANYWHERE));
    }	

    //产量 
    if (polytene.getProduction() != null && polytene.getProduction().length() > 0) {
    	dc.add(Restrictions.like("production", polytene.getProduction(), MatchMode.ANYWHERE));
    }	

    //类型 
    if (polytene.getType() != null && polytene.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", polytene.getType()));
    }	

    //时间 
    if (polytene.getPtime() != null && polytene.getPtime().length() > 0) {
    	dc.add(Restrictions.eq("ptime", polytene.getPtime()));
    }	

    return dc;  
  }
  /**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的唯一对象
	 */
  public Polytene findUniqueBy(String name, Object value) throws OrmException {
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			return (Polytene) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("findUniqueBy时出错", e);
			throw new OrmException("findUniqueBy时出错", e);
		}

  }

  //数据校验
  private void check(Polytene polytene) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
