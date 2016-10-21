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
 * 说明：处理对vision的业务操作
 *
 * @author swk
 * @since 2016-05-04
 * @version 1.0
 */
 @Service @Transactional 
public class VisionService extends BaseService {

  //数据访问层对象
  @Autowired
  private VisionDao visionDao;

	/**
	 * 增加vision
	 */
	public void add(Vision vision) {
		this.visionDao.save(vision);
	}

	/**
	 * 修改vision
	 */
	public void update(Vision vision) {
		this.visionDao.update(vision);
	}

	/**
	 * 删除vision
	 */
	public void delete(Serializable id) {
		this.visionDao.removeById(id);
	}

	/**
	 * 根据ID查询vision的详细信息
	 */
	public Vision queryById(Serializable visionid) {
		return this.visionDao.get(visionid);
	}

	/**
	 * 获取所有的vision对象
	 */
	public List<Vision> queryAll() {
		return this.visionDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Vision> queryByVO(Vision vision) {
		return this.visionDao.queryByObject(vision);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Vision vision) {
		return this.visionDao.queryByObject(records, vision);
	}

}