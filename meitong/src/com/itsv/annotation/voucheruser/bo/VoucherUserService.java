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
 * ˵����������û�����ȯ���ҵ�����
 *
 * @author yfh
 * @since 2016-04-21
 * @version 1.0
 */
 @Service @Transactional 
public class VoucherUserService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private VoucherUserDao voucherUserDao;
  @Autowired
  private VoucherWithDao voucherWithDao;

	/**
	 * �����û�����ȯ��
	 */
	public void add(VoucherUser voucherUser) {
		this.voucherUserDao.save(voucherUser);
	}

	/**
	 * �޸��û�����ȯ��
	 */
	public void update(VoucherUser voucherUser) {
		this.voucherUserDao.update(voucherUser);
	}

	/**
	 * ɾ���û�����ȯ��
	 */
	public void delete(Serializable id) {
		this.voucherUserDao.removeById(id);
	}

	/**
	 * ����ID��ѯ�û�����ȯ�����ϸ��Ϣ
	 */
	public VoucherUser queryById(Serializable voucherUserid) {
		return this.voucherUserDao.get(voucherUserid);
	}

	/**
	 * ��ȡ���е��û�����ȯ�����
	 */
	public List<VoucherUser> queryAll() {
		return this.voucherUserDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<VoucherUser> queryByVO(VoucherUser voucherUser) {
		return this.voucherUserDao.queryByObject(voucherUser);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, VoucherUser voucherUser) {
		return this.voucherUserDao.queryByObject(records, voucherUser);
	}
	/**
	 * ����
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