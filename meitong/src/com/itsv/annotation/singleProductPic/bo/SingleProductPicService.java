package com.itsv.annotation.singleProductPic.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.singleProductPic.dao.SingleProductPicDao;
import com.itsv.annotation.singleProductPic.vo.SingleProductPic;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对single_product_pic的业务操作
 *
 * @author swk
 * @since 2016-04-11
 * @version 1.0
 */
 @Service @Transactional 
public class SingleProductPicService extends BaseService {

  //数据访问层对象
  @Autowired
  private SingleProductPicDao singleProductPicDao;

	/**
	 * 增加single_product_pic
	 */
	public void add(SingleProductPic singleProductPic) {
		this.singleProductPicDao.save(singleProductPic);
	}

	/**
	 * 修改single_product_pic
	 */
	public void update(SingleProductPic singleProductPic) {
		this.singleProductPicDao.update(singleProductPic);
	}

	/**
	 * 删除single_product_pic
	 */
	public void delete(Serializable id) {
		this.singleProductPicDao.removeById(id);
	}

	/**
	 * 根据ID查询single_product_pic的详细信息
	 */
	public SingleProductPic queryById(Serializable singleProductPicid) {
		return this.singleProductPicDao.get(singleProductPicid);
	}

	/**
	 * 获取所有的single_product_pic对象
	 */
	public List<SingleProductPic> queryAll() {
		return this.singleProductPicDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<SingleProductPic> queryByVO(SingleProductPic singleProductPic) {
		return this.singleProductPicDao.queryByObject(singleProductPic);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, SingleProductPic singleProductPic) {
		return this.singleProductPicDao.queryByObject(records, singleProductPic);
	}

}