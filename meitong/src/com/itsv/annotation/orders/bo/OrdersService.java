package com.itsv.annotation.orders.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.logistics.vo.Logistics;
import com.itsv.annotation.orders.dao.OrdersDao;
import com.itsv.annotation.orders.vo.Orders;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对orders的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class OrdersService extends BaseService {

  //数据访问层对象
  @Autowired
  private OrdersDao ordersDao;

	/**
	 * 增加orders
	 */
	public void add(Orders orders) {
		this.ordersDao.save(orders);
	}

	/**
	 * 修改orders
	 */
	public void update(Orders orders) {
		this.ordersDao.update(orders);
	}

	/**
	 * 删除orders
	 */
	public void delete(Serializable id) {
		this.ordersDao.removeById(id);
	}

	/**
	 * 根据ID查询orders的详细信息
	 */
	public Orders queryById(Serializable ordersid) {
		return this.ordersDao.get(ordersid);
	}
	/**
	 * 根据ddh查询orders的详细信息
	 */
	public Orders queryByDdh(String ddh) {
		Orders orders = new Orders();
		orders.setOrderNumber(ddh);
		List<Orders> list =  this.queryByVO(orders);
		if(list.size()>0){
			orders=list.get(0);
		}
		return orders;
	}
	
	/**
	 * 获取所有的orders对象
	 */
	public List<Orders> queryAll() {
		return this.ordersDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Orders> queryByVO(Orders orders) {
		return this.ordersDao.queryByObject(orders);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Orders orders) {
		return this.ordersDao.queryByObject(records, orders);
	}

}