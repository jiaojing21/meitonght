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
 * ˵���������goodcode��ҵ�����
 *
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Service @Transactional 
public class GoodCodeService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private GoodCodeDao goodCodeDao;

	/**
	 * ����goodcode
	 */
	public void add(GoodCode goodCode) {
		this.goodCodeDao.save(goodCode);
	}

	/**
	 * �޸�goodcode
	 */
	public void update(GoodCode goodCode) {
		this.goodCodeDao.update(goodCode);
	}

	/**
	 * ɾ��goodcode
	 */
	public void delete(Serializable id) {
		this.goodCodeDao.removeById(id);
	}

	/**
	 * ����ID��ѯgoodcode����ϸ��Ϣ
	 */
	public GoodCode queryById(Serializable goodCodeid) {
		return this.goodCodeDao.get(goodCodeid);
	}

	/**
	 * ����goodID��ѯgoodcode����ϸ��Ϣ
	 */
	public List<GoodCode> queryByGoodId(String goodId) {
		GoodCode goodCode = new GoodCode();
		goodCode.setGoodId(goodId);
		List<GoodCode> list = this.queryByVO(goodCode);
		return list;
		
	}
	/**
	 * ��ȡ���е�goodcode����
	 */
	public List<GoodCode> queryAll() {
		return this.goodCodeDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<GoodCode> queryByVO(GoodCode goodCode) {
		return this.goodCodeDao.queryByObject(goodCode);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, GoodCode goodCode) {
		return this.goodCodeDao.queryByObject(records, goodCode);
	}

}