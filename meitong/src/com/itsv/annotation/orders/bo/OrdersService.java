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
 * ˵���������orders��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class OrdersService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private OrdersDao ordersDao;

	/**
	 * ����orders
	 */
	public void add(Orders orders) {
		this.ordersDao.save(orders);
	}

	/**
	 * �޸�orders
	 */
	public void update(Orders orders) {
		this.ordersDao.update(orders);
	}

	/**
	 * ɾ��orders
	 */
	public void delete(Serializable id) {
		this.ordersDao.removeById(id);
	}

	/**
	 * ����ID��ѯorders����ϸ��Ϣ
	 */
	public Orders queryById(Serializable ordersid) {
		return this.ordersDao.get(ordersid);
	}
	/**
	 * ����ddh��ѯorders����ϸ��Ϣ
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
	 * ��ȡ���е�orders����
	 */
	public List<Orders> queryAll() {
		return this.ordersDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Orders> queryByVO(Orders orders) {
		return this.ordersDao.queryByObject(orders);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Orders orders) {
		return this.ordersDao.queryByObject(records, orders);
	}

}