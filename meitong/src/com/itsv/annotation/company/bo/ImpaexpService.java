package com.itsv.annotation.company.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.company.dao.ImpaexpDao;
import com.itsv.annotation.company.vo.Impaexp;
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
 * ˵��������Խ����ڵ�ҵ�����
 *
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */
 @Service @Transactional 
public class ImpaexpService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private ImpaexpDao impaexpDao;

	/**
	 * ���ӽ�����
	 */
	public void add(Impaexp impaexp) {
		this.impaexpDao.save(impaexp);
	}

	/**
	 * �޸Ľ�����
	 */
	public void update(Impaexp impaexp) {
		Impaexp impaexp_base=this.queryById(impaexp.getId());
		BeanUtil.copyProperty(impaexp_base, impaexp);
		this.impaexpDao.update(impaexp_base);
	}

	/**
	 * ɾ��������
	 */
	public void delete(Serializable id) {
		Impaexp impaexp=this.queryById(id);
		if(impaexp !=null){
			this.impaexpDao.removeById(impaexp.getId());
		}
		
	}

	/**
	 * ����ID��ѯ�����ڵ���ϸ��Ϣ
	 */
	public Impaexp queryById(Serializable impaexpid) {
		return this.impaexpDao.get(impaexpid);
	}

	/**
	 * ��ȡ���еĽ����ڶ���
	 */
	public List<Impaexp> queryAll() {
		return this.impaexpDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Impaexp> queryByVO(Impaexp impaexp) {
		return this.impaexpDao.queryByObject(impaexp);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Impaexp impaexp) {
		return this.impaexpDao.queryByObject(records, impaexp);
	}
	
	/**
 	 * ����������������ֵ��ѯ����.
 	 *
 	 * @return ����������Ψһ����
 	 */
   public Impaexp findUniqueBy(String name, Object value) throws OrmException {
 		return this.impaexpDao.findUniqueBy(name, value);

   }

}