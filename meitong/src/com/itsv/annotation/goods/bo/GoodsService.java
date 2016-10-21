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
 * ˵���������goods��ҵ�����
 *
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
 @Service @Transactional 
public class GoodsService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private GoodsDao goodsDao;
  
//���ݷ��ʲ����
  @Autowired
  private SpecDao spenDao;
//���ݷ��ʲ����
  @Autowired
  private GoodCodeDao goodCodeDao;

	/**
	 * ����goods
	 */
	public void add(Goods goods) {
		this.goodsDao.save(goods);
	}

	/**
	 * �޸�goods
	 */
	public void update(Goods goods) {
		this.goodsDao.update(goods);
	}

	/**
	 * ɾ��goods
	 */
	public void delete(Serializable id) {
		this.goodsDao.removeById(id);
	}
	/**
	 * �������Ʋ�ѯgoods����Ϣ
	 */
	public List<Goods> queryByType(String specid){
		return this.goodsDao.queryByType(specid);
	}
	/**
	 * �������Ͳ�ѯgoods����ϸ��Ϣ
	 */
	public List<Goods> queryBySpecName(String specName){
		return this.goodsDao.queryBySpecName(specName);
	}
	/**
	 * ����ID��ѯgoods����ϸ��Ϣ
	 */
	public Goods queryById(Serializable goodsid) {
		return this.goodsDao.get(goodsid);
	}
	/**
	 * ����specid�����ѯprice��С
	 * **/
	public Goods querySubjectByPrice(String specid){
		return this.goodsDao.querySubjectByPrice(specid);
	}
	/**
	 * ��ȡ���е�goods����
	 */
	public List<Goods> queryAll() {
		return this.goodsDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Goods> queryByVO(Goods goods) {
		return this.goodsDao.queryByObject(goods);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Goods goods) {
		return this.goodsDao.queryByObject(records, goods);
	}
	
	/**
	 * ɾ����Ʒ
	 */
	public void delgoods(String id){
		Goods goods = this.goodsDao.get(id);
		Spec spec = this.spenDao.get(goods.getSpecId());
		if(null!=spec){
			this.spenDao.remove(spec); //ɾ����Ʒ
		}
		
		this.goodsDao.removeById(id);//ɾ����Ʒ����
		GoodCode goodCode= new GoodCode();
		goodCode.setGoodId(id);
		List<GoodCode> list = this.goodCodeDao.queryByObject(goodCode);
		if(null!=list&&list.size()>0){
			for(GoodCode gc : list){
				this.goodCodeDao.remove(gc);//ɾ���м������
			}
			
		}
	}
	/**
	 * �ϼ���Ʒ
	 */
	public void theshelves(String id){
		Goods goods = this.goodsDao.get(id);
		goods.setRemark1("1");
		this.goodsDao.update(goods);
	}
	/**
	 * �¼���Ʒ
	 */
	public void theshelf(String id){
		Goods goods = this.goodsDao.get(id);
		goods.setRemark1("2");
		this.goodsDao.update(goods);
	}
	/**
	 * ����specid��ѯ�������ֵ
	 */
	public String querySumBySpecid(String specid) {
		//return this.specDao.queryByObject(records, spec);
		return this.goodsDao.querySumBySpecid(specid);
	}
}