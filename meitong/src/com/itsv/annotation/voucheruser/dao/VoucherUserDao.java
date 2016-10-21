package com.itsv.annotation.voucheruser.dao;

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
import com.itsv.annotation.voucheruser.vo.VoucherUser;

/**
 * 用户代金券表对象的数据访问类
 * 
 * 
 * @author yfh
 * @since 2016-04-21
 * @version 1.0
 */
 @Repository @Transactional
public class VoucherUserDao extends HibernatePagedDao<VoucherUser> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoucherUserDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((VoucherUser) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((VoucherUser) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<VoucherUser> queryByObject(VoucherUser voucherUser) {
		return find(buildCriteriaByVO(voucherUser));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, VoucherUser voucherUser) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(voucherUser));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(VoucherUser voucherUser){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (voucherUser.getId() != null ) {
    	dc.add(Restrictions.eq("id", voucherUser.getId()));
    }	

    //代金券详细主键 
    if (voucherUser.getVoucherWithId() != null && voucherUser.getVoucherWithId().length() > 0) {
    	dc.add(Restrictions.like("voucherWithId", voucherUser.getVoucherWithId(), MatchMode.ANYWHERE));
    }	

    //用户表主键 
    if (voucherUser.getCusId() != null && voucherUser.getCusId().length() > 0) {
    	dc.add(Restrictions.like("cusId", voucherUser.getCusId(), MatchMode.ANYWHERE));
    }	

    //代金券唯一码 
    if (voucherUser.getCode() != null && voucherUser.getCode().length() > 0) {
    	dc.add(Restrictions.like("code", voucherUser.getCode(), MatchMode.ANYWHERE));
    }	

    //获取途径（例：新用户注册） 
    if (voucherUser.getAccess() != null && voucherUser.getAccess().length() > 0) {
    	dc.add(Restrictions.like("access", voucherUser.getAccess(), MatchMode.ANYWHERE));
    }	

    //获取时间 
    if (voucherUser.getFetchTime() != null) {
    	dc.add(Restrictions.eq("fetchTime", voucherUser.getFetchTime()));
      }		

    //失效时间 
    if (voucherUser.getFailureTime() != null) {
    	dc.add(Restrictions.eq("failureTime", voucherUser.getFailureTime()));
      }		

    //状态（0：未领取；1：已领取） 
    if (voucherUser.getType() != null && voucherUser.getType().length() > 0) {
    	dc.add(Restrictions.like("type", voucherUser.getType(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(VoucherUser voucherUser) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
