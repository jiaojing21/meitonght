package com.itsv.annotation.goodCode.dao;

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
import com.itsv.annotation.goodCode.vo.GoodCode;

/**
 * goodcode对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Repository @Transactional
public class GoodCodeDao extends HibernatePagedDao<GoodCode> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GoodCodeDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((GoodCode) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((GoodCode) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<GoodCode> queryByObject(GoodCode goodCode) {
		return find(buildCriteriaByVO(goodCode));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, GoodCode goodCode) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(goodCode));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(GoodCode goodCode){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (goodCode.getId() != null ) {
    	dc.add(Restrictions.eq("id", goodCode.getId()));
    }	

    //goodid 
    if (goodCode.getGoodId() != null && goodCode.getGoodId().length() > 0) {
    	dc.add(Restrictions.like("goodId", goodCode.getGoodId(), MatchMode.ANYWHERE));
    }	

    //code 
    if (goodCode.getCode() != null && goodCode.getCode().length() > 0) {
    	dc.add(Restrictions.like("code", goodCode.getCode(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(GoodCode goodCode) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
