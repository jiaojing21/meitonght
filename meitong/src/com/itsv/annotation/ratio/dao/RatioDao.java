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
import com.itsv.annotation.ratio.vo.Ratio;

/**
 * �������������ݷ�����
 * 
 * 
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */
 @Repository @Transactional
public class RatioDao extends HibernatePagedDao<Ratio> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RatioDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Ratio) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Ratio) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Ratio> queryByObject(Ratio ratio) {
		return find(buildCriteriaByVO(ratio));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Ratio ratio) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(ratio));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Ratio ratio){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (ratio.getId() != null ) {
    	dc.add(Restrictions.eq("id", ratio.getId()));
    }	

    //���� 
    if (ratio.getTitle() != null && ratio.getTitle().length() > 0) {
    	dc.add(Restrictions.like("title", ratio.getTitle(), MatchMode.ANYWHERE));
    }	

    //ʱ�� 
    if (ratio.getTime() != null && ratio.getTime().length() > 0) {
    	dc.add(Restrictions.like("time", ratio.getTime(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (ratio.getType() != null && ratio.getType().length() > 0) {
    	dc.add(Restrictions.like("type", ratio.getType(), MatchMode.ANYWHERE));
    }	

    //ͼƬ��ַ 
    if (ratio.getPicurl() != null && ratio.getPicurl().length() > 0) {
    	dc.add(Restrictions.like("picurl", ratio.getPicurl(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (ratio.getContent() != null && ratio.getContent().length() > 0) {
    	dc.add(Restrictions.like("content", ratio.getContent(), MatchMode.ANYWHERE));
    }	

    //�ӷ��� 
    if (ratio.getSubtype() != null && ratio.getSubtype().length() > 0) {
    	dc.add(Restrictions.like("subtype", ratio.getSubtype(), MatchMode.ANYWHERE));
    }	
    dc.addOrder(Order.desc("time"));
    return dc;  
  }

  //����У��
  private void check(Ratio ratio) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
