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
 * ˵���������customer��ҵ�����
 *
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Service @Transactional 
public class CustomerService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private CustomerDao customerDao;

	/**
	 * ����customer
	 */
	public void add(Customer customer) {
		this.customerDao.save(customer);
	}

	/**
	 * �޸�customer
	 */
	public void update(Customer customer) {
		this.customerDao.update(customer);
	}

	/**
	 * ɾ��customer
	 */
	public void delete(Serializable id) {
		this.customerDao.removeById(id);
	}

	/**
	 * ����ID��ѯcustomer����ϸ��Ϣ
	 */
	public Customer queryById(Serializable customerid) {
		return this.customerDao.get(customerid);
	}

	/**
	 * ��ȡ���е�customer����
	 */
	public List<Customer> queryAll() {
		return this.customerDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Customer> queryByVO(Customer customer) {
		return this.customerDao.queryByObject(customer);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Customer customer) {
		return this.customerDao.queryByObject(records, customer);
	}

}