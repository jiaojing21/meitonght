package com.itsv.annotation.suggestion.dao;

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
import com.itsv.annotation.suggestion.vo.Suggestion;

/**
 * suggestion��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class SuggestionDao extends HibernatePagedDao<Suggestion> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SuggestionDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Suggestion) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Suggestion) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Suggestion> queryByObject(Suggestion suggestion) {
		return find(buildCriteriaByVO(suggestion));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Suggestion suggestion) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(suggestion));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Suggestion suggestion){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (suggestion.getId() != null ) {
    	dc.add(Restrictions.eq("id", suggestion.getId()));
    }	

    //spokesman 
    if (suggestion.getSpokesMan() != null && suggestion.getSpokesMan().length() > 0) {
    	dc.add(Restrictions.like("spokesMan", suggestion.getSpokesMan(), MatchMode.ANYWHERE));
    }	

    //createtime 
    if (suggestion.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", suggestion.getCreatetime()));
      }		

    //flag 
    if (suggestion.getFlag() != null&& suggestion.getContent().length() > 0) {
    	dc.add(Restrictions.eq("flag", suggestion.getFlag()));
      }	
    
    //content 
    if (suggestion.getContent() != null && suggestion.getContent().length() > 0) {
    	dc.add(Restrictions.like("content", suggestion.getContent(), MatchMode.ANYWHERE));
    }	

    //answer 
    if (suggestion.getAnswer() != null && suggestion.getAnswer().length() > 0) {
    	dc.add(Restrictions.like("answer", suggestion.getAnswer(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (suggestion.getRemark1() != null && suggestion.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", suggestion.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (suggestion.getRemark2() != null && suggestion.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", suggestion.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Suggestion suggestion) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
