package com.itsv.annotation.kefu.dao;

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
import com.itsv.annotation.kefu.vo.Kefu;

/**
 * kefu��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class KefuDao extends HibernatePagedDao<Kefu> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(KefuDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Kefu) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Kefu) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Kefu> queryByObject(Kefu kefu) {
		return find(buildCriteriaByVO(kefu));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Kefu kefu) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(kefu));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Kefu kefu){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (kefu.getId() != null ) {
    	dc.add(Restrictions.eq("id", kefu.getId()));
    }	

    //employeenumber 
    if (kefu.getEmployeeNumber() != null && kefu.getEmployeeNumber().length() > 0) {
    	dc.add(Restrictions.like("employeeNumber", kefu.getEmployeeNumber(), MatchMode.ANYWHERE));
    }	

    //name 
    if (kefu.getName() != null && kefu.getName().length() > 0) {
    	dc.add(Restrictions.like("name", kefu.getName(), MatchMode.ANYWHERE));
    }	

    //worktime 
    if (kefu.getWorkTime() != null && kefu.getWorkTime().length() > 0) {
    	dc.add(Restrictions.like("workTime", kefu.getWorkTime(), MatchMode.ANYWHERE));
    }	

    //answer 
    if (kefu.getAnswer() != null && kefu.getAnswer().length() > 0) {
    	dc.add(Restrictions.like("answer", kefu.getAnswer(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (kefu.getRemark1() != null && kefu.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", kefu.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (kefu.getRemark2() != null && kefu.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", kefu.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Kefu kefu) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
