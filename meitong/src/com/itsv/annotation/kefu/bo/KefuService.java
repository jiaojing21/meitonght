package com.itsv.annotation.kefu.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.kefu.dao.KefuDao;
import com.itsv.annotation.kefu.vo.Kefu;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������kefu��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class KefuService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private KefuDao kefuDao;

	/**
	 * ����kefu
	 */
	public void add(Kefu kefu) {
		this.kefuDao.save(kefu);
	}

	/**
	 * �޸�kefu
	 */
	public void update(Kefu kefu) {
		this.kefuDao.update(kefu);
	}

	/**
	 * ɾ��kefu
	 */
	public void delete(Serializable id) {
		this.kefuDao.removeById(id);
	}

	/**
	 * ����ID��ѯkefu����ϸ��Ϣ
	 */
	public Kefu queryById(Serializable kefuid) {
		return this.kefuDao.get(kefuid);
	}

	/**
	 * ��ȡ���е�kefu����
	 */
	public List<Kefu> queryAll() {
		return this.kefuDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Kefu> queryByVO(Kefu kefu) {
		return this.kefuDao.queryByObject(kefu);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Kefu kefu) {
		return this.kefuDao.queryByObject(records, kefu);
	}

}