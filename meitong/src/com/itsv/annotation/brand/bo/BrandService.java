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
 * 说明：处理对brand的业务操作
 *
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
 @Service @Transactional 
public class BrandService extends BaseService {

  //数据访问层对象
  @Autowired
  private BrandDao brandDao;

	/**
	 * 增加brand
	 */
	public void add(Brand brand) {
		this.brandDao.save(brand);
	}

	/**
	 * 修改brand
	 */
	public void update(Brand brand) {
		this.brandDao.update(brand);
	}

	/**
	 * 删除brand
	 */
	public void delete(Serializable id) {
		this.brandDao.removeById(id);
	}

	/**
	 * 根据ID查询brand的详细信息
	 */
	public Brand queryById(Serializable brandid) {
		return this.brandDao.get(brandid);
	}

	/**
	 * 获取所有的brand对象
	 */
	public List<Brand> queryAll() {
		return this.brandDao.getAll();
	}
	
	/**
	 * 通过brandcode查询品牌信息
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
	 * 根据类别获取所有的brand对象
	 */
	public List<Brand> queryByTypeCode(String type){
		Brand brand = new Brand();
		brand.setType(type);
		List<Brand> list =  this.queryByVO(brand);
		return list;
		
	}
	
	/**
	 * 组合条件查询
	 */
	public List<Brand> queryByVO(Brand brand) {
		return this.brandDao.queryByObject(brand);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Brand brand) {
		return this.brandDao.queryByObject(records, brand);
	}
 }