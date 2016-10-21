package com.itsv.annotation.goods.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.goodCode.dao.GoodCodeDao;
import com.itsv.annotation.goodCode.vo.GoodCode;
import com.itsv.annotation.goods.dao.GoodsDao;
import com.itsv.annotation.goods.vo.Goods;
import com.itsv.annotation.spec.dao.SpecDao;
import com.itsv.annotation.spec.vo.Spec;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对goods的业务操作
 *
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
 @Service @Transactional 
public class GoodsService extends BaseService {

  //数据访问层对象
  @Autowired
  private GoodsDao goodsDao;
  
//数据访问层对象
  @Autowired
  private SpecDao spenDao;
//数据访问层对象
  @Autowired
  private GoodCodeDao goodCodeDao;

	/**
	 * 增加goods
	 */
	public void add(Goods goods) {
		this.goodsDao.save(goods);
	}

	/**
	 * 修改goods
	 */
	public void update(Goods goods) {
		this.goodsDao.update(goods);
	}

	/**
	 * 删除goods
	 */
	public void delete(Serializable id) {
		this.goodsDao.removeById(id);
	}
	/**
	 * 根据名称查询goods的信息
	 */
	public List<Goods> queryByType(String specid){
		return this.goodsDao.queryByType(specid);
	}
	/**
	 * 根据类型查询goods的详细信息
	 */
	public List<Goods> queryBySpecName(String specName){
		return this.goodsDao.queryBySpecName(specName);
	}
	/**
	 * 根据ID查询goods的详细信息
	 */
	public Goods queryById(Serializable goodsid) {
		return this.goodsDao.get(goodsid);
	}
	/**
	 * 根据specid分组查询price最小
	 * **/
	public Goods querySubjectByPrice(String specid){
		return this.goodsDao.querySubjectByPrice(specid);
	}
	/**
	 * 获取所有的goods对象
	 */
	public List<Goods> queryAll() {
		return this.goodsDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Goods> queryByVO(Goods goods) {
		return this.goodsDao.queryByObject(goods);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Goods goods) {
		return this.goodsDao.queryByObject(records, goods);
	}
	
	/**
	 * 删除商品
	 */
	public void delgoods(String id){
		Goods goods = this.goodsDao.get(id);
		Spec spec = this.spenDao.get(goods.getSpecId());
		if(null!=spec){
			this.spenDao.remove(spec); //删除商品
		}
		
		this.goodsDao.removeById(id);//删除商品详情
		GoodCode goodCode= new GoodCode();
		goodCode.setGoodId(id);
		List<GoodCode> list = this.goodCodeDao.queryByObject(goodCode);
		if(null!=list&&list.size()>0){
			for(GoodCode gc : list){
				this.goodCodeDao.remove(gc);//删除中间表数据
			}
			
		}
	}
	/**
	 * 上架商品
	 */
	public void theshelves(String id){
		Goods goods = this.goodsDao.get(id);
		goods.setRemark1("1");
		this.goodsDao.update(goods);
	}
	/**
	 * 下架商品
	 */
	public void theshelf(String id){
		Goods goods = this.goodsDao.get(id);
		goods.setRemark1("2");
		this.goodsDao.update(goods);
	}
	/**
	 * 根据specid查询该组的总值
	 */
	public String querySumBySpecid(String specid) {
		//return this.specDao.queryByObject(records, spec);
		return this.goodsDao.querySumBySpecid(specid);
	}
}