package com.itsv.annotation.refund.dao;

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
import com.itsv.annotation.refund.vo.Refund;

/**
 * refund对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class RefundDao extends HibernatePagedDao<Refund> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RefundDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Refund) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Refund) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Refund> queryByObject(Refund refund) {
		return find(buildCriteriaByVO(refund));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Refund refund) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(refund));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Refund refund){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (refund.getId() != null ) {
    	dc.add(Restrictions.eq("id", refund.getId()));
    }	

    //order_number 
    if (refund.getOrderNumber() != null && refund.getOrderNumber().length() > 0) {
    	dc.add(Restrictions.like("orderNumber", refund.getOrderNumber(), MatchMode.ANYWHERE));
    }	

    //refund_number 
    if (refund.getRefundNumber() != null && refund.getRefundNumber().length() > 0) {
    	dc.add(Restrictions.like("refundNumber", refund.getRefundNumber(), MatchMode.ANYWHERE));
    }	

    //proposer 
    if (refund.getProposer() != null && refund.getProposer().length() > 0) {
    	dc.add(Restrictions.like("proposer", refund.getProposer(), MatchMode.ANYWHERE));
    }	

    //proposer_phone 
    if (refund.getProposerPhone() != null && refund.getProposerPhone().length() > 0) {
    	dc.add(Restrictions.like("proposerPhone", refund.getProposerPhone(), MatchMode.ANYWHERE));
    }	

    //logistics_number 
    if (refund.getLogisticsNumber() != null && refund.getLogisticsNumber().length() > 0) {
    	dc.add(Restrictions.like("logisticsNumber", refund.getLogisticsNumber(), MatchMode.ANYWHERE));
    }	

    //reason 
    if (refund.getReason() != null && refund.getReason().length() > 0) {
    	dc.add(Restrictions.like("reason", refund.getReason(), MatchMode.ANYWHERE));
    }	

    //refundtime 
    if (refund.getRefundTime() != null) {
    	dc.add(Restrictions.eq("refundTime", refund.getRefundTime()));
      }		

    //flag 
    if (refund.getFlag() != null && refund.getFlag().length() > 0) {
    	dc.add(Restrictions.like("flag", refund.getFlag(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (refund.getRemark1() != null && refund.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", refund.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (refund.getRemark2() != null && refund.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", refund.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Refund refund) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
