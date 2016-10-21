package com.itsv.annotation.ratio.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.ratio.dao.RatioDao;
import com.itsv.annotation.ratio.vo.Ratio;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵��������Ա������ҵ�����
 *
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */
 @Service @Transactional 
public class RatioService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private RatioDao ratioDao;

	/**
	 * ���ӱ�����
	 */
	public void add(Ratio ratio) {
		this.ratioDao.save(ratio);
	}

	/**
	 * �޸ı�����
	 */
	public void update(Ratio ratio) {
		this.ratioDao.update(ratio);
	}

	/**
	 * ɾ��������
	 */
	public void delete(Serializable id) {
		this.ratioDao.removeById(id);
	}

	/**
	 * ����ID��ѯ���������ϸ��Ϣ
	 */
	public Ratio queryById(Serializable ratioid) {
		return this.ratioDao.get(ratioid);
	}

	/**
	 * ��ȡ���еı��������
	 */
	public List<Ratio> queryAll() {
		return this.ratioDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Ratio> queryByVO(Ratio ratio) {
		return this.ratioDao.queryByObject(ratio);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Ratio ratio) {
		return this.ratioDao.queryByObject(records, ratio);
	}

}