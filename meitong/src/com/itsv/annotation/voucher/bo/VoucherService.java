package com.itsv.annotation.voucher.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.itsv.annotation.util.UTILCN;
import com.itsv.annotation.voucher.dao.VoucherDao;
import com.itsv.annotation.voucher.vo.Voucher;
import com.itsv.annotation.voucherwith.dao.VoucherWithDao;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵��������Դ���ȯ��ҵ�����
 *
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Service @Transactional 
public class VoucherService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private VoucherDao voucherDao;
  @Autowired
  private VoucherWithDao voucherWithDao;

	/**
	 * ���Ӵ���ȯ
	 */
	public void add(Voucher voucher) {
		String amount = voucher.getAmount();
		int num = 0;
		if(!"".equals(amount)){
			num = Integer.parseInt(amount);
		}
		this.voucherDao.save(voucher);
		for(int i=0;i<num;i++){
			String code = UTILCN.sj();
			VoucherWith voucherWith = new VoucherWith();
			voucherWith.setCode(code);
			List<VoucherWith> vw = this.voucherWithDao.queryByObject(voucherWith);
			while(vw.size()>0){
				code = UTILCN.sj();
				VoucherWith with = new VoucherWith();
				with.setCode(code);
				vw = this.voucherWithDao.queryByObject(with);
			}
			voucherWith.setType("0");
			voucherWith.setCreatetime(new Date());
			voucherWith.setCreateuser(voucher.getCreateuser());
			voucherWith.setVoucherId(voucher.getId());
			this.voucherWithDao.save(voucherWith);
		}
	}

	/**
	 * �޸Ĵ���ȯ
	 */
	public void update(Voucher voucher) {
		this.voucherDao.update(voucher);
	}

	/**
	 * ɾ������ȯ
	 */
	public void delete(Serializable id) {
		this.voucherDao.removeById(id);
	}

	/**
	 * ����ID��ѯ����ȯ����ϸ��Ϣ
	 */
	public Voucher queryById(Serializable voucherid) {
		return this.voucherDao.get(voucherid);
	}

	/**
	 * ��ȡ���еĴ���ȯ����
	 */
	public List<Voucher> queryAll() {
		return this.voucherDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Voucher> queryByVO(Voucher voucher) {
		return this.voucherDao.queryByObject(voucher);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Voucher voucher) {
		return this.voucherDao.queryByObject(records, voucher);
	}

}