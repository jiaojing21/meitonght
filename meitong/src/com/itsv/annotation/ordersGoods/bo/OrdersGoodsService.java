package com.itsv.annotation.ordersGoods.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.ordersGoods.dao.OrdersGoodsDao;
import com.itsv.annotation.ordersGoods.vo.OrdersGoods;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������orders_goods��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class OrdersGoodsService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private OrdersGoodsDao ordersGoodsDao;

	/**
	 * ����orders_goods
	 */
	public void add(OrdersGoods ordersGoods) {
		this.ordersGoodsDao.save(ordersGoods);
	}

	/**
	 * �޸�orders_goods
	 */
	public void update(OrdersGoods ordersGoods) {
		this.ordersGoodsDao.update(ordersGoods);
	}

	/**
	 * ɾ��orders_goods
	 */
	public void delete(Serializable id) {
		this.ordersGoodsDao.removeById(id);
	}

	/**
	 * ����ID��ѯorders_goods����ϸ��Ϣ
	 */
	public OrdersGoods queryById(Serializable ordersGoodsid) {
		return this.ordersGoodsDao.get(ordersGoodsid);
	}
	/**
	 * ��ȡ���е�orders_goods����
	 */
	public List<OrdersGoods> queryAll() {
		return this.ordersGoodsDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<OrdersGoods> queryByVO(OrdersGoods ordersGoods) {
		return this.ordersGoodsDao.queryByObject(ordersGoods);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, OrdersGoods ordersGoods) {
		return this.ordersGoodsDao.queryByObject(records, ordersGoods);
	}

}