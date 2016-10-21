package com.itsv.annotation.ratio.dao;

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
import org.hibernate.SessionFactory;
import com.itsv.annotation.ratio.vo.RatioSub;

/**
 * ����ͼ�ӱ��������ݷ�����
 * 
 * 
 * @author quyf
 * @since 2014-12-30
 * @version 1.0
 */
 @Repository @Transactional
public class RatioSubDao extends HibernatePagedDao<RatioSub> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RatioSubDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((RatioSub) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((RatioSub) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<RatioSub> queryByObject(RatioSub ratioSub) {
		return find(buildCriteriaByVO(ratioSub));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, RatioSub ratioSub) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(ratioSub));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(RatioSub ratioSub){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (ratioSub.getId() != null ) {
    	dc.add(Restrictions.eq("id", ratioSub.getId()));
    }	

    //ratioid 
    if (ratioSub.getRatioid() != null && ratioSub.getRatioid().length() > 0) {
    	dc.add(Restrictions.like("ratioid", ratioSub.getRatioid(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (ratioSub.getRname() != null && ratioSub.getRname().length() > 0) {
    	dc.add(Restrictions.like("rname", ratioSub.getRname(), MatchMode.ANYWHERE));
    }	

    //����1 
    if (ratioSub.getDataone() != null && ratioSub.getDataone().length() > 0) {
    	dc.add(Restrictions.like("dataone", ratioSub.getDataone(), MatchMode.ANYWHERE));
    }	

    //����2 
    if (ratioSub.getDatatwo() != null && ratioSub.getDatatwo().length() > 0) {
    	dc.add(Restrictions.like("datatwo", ratioSub.getDatatwo(), MatchMode.ANYWHERE));
    }	
    //px 
    if (ratioSub.getPx() != null) {
    	dc.add(Restrictions.eq("px", ratioSub.getPx()));
      }	
    dc.addOrder(Order.asc("px"));
    return dc;  
  }

  //����У��
  private void check(RatioSub ratioSub) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
