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
 * spec��������ݷ�����
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
		//���л���У��
		check((Spec) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Spec) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Spec> queryByObject(Spec spec) {
		return find(buildCriteriaByVO(spec));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Spec spec) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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
  
  //����У��
  private void check(Spec spec) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
