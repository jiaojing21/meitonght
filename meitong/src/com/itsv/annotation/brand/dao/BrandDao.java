package com.itsv.annotation.brand.dao;

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
import com.itsv.annotation.brand.vo.Brand;

/**
 * brand对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
 @Repository @Transactional
public class BrandDao extends HibernatePagedDao<Brand> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrandDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Brand) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Brand) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Brand> queryByObject(Brand brand) {
		return find(buildCriteriaByVO(brand));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Brand brand) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(brand));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Brand brand){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (brand.getId() != null ) {
    	dc.add(Restrictions.eq("id", brand.getId()));
    }	

    //name 
    if (brand.getName() != null && brand.getName().length() > 0) {
    	dc.add(Restrictions.like("name", brand.getName(), MatchMode.ANYWHERE));
    }
    
  //type
    if (brand.getType() != null && brand.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", brand.getType()));
    }	

    //cradle 
    if (brand.getCradle() != null && brand.getCradle().length() > 0) {
    	dc.add(Restrictions.like("cradle", brand.getCradle(), MatchMode.ANYWHERE));
    }	

    //introduce 
    if (brand.getIntroduce() != null && brand.getIntroduce().length() > 0) {
    	dc.add(Restrictions.like("introduce", brand.getIntroduce(), MatchMode.ANYWHERE));
    }	

    //pictureurl 
    if (brand.getPictureurl() != null && brand.getPictureurl().length() > 0) {
    	dc.add(Restrictions.like("pictureurl", brand.getPictureurl(), MatchMode.ANYWHERE));
    }	

    //brandcode 
    if (brand.getBrandcode() != null && brand.getBrandcode().length() > 0) {
    	dc.add(Restrictions.eq("brandcode", brand.getBrandcode()));
    }	

    //flag 
    if (brand.getFlag() != null && brand.getFlag().length() > 0) {
    	dc.add(Restrictions.eq("flag", brand.getFlag()));
    }	

    return dc;  
  }

  //数据校验
  private void check(Brand brand) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
