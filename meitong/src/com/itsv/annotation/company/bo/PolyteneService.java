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
 * 说明：处理对聚乙烯产能表的业务操作
 *
 * @author zkf
 * @since 2014-10-23
 * @version 1.0
 */
 @Service @Transactional 
public class PolyteneService extends BaseService {

  //数据访问层对象
  @Autowired
  private PolyteneDao polyteneDao;

	/**
	 * 增加聚乙烯产能表
	 */
	public void add(Polytene polytene) {
		this.polyteneDao.save(polytene);
	}

	/**
	 * 修改聚乙烯产能表
	 */
	public void update(Polytene polytene) {
		Polytene polytene_base=this.queryById(polytene.getId());
		BeanUtil.copyProperty(polytene_base, polytene);
		this.polyteneDao.update(polytene_base);
	}

	/**
	 * 删除聚乙烯产能表
	 */
	public void delete(Serializable id) {
		Polytene polytene=this.queryById(id);
		if(polytene !=null){
			this.polyteneDao.removeById(polytene.getId());
		}
		
	}

	/**
	 * 根据ID查询聚乙烯产能表的详细信息
	 */
	public Polytene queryById(Serializable polyteneid) {
		return this.polyteneDao.get(polyteneid);
	}

	/**
	 * 获取所有的聚乙烯产能表对象
	 */
	public List<Polytene> queryAll() {
		return this.polyteneDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Polytene> queryByVO(Polytene polytene) {
		return this.polyteneDao.queryByObject(polytene);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Polytene polytene) {
		return this.polyteneDao.queryByObject(records, polytene);
	}
	
	public Polytene findUniqueBy(String name, Object value) throws OrmException {
		return this.polyteneDao.findUniqueBy(name, value);

	}
	

}