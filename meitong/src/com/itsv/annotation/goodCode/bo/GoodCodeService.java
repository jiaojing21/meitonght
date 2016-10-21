package com.itsv.annotation.goodCode.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.brand.vo.Brand;
import com.itsv.annotation.goodCode.dao.GoodCodeDao;
import com.itsv.annotation.goodCode.vo.GoodCode;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对goodcode的业务操作
 *
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Service @Transactional 
public class GoodCodeService extends BaseService {

  //数据访问层对象
  @Autowired
  private GoodCodeDao goodCodeDao;

	/**
	 * 增加goodcode
	 */
	public void add(GoodCode goodCode) {
		this.goodCodeDao.save(goodCode);
	}

	/**
	 * 修改goodcode
	 */
	public void update(GoodCode goodCode) {
		this.goodCodeDao.update(goodCode);
	}

	/**
	 * 删除goodcode
	 */
	public void delete(Serializable id) {
		this.goodCodeDao.removeById(id);
	}

	/**
	 * 根据ID查询goodcode的详细信息
	 */
	public GoodCode queryById(Serializable goodCodeid) {
		return this.goodCodeDao.get(goodCodeid);
	}

	/**
	 * 根据goodID查询goodcode的详细信息
	 */
	public List<GoodCode> queryByGoodId(String goodId) {
		GoodCode goodCode = new GoodCode();
		goodCode.setGoodId(goodId);
		List<GoodCode> list = this.queryByVO(goodCode);
		return list;
		
	}
	/**
	 * 获取所有的goodcode对象
	 */
	public List<GoodCode> queryAll() {
		return this.goodCodeDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<GoodCode> queryByVO(GoodCode goodCode) {
		return this.goodCodeDao.queryByObject(goodCode);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, GoodCode goodCode) {
		return this.goodCodeDao.queryByObject(records, goodCode);
	}

}