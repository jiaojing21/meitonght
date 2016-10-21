package com.itsv.annotation.pictext.dao;

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
import com.itsv.annotation.pictext.vo.Pictext;

/**
 * pictext��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-05-06
 * @version 1.0
 */
 @Repository @Transactional
public class PictextDao extends HibernatePagedDao<Pictext> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PictextDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Pictext) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Pictext) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Pictext> queryByObject(Pictext pictext) {
		return find(buildCriteriaByVO(pictext));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Pictext pictext) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(pictext));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Pictext pictext){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (pictext.getId() != null ) {
    	dc.add(Restrictions.eq("id", pictext.getId()));
    }	

    //createtime 
    if (pictext.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", pictext.getCreatetime()));
      }		

    //userid 
    if (pictext.getUserId() != null && pictext.getUserId().length() > 0) {
    	dc.add(Restrictions.like("userId", pictext.getUserId(), MatchMode.ANYWHERE));
    }	

    //coment 
    if (pictext.getComent() != null && pictext.getComent().length() > 0) {
    	dc.add(Restrictions.like("coment", pictext.getComent(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Pictext pictext) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
