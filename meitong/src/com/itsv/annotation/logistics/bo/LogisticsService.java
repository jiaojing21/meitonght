package com.itsv.annotation.logistics.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.logistics.dao.LogisticsDao;
import com.itsv.annotation.logistics.vo.Logistics;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������logistics��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class LogisticsService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private LogisticsDao logisticsDao;

	/**
	 * ����logistics
	 */
	public void add(Logistics logistics) {
		this.logisticsDao.save(logistics);
	}

	/**
	 * �޸�logistics
	 */
	public void update(Logistics logistics) {
		this.logisticsDao.update(logistics);
	}

	/**
	 * ɾ��logistics
	 */
	public void delete(Serializable id) {
		this.logisticsDao.removeById(id);
	}
	
	/**
	 * ����ID��ѯlogistics����ϸ��Ϣ
	 */
	public Logistics queryById(Serializable logisticsid) {
		return this.logisticsDao.get(logisticsid);
	}

	/**
	 * ��ȡ���е�logistics����
	 */
	public List<Logistics> queryAll() {
		return this.logisticsDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Logistics> queryByVO(Logistics logistics) {
		return this.logisticsDao.queryByObject(logistics);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Logistics logistics) {
		return this.logisticsDao.queryByObject(records, logistics);
	}

}