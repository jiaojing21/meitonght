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
 * region��������ݷ�����
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
		//���л���У��
		check((Region) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Region) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Region> queryByObject(Region region) {
		return find(buildCriteriaByVO(region));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Region region) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

  //����У��
  private void check(Region region) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
