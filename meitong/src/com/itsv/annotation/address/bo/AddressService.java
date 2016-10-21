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
 * ˵���������address��ҵ�����
 *
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
 @Service @Transactional 
public class AddressService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private AddressDao addressDao;

	/**
	 * ����address
	 */
	public void add(Address address) {
		this.addressDao.save(address);
	}

	/**
	 * �޸�address
	 */
	public void update(Address address) {
		this.addressDao.update(address);
	}

	/**
	 * ɾ��address
	 */
	public void delete(Serializable id) {
		this.addressDao.removeById(id);
	}

	/**
	 * ����ID��ѯaddress����ϸ��Ϣ
	 */
	public Address queryById(Serializable addressid) {
		return this.addressDao.get(addressid);
	}

	/**
	 * ��ȡ���е�address����
	 */
	public List<Address> queryAll() {
		return this.addressDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Address> queryByVO(Address address) {
		return this.addressDao.queryByObject(address);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Address address) {
		return this.addressDao.queryByObject(records, address);
	}

}