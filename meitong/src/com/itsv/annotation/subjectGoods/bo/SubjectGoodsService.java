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
 * 说明：处理对subject_goods的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubjectGoodsService extends BaseService {

  //数据访问层对象
  @Autowired
  private SubjectGoodsDao subjectGoodsDao;

	/**
	 * 增加subject_goods
	 */
	public void add(SubjectGoods subjectGoods) {
		this.subjectGoodsDao.save(subjectGoods);
	}

	/**
	 * 修改subject_goods
	 */
	public void update(SubjectGoods subjectGoods) {
		this.subjectGoodsDao.update(subjectGoods);
	}

	/**
	 * 删除subject_goods
	 */
	public void delete(Serializable id) {
		this.subjectGoodsDao.removeById(id);
	}

	/**
	 * 根据ID查询subject_goods的详细信息
	 */
	public SubjectGoods queryById(Serializable subjectGoodsid) {
		return this.subjectGoodsDao.get(subjectGoodsid);
	}

	/**
	 * 获取所有的subject_goods对象
	 */
	public List<SubjectGoods> queryAll() {
		return this.subjectGoodsDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<SubjectGoods> queryByVO(SubjectGoods subjectGoods) {
		return this.subjectGoodsDao.queryByObject(subjectGoods);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, SubjectGoods subjectGoods) {
		return this.subjectGoodsDao.queryByObject(records, subjectGoods);
	}

}