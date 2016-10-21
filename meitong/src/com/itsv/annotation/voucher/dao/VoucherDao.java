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
 * ����ȯ��������ݷ�����
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
		//���л���У��
		check((Voucher) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Voucher) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Voucher> queryByObject(Voucher voucher) {
		return find(buildCriteriaByVO(voucher));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Voucher voucher) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

    //����ȯ��ֵ 
    if (voucher.getWorth() != null && voucher.getWorth().length() > 0) {
    	dc.add(Restrictions.like("worth", voucher.getWorth(), MatchMode.ANYWHERE));
    }	

    //���� 
    if (voucher.getTerm() != null && voucher.getTerm().length() > 0) {
    	dc.add(Restrictions.like("term", voucher.getTerm(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Voucher voucher) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
