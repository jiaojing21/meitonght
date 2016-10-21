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
 * ����ȯ��ϸ��������ݷ�����
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
		//���л���У��
		check((VoucherWith) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((VoucherWith) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<VoucherWith> queryByObject(VoucherWith voucherWith) {
		return find(buildCriteriaByVO(voucherWith));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, VoucherWith voucherWith) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

    //����ȯΨһ�� 
    if (voucherWith.getCode() != null && voucherWith.getCode().length() > 0) {
    	dc.add(Restrictions.eq("code", voucherWith.getCode()));
    }	

    //����ȯ���� 
    if (voucherWith.getVoucherId() != null && voucherWith.getVoucherId().length() > 0) {
    	dc.add(Restrictions.eq("voucherId", voucherWith.getVoucherId()));
    }	

    //ʹ��״̬ 
    if (voucherWith.getType() != null && voucherWith.getType().length() > 0) {
    	dc.add(Restrictions.eq("type", voucherWith.getType()));
    }	

    return dc;  
  }

  //����У��
  private void check(VoucherWith voucherWith) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
