package com.itsv.annotation.singleProductPic.dao;

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
import com.itsv.annotation.singleProductPic.vo.SingleProductPic;

/**
 * single_product_pic对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-04-11
 * @version 1.0
 */
 @Repository @Transactional
public class SingleProductPicDao extends HibernatePagedDao<SingleProductPic> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SingleProductPicDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((SingleProductPic) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((SingleProductPic) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<SingleProductPic> queryByObject(SingleProductPic singleProductPic) {
		return find(buildCriteriaByVO(singleProductPic));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, SingleProductPic singleProductPic) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(singleProductPic));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(SingleProductPic singleProductPic){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (singleProductPic.getId() != null ) {
    	dc.add(Restrictions.eq("id", singleProductPic.getId()));
    }	

    //createtime 
    if (singleProductPic.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", singleProductPic.getCreatetime()));
      }		

    return dc;  
  }

  //数据校验
  private void check(SingleProductPic singleProductPic) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
