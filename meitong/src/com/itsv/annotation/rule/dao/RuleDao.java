package com.itsv.annotation.rule.dao;

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
import com.itsv.annotation.rule.vo.Rule;

/**
 * ����ȯ�����������ݷ�����
 * 
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Repository @Transactional
public class RuleDao extends HibernatePagedDao<Rule> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RuleDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Rule) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Rule) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Rule> queryByObject(Rule rule) {
		return find(buildCriteriaByVO(rule));
	}
	
	@Resource(name = "sessionFactory")    
	private SessionFactory sessionFactory;
	//ɾ��ȫ��
	public void delall() {
		sessionFactory.getCurrentSession().createSQLQuery("delete from rule");
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Rule rule) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(rule));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Rule rule){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (rule.getId() != null ) {
    	dc.add(Restrictions.eq("id", rule.getId()));
    }	

    //�������� 
    if (rule.getRuleName() != null && rule.getRuleName().length() > 0) {
    	dc.add(Restrictions.like("ruleName", rule.getRuleName(), MatchMode.ANYWHERE));
    }	

    //������ϸ 
    if (rule.getRuleDetail() != null && rule.getRuleDetail().length() > 0) {
    	dc.add(Restrictions.like("ruleDetail", rule.getRuleDetail(), MatchMode.ANYWHERE));
    }	

    //����ؼ��� 
    if (rule.getRuleCrux() != null && rule.getRuleCrux().length() > 0) {
    	dc.add(Restrictions.like("ruleCrux", rule.getRuleCrux(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Rule rule) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
