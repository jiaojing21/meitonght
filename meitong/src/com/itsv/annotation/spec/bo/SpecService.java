package com.itsv.annotation.spec.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.spec.dao.SpecDao;
import com.itsv.annotation.spec.vo.Spec;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������spec��ҵ�����
 *
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
 @Service @Transactional 
public class SpecService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SpecDao specDao;

	/**
	 * ����spec
	 */
	public void add(Spec spec) {
		this.specDao.save(spec);
	}

	/**
	 * �޸�spec
	 */
	public void update(Spec spec) {
		this.specDao.update(spec);
	}

	/**
	 * ɾ��spec
	 */
	public void delete(Serializable id) {
		this.specDao.removeById(id);
	}

	/**
	 * ����ID��ѯspec����ϸ��Ϣ
	 */
	public Spec queryById(Serializable specid) {
		return this.specDao.get(specid);
	}

	/**
	 * ��ȡ���е�spec����
	 */
	public List<Spec> queryAll() {
		return this.specDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Spec> queryByVO(Spec spec) {
		return this.specDao.queryByObject(spec);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Spec spec) {
		return this.specDao.queryByObject(records, spec);
	}
	
}