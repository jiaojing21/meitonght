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
 * 代金券规则对象的数据访问类
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
		//进行基本校验
		check((Rule) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Rule) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Rule> queryByObject(Rule rule) {
		return find(buildCriteriaByVO(rule));
	}
	
	@Resource(name = "sessionFactory")    
	private SessionFactory sessionFactory;
	//删除全部
	public void delall() {
		sessionFactory.getCurrentSession().createSQLQuery("delete from rule");
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Rule rule) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
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

    //规则名称 
    if (rule.getRuleName() != null && rule.getRuleName().length() > 0) {
    	dc.add(Restrictions.like("ruleName", rule.getRuleName(), MatchMode.ANYWHERE));
    }	

    //规则详细 
    if (rule.getRuleDetail() != null && rule.getRuleDetail().length() > 0) {
    	dc.add(Restrictions.like("ruleDetail", rule.getRuleDetail(), MatchMode.ANYWHERE));
    }	

    //规则关键字 
    if (rule.getRuleCrux() != null && rule.getRuleCrux().length() > 0) {
    	dc.add(Restrictions.like("ruleCrux", rule.getRuleCrux(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Rule rule) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
