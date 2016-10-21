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
 * vision对象的数据访问类
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
		//进行基本校验
		check((Vision) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Vision) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Vision> queryByObject(Vision vision) {
		return find(buildCriteriaByVO(vision));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Vision vision) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
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

  //数据校验
  private void check(Vision vision) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
