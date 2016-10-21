package com.itsv.annotation.ordersGoods.dao;

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
import com.itsv.annotation.ordersGoods.vo.OrdersGoods;

/**
 * orders_goods对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class OrdersGoodsDao extends HibernatePagedDao<OrdersGoods> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrdersGoodsDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((OrdersGoods) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((OrdersGoods) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<OrdersGoods> queryByObject(OrdersGoods ordersGoods) {
		return find(buildCriteriaByVO(ordersGoods));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, OrdersGoods ordersGoods) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(ordersGoods));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(OrdersGoods ordersGoods){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (ordersGoods.getId() != null ) {
    	dc.add(Restrictions.eq("id", ordersGoods.getId()));
    }	

    //order_number 
    if (ordersGoods.getOrderNumber() != null && ordersGoods.getOrderNumber().length() > 0) {
    	dc.add(Restrictions.like("orderNumber", ordersGoods.getOrderNumber(), MatchMode.ANYWHERE));
    }	

    //goodsid 
    if (ordersGoods.getGoodsId() != null && ordersGoods.getGoodsId().length() > 0) {
    	dc.add(Restrictions.like("goodsId", ordersGoods.getGoodsId(), MatchMode.ANYWHERE));
    }	

    //goods_number 
    if (ordersGoods.getGoodsNumber() != null && ordersGoods.getGoodsNumber().length() > 0) {
    	dc.add(Restrictions.like("goodsNumber", ordersGoods.getGoodsNumber(), MatchMode.ANYWHERE));
    }	

    //goods_total 
    if (ordersGoods.getGoodsTotal() != null && ordersGoods.getGoodsTotal().length() > 0) {
    	dc.add(Restrictions.like("goodsTotal", ordersGoods.getGoodsTotal(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (ordersGoods.getRemark1() != null && ordersGoods.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", ordersGoods.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (ordersGoods.getRemark2() != null && ordersGoods.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", ordersGoods.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(OrdersGoods ordersGoods) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
