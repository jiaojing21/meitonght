package com.itsv.annotation.region.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.region.dao.RegionDao;
import com.itsv.annotation.region.vo.Region;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������region��ҵ�����
 *
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
 @Service @Transactional 
public class RegionService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private RegionDao regionDao;

	/**
	 * ����region
	 */
	public void add(Region region) {
		this.regionDao.save(region);
	}

	/**
	 * �޸�region
	 */
	public void update(Region region) {
		this.regionDao.update(region);
	}

	/**
	 * ɾ��region
	 */
	public void delete(Serializable id) {
		this.regionDao.removeById(id);
	}

	/**
	 * ����ID��ѯregion����ϸ��Ϣ
	 */
	public Region queryById(Serializable regionid) {
		return this.regionDao.get(regionid);
	}

	/**
	 * ��ȡ���е�region����
	 */
	public List<Region> queryAll() {
		return this.regionDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Region> queryByVO(Region region) {
		return this.regionDao.queryByObject(region);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Region region) {
		return this.regionDao.queryByObject(records, region);
	}

}