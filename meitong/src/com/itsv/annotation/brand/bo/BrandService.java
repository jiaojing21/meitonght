package com.itsv.annotation.brand.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.brand.dao.BrandDao;
import com.itsv.annotation.brand.vo.Brand;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������brand��ҵ�����
 *
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
 @Service @Transactional 
public class BrandService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private BrandDao brandDao;

	/**
	 * ����brand
	 */
	public void add(Brand brand) {
		this.brandDao.save(brand);
	}

	/**
	 * �޸�brand
	 */
	public void update(Brand brand) {
		this.brandDao.update(brand);
	}

	/**
	 * ɾ��brand
	 */
	public void delete(Serializable id) {
		this.brandDao.removeById(id);
	}

	/**
	 * ����ID��ѯbrand����ϸ��Ϣ
	 */
	public Brand queryById(Serializable brandid) {
		return this.brandDao.get(brandid);
	}

	/**
	 * ��ȡ���е�brand����
	 */
	public List<Brand> queryAll() {
		return this.brandDao.getAll();
	}
	
	/**
	 * ͨ��brandcode��ѯƷ����Ϣ
	 */
	public Brand queryByCode(String brandcode){
		Brand brand = new Brand();
		brand.setBrandcode(brandcode);
		List<Brand> list =  this.queryByVO(brand);
		if(list.size()>0){
			brand=list.get(0);
		}
		return brand;
		
	}
	
	/**
	 * ��������ȡ���е�brand����
	 */
	public List<Brand> queryByTypeCode(String type){
		Brand brand = new Brand();
		brand.setType(type);
		List<Brand> list =  this.queryByVO(brand);
		return list;
		
	}
	
	/**
	 * ���������ѯ
	 */
	public List<Brand> queryByVO(Brand brand) {
		return this.brandDao.queryByObject(brand);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Brand brand) {
		return this.brandDao.queryByObject(records, brand);
	}
 }