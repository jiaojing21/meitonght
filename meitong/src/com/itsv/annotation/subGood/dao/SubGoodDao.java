package com.itsv.annotation.subGood.dao;

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
import com.itsv.annotation.subGood.vo.SubGood;

/**
 * subgood��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
 @Repository @Transactional
public class SubGoodDao extends HibernatePagedDao<SubGood> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SubGoodDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((SubGood) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((SubGood) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<SubGood> queryByObject(SubGood subGood) {
		return find(buildCriteriaByVO(subGood));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, SubGood subGood) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(subGood));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(SubGood subGood){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (subGood.getId() != null ) {
    	dc.add(Restrictions.eq("id", subGood.getId()));
    }	

    //title 
    if (subGood.getTitle() != null && subGood.getTitle().length() > 0) {
    	dc.add(Restrictions.like("title", subGood.getTitle(), MatchMode.ANYWHERE));
    }	

    //comment 
    if (subGood.getComment() != null && subGood.getComment().length() > 0) {
    	dc.add(Restrictions.like("comment", subGood.getComment(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (subGood.getRemark1() != null && subGood.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", subGood.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (subGood.getRemark2() != null && subGood.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", subGood.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(SubGood subGood) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
