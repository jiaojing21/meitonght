package com.itsv.annotation.vision.dao;

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
import com.itsv.annotation.vision.vo.Vision;

/**
 * vision��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-05-04
 * @version 1.0
 */
 @Repository @Transactional
public class VisionDao extends HibernatePagedDao<Vision> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VisionDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Vision) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Vision) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Vision> queryByObject(Vision vision) {
		return find(buildCriteriaByVO(vision));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Vision vision) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(vision));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Vision vision){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (vision.getId() != null ) {
    	dc.add(Restrictions.eq("id", vision.getId()));
    }	

    //cusid 
    if (vision.getCusId() != null && vision.getCusId().length() > 0) {
    	dc.add(Restrictions.like("cusId", vision.getCusId(), MatchMode.ANYWHERE));
    }	

    //seatof 
    if (vision.getSeatof() != null && vision.getSeatof().length() > 0) {
    	dc.add(Restrictions.like("seatof", vision.getSeatof(), MatchMode.ANYWHERE));
    }	

    //mood 
    if (vision.getMood() != null && vision.getMood().length() > 0) {
    	dc.add(Restrictions.like("mood", vision.getMood(), MatchMode.ANYWHERE));
    }	

    //createtime 
    if (vision.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", vision.getCreatetime()));
      }		

    //auditStatus 
    if (vision.getAuditStatus() != null && vision.getAuditStatus().length() > 0) {
    	dc.add(Restrictions.like("auditStatus", vision.getAuditStatus(), MatchMode.ANYWHERE));
    }	

    //userid 
    if (vision.getUserId() != null && vision.getUserId().length() > 0) {
    	dc.add(Restrictions.like("userId", vision.getUserId(), MatchMode.ANYWHERE));
    }	

    //audittime 
    if (vision.getAuditTime() != null) {
    	dc.add(Restrictions.eq("auditTime", vision.getAuditTime()));
      }		

    return dc;  
  }

  //����У��
  private void check(Vision vision) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
