package com.itsv.annotation.logistics.dao;

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
import com.itsv.annotation.logistics.vo.Logistics;

/**
 * logistics��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class LogisticsDao extends HibernatePagedDao<Logistics> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogisticsDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Logistics) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Logistics) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Logistics> queryByObject(Logistics logistics) {
		return find(buildCriteriaByVO(logistics));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Logistics logistics) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(logistics));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Logistics logistics){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (logistics.getId() != null ) {
    	dc.add(Restrictions.eq("id", logistics.getId()));
    }	

    //logistics_number 
    if (logistics.getLogisticsNumber() != null && logistics.getLogisticsNumber().length() > 0) {
    	dc.add(Restrictions.eq("logisticsNumber", logistics.getLogisticsNumber()));
    }	

    //order_number 
    if (logistics.getOrderNumber() != null && logistics.getOrderNumber().length() > 0) {
    	dc.add(Restrictions.eq("orderNumber", logistics.getOrderNumber()));
    }	

    //name 
    if (logistics.getName() != null && logistics.getName().length() > 0) {
    	dc.add(Restrictions.like("name", logistics.getName(), MatchMode.ANYWHERE));
    }
    
    //code 
    if (logistics.getCode() != null && logistics.getCode().length() > 0) {
    	dc.add(Restrictions.like("code", logistics.getCode(), MatchMode.ANYWHERE));
    }

    //sendtime 
    if (logistics.getSendTime() != null) {
    	dc.add(Restrictions.eq("sendTime", logistics.getSendTime()));
      }		

    //remark_1 
    if (logistics.getRemark1() != null && logistics.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", logistics.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (logistics.getRemark2() != null && logistics.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", logistics.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Logistics logistics) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
