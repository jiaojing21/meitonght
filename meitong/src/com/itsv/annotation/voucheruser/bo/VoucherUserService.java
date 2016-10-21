package com.itsv.annotation.voucheruser.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.voucheruser.dao.VoucherUserDao;
import com.itsv.annotation.voucheruser.vo.VoucherUser;
import com.itsv.annotation.voucherwith.dao.VoucherWithDao;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对用户代金券表的业务操作
 *
 * @author yfh
 * @since 2016-04-21
 * @version 1.0
 */
 @Service @Transactional 
public class VoucherUserService extends BaseService {

  //数据访问层对象
  @Autowired
  private VoucherUserDao voucherUserDao;
  @Autowired
  private VoucherWithDao voucherWithDao;

	/**
	 * 增加用户代金券表
	 */
	public void add(VoucherUser voucherUser) {
		this.voucherUserDao.save(voucherUser);
	}

	/**
	 * 修改用户代金券表
	 */
	public void update(VoucherUser voucherUser) {
		this.voucherUserDao.update(voucherUser);
	}

	/**
	 * 删除用户代金券表
	 */
	public void delete(Serializable id) {
		this.voucherUserDao.removeById(id);
	}

	/**
	 * 根据ID查询用户代金券表的详细信息
	 */
	public VoucherUser queryById(Serializable voucherUserid) {
		return this.voucherUserDao.get(voucherUserid);
	}

	/**
	 * 获取所有的用户代金券表对象
	 */
	public List<VoucherUser> queryAll() {
		return this.voucherUserDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<VoucherUser> queryByVO(VoucherUser voucherUser) {
		return this.voucherUserDao.queryByObject(voucherUser);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, VoucherUser voucherUser) {
		return this.voucherUserDao.queryByObject(records, voucherUser);
	}
	/**
	 * 新增
	 * @param vu
	 * @param appid
	 */
	public void save(VoucherUser vu,String appid){
		this.voucherUserDao.save(vu);
		String [] app = appid.split(",");
		for(String id : app){
			if(null!=id&&!"".equals(id)){
				VoucherWith vw = this.voucherWithDao.get(id);
				vw.setType("1");
				this.voucherWithDao.update(vw);
			}
		}
		
		
	}

}