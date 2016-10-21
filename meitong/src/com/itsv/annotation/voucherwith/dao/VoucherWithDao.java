package com.itsv.annotation.voucherwith.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

/**
 * 代金券详细对象的数据访问类
 * 
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Repository @Transactional
public class VoucherWithDao extends HibernatePagedDao<VoucherWith> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoucherWithDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((VoucherWith) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((VoucherWith) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<VoucherWith> queryByObject(VoucherWith voucherWith) {
		return find(buildCriteriaByVO(voucherWith));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, VoucherWith voucherWith) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(voucherWith));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(VoucherWith voucherWith){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (voucherWith.getId() != null ) {
    	dc.add(Restrictions.eq("id", voucherWith.getId()));
    }	

    //代金券唯一码 
    if (voucherWith.getCode() != null && voucherWith.getCode().length() > 0) {
    	dc.add(Restrictions.eq("code", voucherWith.getCode()));
    }	

    //代金券主键 
    if (voucherWith.getVoucherId() != null && voucherWith.getVoucherId().length() > 0) {
    	dc.add(Restrictions.eq("voucherId", voucherWith.getVoucherId()));
    }	

    //使用状态 
    if (voucherWith.getType() != null && voucherWith.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", voucherWith.getType()));
    }	

    return dc;  
  }

  //数据校验
  private void check(VoucherWith voucherWith) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
