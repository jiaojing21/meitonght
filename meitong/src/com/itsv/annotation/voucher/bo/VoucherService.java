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
 * 说明：处理对代金券的业务操作
 *
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Service @Transactional 
public class VoucherService extends BaseService {

  //数据访问层对象
  @Autowired
  private VoucherDao voucherDao;
  @Autowired
  private VoucherWithDao voucherWithDao;

	/**
	 * 增加代金券
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
	 * 修改代金券
	 */
	public void update(Voucher voucher) {
		this.voucherDao.update(voucher);
	}

	/**
	 * 删除代金券
	 */
	public void delete(Serializable id) {
		this.voucherDao.removeById(id);
	}

	/**
	 * 根据ID查询代金券的详细信息
	 */
	public Voucher queryById(Serializable voucherid) {
		return this.voucherDao.get(voucherid);
	}

	/**
	 * 获取所有的代金券对象
	 */
	public List<Voucher> queryAll() {
		return this.voucherDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Voucher> queryByVO(Voucher voucher) {
		return this.voucherDao.queryByObject(voucher);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Voucher voucher) {
		return this.voucherDao.queryByObject(records, voucher);
	}

}