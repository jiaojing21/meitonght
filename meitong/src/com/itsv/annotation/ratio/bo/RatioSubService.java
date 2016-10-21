package com.itsv.annotation.ratio.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.ratio.dao.RatioSubDao;
import com.itsv.annotation.ratio.vo.RatioSub;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵��������Ա���ͼ�ӱ��ҵ�����
 *
 * @author quyf
 * @since 2014-12-30
 * @version 1.0
 */
 @Service @Transactional 
public class RatioSubService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private RatioSubDao ratioSubDao;

	/**
	 * ���ӱ���ͼ�ӱ�
	 */
	public void add(RatioSub ratioSub) {
		this.ratioSubDao.save(ratioSub);
	}

	/**
	 * �޸ı���ͼ�ӱ�
	 */
	public void update(RatioSub ratioSub) {
		this.ratioSubDao.update(ratioSub);
	}

	/**
	 * ɾ������ͼ�ӱ�
	 */
	public void delete(Serializable id) {
		this.ratioSubDao.removeById(id);
	}

	/**
	 * ����ID��ѯ����ͼ�ӱ����ϸ��Ϣ
	 */
	public RatioSub queryById(Serializable ratioSubid) {
		return this.ratioSubDao.get(ratioSubid);
	}

	/**
	 * ��ȡ���еı���ͼ�ӱ����
	 */
	public List<RatioSub> queryAll() {
		return this.ratioSubDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<RatioSub> queryByVO(RatioSub ratioSub) {
		return this.ratioSubDao.queryByObject(ratioSub);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, RatioSub ratioSub) {
		return this.ratioSubDao.queryByObject(records, ratioSub);
	}

}