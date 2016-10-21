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
 * ˵���������single_product_pic��ҵ�����
 *
 * @author swk
 * @since 2016-04-11
 * @version 1.0
 */
 @Service @Transactional 
public class SingleProductPicService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SingleProductPicDao singleProductPicDao;

	/**
	 * ����single_product_pic
	 */
	public void add(SingleProductPic singleProductPic) {
		this.singleProductPicDao.save(singleProductPic);
	}

	/**
	 * �޸�single_product_pic
	 */
	public void update(SingleProductPic singleProductPic) {
		this.singleProductPicDao.update(singleProductPic);
	}

	/**
	 * ɾ��single_product_pic
	 */
	public void delete(Serializable id) {
		this.singleProductPicDao.removeById(id);
	}

	/**
	 * ����ID��ѯsingle_product_pic����ϸ��Ϣ
	 */
	public SingleProductPic queryById(Serializable singleProductPicid) {
		return this.singleProductPicDao.get(singleProductPicid);
	}

	/**
	 * ��ȡ���е�single_product_pic����
	 */
	public List<SingleProductPic> queryAll() {
		return this.singleProductPicDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<SingleProductPic> queryByVO(SingleProductPic singleProductPic) {
		return this.singleProductPicDao.queryByObject(singleProductPic);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, SingleProductPic singleProductPic) {
		return this.singleProductPicDao.queryByObject(records, singleProductPic);
	}

}