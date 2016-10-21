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
 * �û�����ȯ���������ݷ�����
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
		//���л���У��
		check((VoucherUser) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((VoucherUser) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<VoucherUser> queryByObject(VoucherUser voucherUser) {
		return find(buildCriteriaByVO(voucherUser));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, VoucherUser voucherUser) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

    //����ȯ��ϸ���� 
    if (voucherUser.getVoucherWithId() != null && voucherUser.getVoucherWithId().length() > 0) {
    	dc.add(Restrictions.like("voucherWithId", voucherUser.getVoucherWithId(), MatchMode.ANYWHERE));
    }	

    //�û������� 
    if (voucherUser.getCusId() != null && voucherUser.getCusId().length() > 0) {
    	dc.add(Restrictions.like("cusId", voucherUser.getCusId(), MatchMode.ANYWHERE));
    }	

    //����ȯΨһ�� 
    if (voucherUser.getCode() != null && voucherUser.getCode().length() > 0) {
    	dc.add(Restrictions.like("code", voucherUser.getCode(), MatchMode.ANYWHERE));
    }	

    //��ȡ;�����������û�ע�ᣩ 
    if (voucherUser.getAccess() != null && voucherUser.getAccess().length() > 0) {
    	dc.add(Restrictions.like("access", voucherUser.getAccess(), MatchMode.ANYWHERE));
    }	

    //��ȡʱ�� 
    if (voucherUser.getFetchTime() != null) {
    	dc.add(Restrictions.eq("fetchTime", voucherUser.getFetchTime()));
      }		

    //ʧЧʱ�� 
    if (voucherUser.getFailureTime() != null) {
    	dc.add(Restrictions.eq("failureTime", voucherUser.getFailureTime()));
      }		

    //״̬��0��δ��ȡ��1������ȡ�� 
    if (voucherUser.getType() != null && voucherUser.getType().length() > 0) {
    	dc.add(Restrictions.like("type", voucherUser.getType(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(VoucherUser voucherUser) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
