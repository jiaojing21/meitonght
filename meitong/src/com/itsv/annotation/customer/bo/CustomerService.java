package com.itsv.annotation.customer.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.customer.dao.CustomerDao;
import com.itsv.annotation.customer.vo.Customer;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对customer的业务操作
 *
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Service @Transactional 
public class CustomerService extends BaseService {

  //数据访问层对象
  @Autowired
  private CustomerDao customerDao;

	/**
	 * 增加customer
	 */
	public void add(Customer customer) {
		this.customerDao.save(customer);
	}

	/**
	 * 修改customer
	 */
	public void update(Customer customer) {
		this.customerDao.update(customer);
	}

	/**
	 * 删除customer
	 */
	public void delete(Serializable id) {
		this.customerDao.removeById(id);
	}

	/**
	 * 根据ID查询customer的详细信息
	 */
	public Customer queryById(Serializable customerid) {
		return this.customerDao.get(customerid);
	}

	/**
	 * 获取所有的customer对象
	 */
	public List<Customer> queryAll() {
		return this.customerDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Customer> queryByVO(Customer customer) {
		return this.customerDao.queryByObject(customer);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Customer customer) {
		return this.customerDao.queryByObject(records, customer);
	}

}