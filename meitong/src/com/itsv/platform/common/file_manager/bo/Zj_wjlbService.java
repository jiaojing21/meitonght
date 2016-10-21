package com.itsv.platform.common.file_manager.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.platform.common.file_manager.dao.Zj_wjlbDao;
import com.itsv.platform.common.fileMgr.vo.ZjWjlb;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对存储类别信息的业务操作
 *
 * @author milu
 * @since 2007-07-17
 * @version 1.0
 */
public class Zj_wjlbService extends BaseService {

  //数据访问层对象
  private Zj_wjlbDao zj_wjlbDao;

	/**
	 * 增加存储类别信息
	 */
	public void add(ZjWjlb ZjWjlb) {
		ZjWjlb wjlb = new ZjWjlb();
		wjlb.setCclbmc(ZjWjlb.getCclbmc());
		wjlb.setZt("1");
		List list = this.zj_wjlbDao.queryByObject(wjlb);
		if(list.size() != 0){
			wjlb = (ZjWjlb)list.get(0);
			wjlb.setZt("0");
			this.zj_wjlbDao.update(wjlb);
			ZjWjlb.setLbmcbb(wjlb.getLbmcbb() + 1);
		}
		this.zj_wjlbDao.save(ZjWjlb);
	}

	/**
	 * 修改存储类别信息
	 */
	public void update(ZjWjlb ZjWjlb) {
		this.zj_wjlbDao.update(ZjWjlb);
	}

	/**
	 * 删除存储类别信息
	 */
	public void delete(Serializable id) {
		ZjWjlb wjlb = new ZjWjlb();
		wjlb.setId((String)id);
		List list = this.zj_wjlbDao.queryByObject(wjlb);
		if(list == null || list.size() != 0){
			wjlb = (ZjWjlb)list.get(0);
			ZjWjlb qrywjlb = new ZjWjlb();
			qrywjlb.setCclbmc(wjlb.getCclbmc());
			qrywjlb.setLbmcbb(wjlb.getLbmcbb() - 1);
			List qrylist = this.zj_wjlbDao.queryByObject(qrywjlb);
			if(qrylist == null || qrylist.size() != 0){
				wjlb = (ZjWjlb)qrylist.get(0);
				wjlb.setZt("1");
				this.zj_wjlbDao.update(wjlb);
			}
		}
		this.zj_wjlbDao.removeById(id);
	}

	/**
	 * 根据ID查询存储类别信息的详细信息
	 */
	public ZjWjlb queryById(Serializable zj_wjlbid) {
		return this.zj_wjlbDao.get(zj_wjlbid);
	}

	/**
	 * 获取所有的存储类别信息对象
	 */
	public List<ZjWjlb> queryAll() {
		return this.zj_wjlbDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<ZjWjlb> queryByVO(ZjWjlb ZjWjlb) {
		return this.zj_wjlbDao.queryByObject(ZjWjlb);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, ZjWjlb ZjWjlb) {
		return this.zj_wjlbDao.queryByObject(records, ZjWjlb);
	}

	public void setZj_wjlbDao(Zj_wjlbDao zj_wjlbDao) {
		this.zj_wjlbDao = zj_wjlbDao;
	}

}