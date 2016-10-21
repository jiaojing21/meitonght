package com.itsv.annotation.voucherwith.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.voucherwith.dao.VoucherWithDao;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵��������Դ���ȯ��ϸ��ҵ�����
 *
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Service @Transactional 
public class VoucherWithService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private VoucherWithDao voucherWithDao;

	/**
	 * ���Ӵ���ȯ��ϸ
	 */
	public void add(VoucherWith voucherWith) {
		this.voucherWithDao.save(voucherWith);
	}

	/**
	 * �޸Ĵ���ȯ��ϸ
	 */
	public void update(VoucherWith voucherWith) {
		this.voucherWithDao.update(voucherWith);
	}

	/**
	 * ɾ������ȯ��ϸ
	 */
	public void delete(Serializable id) {
		this.voucherWithDao.removeById(id);
	}

	/**
	 * ����ID��ѯ����ȯ��ϸ����ϸ��Ϣ
	 */
	public VoucherWith queryById(Serializable voucherWithid) {
		return this.voucherWithDao.get(voucherWithid);
	}

	/**
	 * ��ȡ���еĴ���ȯ��ϸ����
	 */
	public List<VoucherWith> queryAll() {
		return this.voucherWithDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<VoucherWith> queryByVO(VoucherWith voucherWith) {
		return this.voucherWithDao.queryByObject(voucherWith);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, VoucherWith voucherWith) {
		return this.voucherWithDao.queryByObject(records, voucherWith);
	}

}