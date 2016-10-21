package com.itsv.annotation.orders.dao;

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
import com.itsv.annotation.orders.vo.Orders;

/**
 * orders对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-04-14
 * @version 1.0
 */
 @Repository @Transactional
public class OrdersDao extends HibernatePagedDao<Orders> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrdersDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Orders) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Orders) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Orders> queryByObject(Orders orders) {
		return find(buildCriteriaByVO(orders));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Orders orders) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(orders));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Orders orders){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (orders.getId() != null ) {
    	dc.add(Restrictions.eq("id", orders.getId()));
    }	

    //customerid 
    if (orders.getCustomerId() != null && orders.getCustomerId().length() > 0) {
    	dc.add(Restrictions.like("customerId", orders.getCustomerId(), MatchMode.ANYWHERE));
    }	

    //order_number 
    if (orders.getOrderNumber() != null && orders.getOrderNumber().length() > 0) {
    	dc.add(Restrictions.eq("orderNumber", orders.getOrderNumber()));
    }	

    //address 
    if (orders.getAddress() != null && orders.getAddress().length() > 0) {
    	dc.add(Restrictions.like("address", orders.getAddress(), MatchMode.ANYWHERE));
    }	

    //payment 
    if (orders.getPayment() != null && orders.getPayment().length() > 0) {
    	dc.add(Restrictions.like("payment", orders.getPayment(), MatchMode.ANYWHERE));
    }	

    //payplatform 
    if (orders.getPayPlatform() != null && orders.getPayPlatform().length() > 0) {
    	dc.add(Restrictions.like("payPlatform", orders.getPayPlatform(), MatchMode.ANYWHERE));
    }	

    //createtime 
    if (orders.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", orders.getCreatetime()));
      }		

    //paytime 
    if (orders.getPayTime() != null) {
    	dc.add(Restrictions.eq("payTime", orders.getPayTime()));
      }		

    //confirmattime 
    if (orders.getConfirmatTime() != null) {
    	dc.add(Restrictions.eq("confirmatTime", orders.getConfirmatTime()));
      }		

    //status 
    if (orders.getStatus() != null && orders.getStatus().length() > 0) {
    	dc.add(Restrictions.like("status", orders.getStatus(), MatchMode.ANYWHERE));
    }	

    //flag 
    if (orders.getFlag() != null && orders.getFlag().length() > 0) {
    	dc.add(Restrictions.like("flag", orders.getFlag(), MatchMode.ANYWHERE));
    }	

    //total 
    if (orders.getTotal() != null && orders.getTotal().length() > 0) {
    	dc.add(Restrictions.like("total", orders.getTotal(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (orders.getRemark1() != null && orders.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", orders.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (orders.getRemark2() != null && orders.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", orders.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Orders orders) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
