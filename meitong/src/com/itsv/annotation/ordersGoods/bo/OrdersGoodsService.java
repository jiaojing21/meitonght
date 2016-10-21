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
 * 说明：处理对orders_goods的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class OrdersGoodsService extends BaseService {

  //数据访问层对象
  @Autowired
  private OrdersGoodsDao ordersGoodsDao;

	/**
	 * 增加orders_goods
	 */
	public void add(OrdersGoods ordersGoods) {
		this.ordersGoodsDao.save(ordersGoods);
	}

	/**
	 * 修改orders_goods
	 */
	public void update(OrdersGoods ordersGoods) {
		this.ordersGoodsDao.update(ordersGoods);
	}

	/**
	 * 删除orders_goods
	 */
	public void delete(Serializable id) {
		this.ordersGoodsDao.removeById(id);
	}

	/**
	 * 根据ID查询orders_goods的详细信息
	 */
	public OrdersGoods queryById(Serializable ordersGoodsid) {
		return this.ordersGoodsDao.get(ordersGoodsid);
	}
	/**
	 * 获取所有的orders_goods对象
	 */
	public List<OrdersGoods> queryAll() {
		return this.ordersGoodsDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<OrdersGoods> queryByVO(OrdersGoods ordersGoods) {
		return this.ordersGoodsDao.queryByObject(ordersGoods);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, OrdersGoods ordersGoods) {
		return this.ordersGoodsDao.queryByObject(records, ordersGoods);
	}

}