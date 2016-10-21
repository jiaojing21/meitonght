package com.itsv.annotation.subject.dao;

import java.util.List;

import javax.annotation.Resource;

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
import com.itsv.annotation.subject.vo.Subject;

/**
 * subject对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class SubjectDao extends HibernatePagedDao<Subject> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SubjectDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Subject) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Subject) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Subject> queryByObject(Subject subject) {
		return find(buildCriteriaByVO(subject));
	}
	

	
  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Subject subject) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(subject));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Subject subject){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (subject.getId() != null ) {
    	dc.add(Restrictions.eq("id", subject.getId()));
    }	

    //title 
    if (subject.getTitle() != null && subject.getTitle().length() > 0) {
    	dc.add(Restrictions.like("title", subject.getTitle(), MatchMode.ANYWHERE));
    }	

    //username 
    if (subject.getUserName() != null && subject.getUserName().length() > 0) {
    	dc.add(Restrictions.like("userName", subject.getUserName(), MatchMode.ANYWHERE));
    }	

    //picture 
    if (subject.getSubGoodId() != null && subject.getSubGoodId().length() > 0) {
    	dc.add(Restrictions.like("subGoodId", subject.getSubGoodId(), MatchMode.ANYWHERE));
    }	

    //createtime 
    if (subject.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", subject.getCreatetime()));
      }		

    //comment 
    if (subject.getComment() != null && subject.getComment().length() > 0) {
    	dc.add(Restrictions.like("comment", subject.getComment(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (subject.getRemark1() != null && subject.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", subject.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (subject.getRemark2() != null && subject.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", subject.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Subject subject) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
