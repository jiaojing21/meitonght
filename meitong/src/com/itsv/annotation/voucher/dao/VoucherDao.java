package com.itsv.annotation.voucher.dao;

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
import com.itsv.annotation.voucher.vo.Voucher;

/**
 * 代金券对象的数据访问类
 * 
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Repository @Transactional
public class VoucherDao extends HibernatePagedDao<Voucher> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoucherDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Voucher) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Voucher) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Voucher> queryByObject(Voucher voucher) {
		return find(buildCriteriaByVO(voucher));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Voucher voucher) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(voucher));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Voucher voucher){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (voucher.getId() != null ) {
    	dc.add(Restrictions.eq("id", voucher.getId()));
    }	

    //代金券价值 
    if (voucher.getWorth() != null && voucher.getWorth().length() > 0) {
    	dc.add(Restrictions.like("worth", voucher.getWorth(), MatchMode.ANYWHERE));
    }	

    //期限 
    if (voucher.getTerm() != null && voucher.getTerm().length() > 0) {
    	dc.add(Restrictions.like("term", voucher.getTerm(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Voucher voucher) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
