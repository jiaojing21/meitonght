package com.itsv.annotation.subGoodGoods.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.subGoodGoods.dao.SubGoodGoodsDao;
import com.itsv.annotation.subGoodGoods.vo.SubGoodGoods;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对subgood_goods的业务操作
 *
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubGoodGoodsService extends BaseService {

  //数据访问层对象
  @Autowired
  private SubGoodGoodsDao subGoodGoodsDao;

	/**
	 * 增加subgood_goods
	 */
	public void add(SubGoodGoods subGoodGoods) {
		this.subGoodGoodsDao.save(subGoodGoods);
	}

	/**
	 * 修改subgood_goods
	 */
	public void update(SubGoodGoods subGoodGoods) {
		this.subGoodGoodsDao.update(subGoodGoods);
	}

	/**
	 * 删除subgood_goods
	 */
	public void delete(Serializable id) {
		this.subGoodGoodsDao.removeById(id);
	}

	/**
	 * 根据ID查询subgood_goods的详细信息
	 */
	public SubGoodGoods queryById(Serializable subGoodGoodsid) {
		return this.subGoodGoodsDao.get(subGoodGoodsid);
	}

	/**
	 * 获取所有的subgood_goods对象
	 */
	public List<SubGoodGoods> queryAll() {
		return this.subGoodGoodsDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<SubGoodGoods> queryByVO(SubGoodGoods subGoodGoods) {
		return this.subGoodGoodsDao.queryByObject(subGoodGoods);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, SubGoodGoods subGoodGoods) {
		return this.subGoodGoodsDao.queryByObject(records, subGoodGoods);
	}

}