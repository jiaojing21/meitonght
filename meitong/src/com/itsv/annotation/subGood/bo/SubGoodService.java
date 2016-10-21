package com.itsv.annotation.subGood.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.subGood.dao.SubGoodDao;
import com.itsv.annotation.subGood.vo.SubGood;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������subgood��ҵ�����
 *
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubGoodService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SubGoodDao subGoodDao;

	/**
	 * ����subgood
	 */
	public void add(SubGood subGood) {
		this.subGoodDao.save(subGood);
	}

	/**
	 * �޸�subgood
	 */
	public void update(SubGood subGood) {
		this.subGoodDao.update(subGood);
	}

	/**
	 * ɾ��subgood
	 */
	public void delete(Serializable id) {
		this.subGoodDao.removeById(id);
	}

	/**
	 * ����ID��ѯsubgood����ϸ��Ϣ
	 */
	public SubGood queryById(Serializable subGoodid) {
		return this.subGoodDao.get(subGoodid);
	}

	/**
	 * ��ȡ���е�subgood����
	 */
	public List<SubGood> queryAll() {
		return this.subGoodDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<SubGood> queryByVO(SubGood subGood) {
		return this.subGoodDao.queryByObject(subGood);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, SubGood subGood) {
		return this.subGoodDao.queryByObject(records, subGood);
	}

}