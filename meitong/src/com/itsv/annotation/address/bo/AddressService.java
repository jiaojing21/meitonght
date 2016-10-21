package com.itsv.annotation.address.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.address.dao.AddressDao;
import com.itsv.annotation.address.vo.Address;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对address的业务操作
 *
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
 @Service @Transactional 
public class AddressService extends BaseService {

  //数据访问层对象
  @Autowired
  private AddressDao addressDao;

	/**
	 * 增加address
	 */
	public void add(Address address) {
		this.addressDao.save(address);
	}

	/**
	 * 修改address
	 */
	public void update(Address address) {
		this.addressDao.update(address);
	}

	/**
	 * 删除address
	 */
	public void delete(Serializable id) {
		this.addressDao.removeById(id);
	}

	/**
	 * 根据ID查询address的详细信息
	 */
	public Address queryById(Serializable addressid) {
		return this.addressDao.get(addressid);
	}

	/**
	 * 获取所有的address对象
	 */
	public List<Address> queryAll() {
		return this.addressDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Address> queryByVO(Address address) {
		return this.addressDao.queryByObject(address);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Address address) {
		return this.addressDao.queryByObject(records, address);
	}

}