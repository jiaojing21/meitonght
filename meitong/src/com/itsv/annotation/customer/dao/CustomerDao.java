package com.itsv.annotation.customer.dao;

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
import com.itsv.annotation.customer.vo.Customer;

/**
 * customer对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Repository @Transactional
public class CustomerDao extends HibernatePagedDao<Customer> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Customer) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Customer) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Customer> queryByObject(Customer customer) {
		return find(buildCriteriaByVO(customer));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Customer customer) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(customer));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Customer customer){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (customer.getId() != null ) {
    	dc.add(Restrictions.eq("id", customer.getId()));
    }	

    //phone 
    if (customer.getPhone() != null && customer.getPhone().length() > 0) {
    	dc.add(Restrictions.like("phone", customer.getPhone(), MatchMode.ANYWHERE));
    }	

    //nickname 
    if (customer.getNickname() != null && customer.getNickname().length() > 0) {
    	dc.add(Restrictions.like("nickname", customer.getNickname(), MatchMode.ANYWHERE));
    }	

    //realname 
    if (customer.getRealname() != null && customer.getRealname().length() > 0) {
    	dc.add(Restrictions.like("realname", customer.getRealname(), MatchMode.ANYWHERE));
    }	

    //loginpass 
    if (customer.getLoginpass() != null && customer.getLoginpass().length() > 0) {
    	dc.add(Restrictions.like("loginpass", customer.getLoginpass(), MatchMode.ANYWHERE));
    }	

    //paypass 
    if (customer.getPaypass() != null && customer.getPaypass().length() > 0) {
    	dc.add(Restrictions.like("paypass", customer.getPaypass(), MatchMode.ANYWHERE));
    }	

    //platform 
    if (customer.getPlatform() != null && customer.getPlatform().length() > 0) {
    	dc.add(Restrictions.like("platform", customer.getPlatform(), MatchMode.ANYWHERE));
    }	

    //logincode 
    if (customer.getLogincode() != null && customer.getLogincode().length() > 0) {
    	dc.add(Restrictions.like("logincode", customer.getLogincode(), MatchMode.ANYWHERE));
    }	

    //sex 
    if (customer.getSex() != null && customer.getSex().length() > 0) {
    	dc.add(Restrictions.like("sex", customer.getSex(), MatchMode.ANYWHERE));
    }	

    //birthday 
    if (customer.getBirthday() != null) {
    	dc.add(Restrictions.eq("birthday", customer.getBirthday()));
      }		

    //cash 
    if (customer.getCash() != null && customer.getCash().length() > 0) {
    	dc.add(Restrictions.like("cash", customer.getCash(), MatchMode.ANYWHERE));
    }	

    //remark 
    if (customer.getRemark() != null && customer.getRemark().length() > 0) {
    	dc.add(Restrictions.like("remark", customer.getRemark(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (customer.getRemark1() != null && customer.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", customer.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (customer.getRemark2() != null && customer.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", customer.getRemark2(), MatchMode.ANYWHERE));
    }	
    //注册时间
    if (customer.getCreateTime() != null ) {
    	dc.add(Restrictions.eq("createTime", customer.getCreateTime()));
    }

    return dc;  
  }

  //数据校验
  private void check(Customer customer) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
