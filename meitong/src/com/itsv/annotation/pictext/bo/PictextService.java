package com.itsv.annotation.pictext.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.pictext.dao.PictextDao;
import com.itsv.annotation.pictext.vo.Pictext;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������pictext��ҵ�����
 *
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */
 @Service @Transactional 
public class PictextService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private PictextDao pictextDao;

	/**
	 * ����pictext
	 */
	public void add(Pictext pictext) {
		this.pictextDao.save(pictext);
	}

	/**
	 * �޸�pictext
	 */
	public void update(Pictext pictext) {
		this.pictextDao.update(pictext);
	}

	/**
	 * ɾ��pictext
	 */
	public void delete(Serializable id) {
		this.pictextDao.removeById(id);
	}

	/**
	 * ����ID��ѯpictext����ϸ��Ϣ
	 */
	public Pictext queryById(Serializable pictextid) {
		return this.pictextDao.get(pictextid);
	}

	/**
	 * ��ȡ���е�pictext����
	 */
	public List<Pictext> queryAll() {
		return this.pictextDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Pictext> queryByVO(Pictext pictext) {
		return this.pictextDao.queryByObject(pictext);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Pictext pictext) {
		return this.pictextDao.queryByObject(records, pictext);
	}

}