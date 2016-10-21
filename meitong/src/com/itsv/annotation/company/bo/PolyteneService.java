package com.itsv.annotation.company.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.company.dao.PolyteneDao;
import com.itsv.annotation.company.vo.Polytene;
import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.paged.IPagedList;
import com.itsv.gbp.core.util.BeanUtil;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵��������Ծ���ϩ���ܱ��ҵ�����
 *
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */
 @Service @Transactional 
public class PolyteneService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private PolyteneDao polyteneDao;

	/**
	 * ���Ӿ���ϩ���ܱ�
	 */
	public void add(Polytene polytene) {
		this.polyteneDao.save(polytene);
	}

	/**
	 * �޸ľ���ϩ���ܱ�
	 */
	public void update(Polytene polytene) {
		Polytene polytene_base=this.queryById(polytene.getId());
		BeanUtil.copyProperty(polytene_base, polytene);
		this.polyteneDao.update(polytene_base);
	}

	/**
	 * ɾ������ϩ���ܱ�
	 */
	public void delete(Serializable id) {
		Polytene polytene=this.queryById(id);
		if(polytene !=null){
			this.polyteneDao.removeById(polytene.getId());
		}
		
	}

	/**
	 * ����ID��ѯ����ϩ���ܱ����ϸ��Ϣ
	 */
	public Polytene queryById(Serializable polyteneid) {
		return this.polyteneDao.get(polyteneid);
	}

	/**
	 * ��ȡ���еľ���ϩ���ܱ����
	 */
	public List<Polytene> queryAll() {
		return this.polyteneDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Polytene> queryByVO(Polytene polytene) {
		return this.polyteneDao.queryByObject(polytene);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Polytene polytene) {
		return this.polyteneDao.queryByObject(records, polytene);
	}
	
	public Polytene findUniqueBy(String name, Object value) throws OrmException {
		return this.polyteneDao.findUniqueBy(name, value);

	}
	

}