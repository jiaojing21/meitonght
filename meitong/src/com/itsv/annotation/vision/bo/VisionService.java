package com.itsv.annotation.vision.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.vision.dao.VisionDao;
import com.itsv.annotation.vision.vo.Vision;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������vision��ҵ�����
 *
 * @author swk
 * @since 2016-05-04
 * @version 1.0
 */
 @Service @Transactional 
public class VisionService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private VisionDao visionDao;

	/**
	 * ����vision
	 */
	public void add(Vision vision) {
		this.visionDao.save(vision);
	}

	/**
	 * �޸�vision
	 */
	public void update(Vision vision) {
		this.visionDao.update(vision);
	}

	/**
	 * ɾ��vision
	 */
	public void delete(Serializable id) {
		this.visionDao.removeById(id);
	}

	/**
	 * ����ID��ѯvision����ϸ��Ϣ
	 */
	public Vision queryById(Serializable visionid) {
		return this.visionDao.get(visionid);
	}

	/**
	 * ��ȡ���е�vision����
	 */
	public List<Vision> queryAll() {
		return this.visionDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Vision> queryByVO(Vision vision) {
		return this.visionDao.queryByObject(vision);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Vision vision) {
		return this.visionDao.queryByObject(records, vision);
	}

}