package com.itsv.annotation.region.dao;

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
import com.itsv.annotation.region.vo.Region;

/**
 * region对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
 @Repository @Transactional
public class RegionDao extends HibernatePagedDao<Region> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegionDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Region) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Region) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Region> queryByObject(Region region) {
		return find(buildCriteriaByVO(region));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Region region) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(region));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Region region){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (region.getId() != null ) {
    	dc.add(Restrictions.eq("id", region.getId()));
    }	

    //region_code 
    if (region.getRegionCode() != null && region.getRegionCode().length() > 0) {
    	dc.add(Restrictions.like("regionCode", region.getRegionCode(), MatchMode.ANYWHERE));
    }	

    //region_name 
    if (region.getRegionName() != null && region.getRegionName().length() > 0) {
    	dc.add(Restrictions.like("regionName", region.getRegionName(), MatchMode.ANYWHERE));
    }	

    //parent_id 
    if (region.getParentId() != null) {
    	dc.add(Restrictions.eq("parentId", region.getParentId()));
      }		

    //region_level 
    if (region.getRegionLevel() != null) {
    	dc.add(Restrictions.eq("regionLevel", region.getRegionLevel()));
      }		

    //region_order 
    if (region.getRegionOrder() != null) {
    	dc.add(Restrictions.eq("regionOrder", region.getRegionOrder()));
      }		

    //region_name_en 
    if (region.getRegionNameEn() != null && region.getRegionNameEn().length() > 0) {
    	dc.add(Restrictions.like("regionNameEn", region.getRegionNameEn(), MatchMode.ANYWHERE));
    }	

    //region_shortname_en 
    if (region.getRegionShortnameEn() != null && region.getRegionShortnameEn().length() > 0) {
    	dc.add(Restrictions.like("regionShortnameEn", region.getRegionShortnameEn(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Region region) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
