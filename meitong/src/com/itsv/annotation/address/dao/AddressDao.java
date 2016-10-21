package com.itsv.annotation.address.dao;

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
import com.itsv.annotation.address.vo.Address;

/**
 * address��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-05-03
 * @version 1.0
 */
 @Repository @Transactional
public class AddressDao extends HibernatePagedDao<Address> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AddressDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Address) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Address) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Address> queryByObject(Address address) {
		return find(buildCriteriaByVO(address));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Address address) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(address));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Address address){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (address.getId() != null ) {
    	dc.add(Restrictions.eq("id", address.getId()));
    }	

    //customerid 
    if (address.getCustomerId() != null && address.getCustomerId().length() > 0) {
    	dc.add(Restrictions.like("customerId", address.getCustomerId(), MatchMode.ANYWHERE));
    }	

    //consignee 
    if (address.getConsignee() != null && address.getConsignee().length() > 0) {
    	dc.add(Restrictions.like("consignee", address.getConsignee(), MatchMode.ANYWHERE));
    }	

    //contact 
    if (address.getContact() != null && address.getContact().length() > 0) {
    	dc.add(Restrictions.like("contact", address.getContact(), MatchMode.ANYWHERE));
    }	

    //postcode 
    if (address.getPostCode() != null && address.getPostCode().length() > 0) {
    	dc.add(Restrictions.like("postCode", address.getPostCode(), MatchMode.ANYWHERE));
    }	

    //address_region 
    if (address.getAddressRegion() != null && address.getAddressRegion().length() > 0) {
    	dc.add(Restrictions.like("addressRegion", address.getAddressRegion(), MatchMode.ANYWHERE));
    }	

    //address 
    if (address.getAddress() != null && address.getAddress().length() > 0) {
    	dc.add(Restrictions.like("address", address.getAddress(), MatchMode.ANYWHERE));
    }	

    //details 
    if (address.getDetails() != null && address.getDetails().length() > 0) {
    	dc.add(Restrictions.like("details", address.getDetails(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (address.getRemark1() != null && address.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", address.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (address.getRemark2() != null && address.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", address.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Address address) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
