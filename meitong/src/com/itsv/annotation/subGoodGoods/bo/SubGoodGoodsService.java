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
 * ˵���������subgood_goods��ҵ�����
 *
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubGoodGoodsService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SubGoodGoodsDao subGoodGoodsDao;

	/**
	 * ����subgood_goods
	 */
	public void add(SubGoodGoods subGoodGoods) {
		this.subGoodGoodsDao.save(subGoodGoods);
	}

	/**
	 * �޸�subgood_goods
	 */
	public void update(SubGoodGoods subGoodGoods) {
		this.subGoodGoodsDao.update(subGoodGoods);
	}

	/**
	 * ɾ��subgood_goods
	 */
	public void delete(Serializable id) {
		this.subGoodGoodsDao.removeById(id);
	}

	/**
	 * ����ID��ѯsubgood_goods����ϸ��Ϣ
	 */
	public SubGoodGoods queryById(Serializable subGoodGoodsid) {
		return this.subGoodGoodsDao.get(subGoodGoodsid);
	}

	/**
	 * ��ȡ���е�subgood_goods����
	 */
	public List<SubGoodGoods> queryAll() {
		return this.subGoodGoodsDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<SubGoodGoods> queryByVO(SubGoodGoods subGoodGoods) {
		return this.subGoodGoodsDao.queryByObject(subGoodGoods);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, SubGoodGoods subGoodGoods) {
		return this.subGoodGoodsDao.queryByObject(records, subGoodGoods);
	}

}