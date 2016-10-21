package com.itsv.annotation.subjectGoods.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.subjectGoods.dao.SubjectGoodsDao;
import com.itsv.annotation.subjectGoods.vo.SubjectGoods;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������subject_goods��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubjectGoodsService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SubjectGoodsDao subjectGoodsDao;

	/**
	 * ����subject_goods
	 */
	public void add(SubjectGoods subjectGoods) {
		this.subjectGoodsDao.save(subjectGoods);
	}

	/**
	 * �޸�subject_goods
	 */
	public void update(SubjectGoods subjectGoods) {
		this.subjectGoodsDao.update(subjectGoods);
	}

	/**
	 * ɾ��subject_goods
	 */
	public void delete(Serializable id) {
		this.subjectGoodsDao.removeById(id);
	}

	/**
	 * ����ID��ѯsubject_goods����ϸ��Ϣ
	 */
	public SubjectGoods queryById(Serializable subjectGoodsid) {
		return this.subjectGoodsDao.get(subjectGoodsid);
	}

	/**
	 * ��ȡ���е�subject_goods����
	 */
	public List<SubjectGoods> queryAll() {
		return this.subjectGoodsDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<SubjectGoods> queryByVO(SubjectGoods subjectGoods) {
		return this.subjectGoodsDao.queryByObject(subjectGoods);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, SubjectGoods subjectGoods) {
		return this.subjectGoodsDao.queryByObject(records, subjectGoods);
	}

}