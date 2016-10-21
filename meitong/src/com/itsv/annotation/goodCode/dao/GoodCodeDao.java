package com.itsv.annotation.goodCode.dao;

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
import com.itsv.annotation.goodCode.vo.GoodCode;

/**
 * goodcode��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
 @Repository @Transactional
public class GoodCodeDao extends HibernatePagedDao<GoodCode> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GoodCodeDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((GoodCode) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((GoodCode) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<GoodCode> queryByObject(GoodCode goodCode) {
		return find(buildCriteriaByVO(goodCode));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, GoodCode goodCode) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(goodCode));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(GoodCode goodCode){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (goodCode.getId() != null ) {
    	dc.add(Restrictions.eq("id", goodCode.getId()));
    }	

    //goodid 
    if (goodCode.getGoodId() != null && goodCode.getGoodId().length() > 0) {
    	dc.add(Restrictions.like("goodId", goodCode.getGoodId(), MatchMode.ANYWHERE));
    }	

    //code 
    if (goodCode.getCode() != null && goodCode.getCode().length() > 0) {
    	dc.add(Restrictions.like("code", goodCode.getCode(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(GoodCode goodCode) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
