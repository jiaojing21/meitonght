package com.itsv.annotation.pictext.dao;

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
import com.itsv.annotation.pictext.vo.Pictext;

/**
 * pictext对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */
 @Repository @Transactional
public class PictextDao extends HibernatePagedDao<Pictext> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PictextDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Pictext) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Pictext) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Pictext> queryByObject(Pictext pictext) {
		return find(buildCriteriaByVO(pictext));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Pictext pictext) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(pictext));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Pictext pictext){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (pictext.getId() != null ) {
    	dc.add(Restrictions.eq("id", pictext.getId()));
    }	

    //createtime 
    if (pictext.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", pictext.getCreatetime()));
      }		

    //userid 
    if (pictext.getUserId() != null && pictext.getUserId().length() > 0) {
    	dc.add(Restrictions.like("userId", pictext.getUserId(), MatchMode.ANYWHERE));
    }	

    //coment 
    if (pictext.getComent() != null && pictext.getComent().length() > 0) {
    	dc.add(Restrictions.like("coment", pictext.getComent(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Pictext pictext) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
