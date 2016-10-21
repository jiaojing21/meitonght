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
 * 说明：处理对进出口的业务操作
 *
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */
 @Service @Transactional 
public class ImpaexpService extends BaseService {

  //数据访问层对象
  @Autowired
  private ImpaexpDao impaexpDao;

	/**
	 * 增加进出口
	 */
	public void add(Impaexp impaexp) {
		this.impaexpDao.save(impaexp);
	}

	/**
	 * 修改进出口
	 */
	public void update(Impaexp impaexp) {
		Impaexp impaexp_base=this.queryById(impaexp.getId());
		BeanUtil.copyProperty(impaexp_base, impaexp);
		this.impaexpDao.update(impaexp_base);
	}

	/**
	 * 删除进出口
	 */
	public void delete(Serializable id) {
		Impaexp impaexp=this.queryById(id);
		if(impaexp !=null){
			this.impaexpDao.removeById(impaexp.getId());
		}
		
	}

	/**
	 * 根据ID查询进出口的详细信息
	 */
	public Impaexp queryById(Serializable impaexpid) {
		return this.impaexpDao.get(impaexpid);
	}

	/**
	 * 获取所有的进出口对象
	 */
	public List<Impaexp> queryAll() {
		return this.impaexpDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Impaexp> queryByVO(Impaexp impaexp) {
		return this.impaexpDao.queryByObject(impaexp);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Impaexp impaexp) {
		return this.impaexpDao.queryByObject(records, impaexp);
	}
	
	/**
 	 * 根据属性名和属性值查询对象.
 	 *
 	 * @return 符合条件的唯一对象
 	 */
   public Impaexp findUniqueBy(String name, Object value) throws OrmException {
 		return this.impaexpDao.findUniqueBy(name, value);

   }

}