package com.itsv.annotation.rule.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.rule.dao.RuleDao;
import com.itsv.annotation.rule.vo.Rule;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对代金券规则的业务操作
 *
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Service @Transactional 
public class RuleService extends BaseService {

  //数据访问层对象
  @Autowired
  private RuleDao ruleDao;

	/**
	 * 增加代金券规则
	 */
	public void add(Rule rule) {
		this.ruleDao.save(rule);
	}

	/**
	 * 修改代金券规则
	 */
	public void update(Rule rule) {
		this.ruleDao.update(rule);
	}

	/**
	 * 删除代金券规则
	 */
	public void delete(Serializable id) {
		this.ruleDao.removeById(id);
	}

	/**
	 * 根据ID查询代金券规则的详细信息
	 */
	public Rule queryById(Serializable ruleid) {
		return this.ruleDao.get(ruleid);
	}

	/**
	 * 获取所有的代金券规则对象
	 */
	public List<Rule> queryAll() {
		return this.ruleDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Rule> queryByVO(Rule rule) {
		return this.ruleDao.queryByObject(rule);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Rule rule) {
		return this.ruleDao.queryByObject(records, rule);
	}
	
	/**
	 * 添加规则
	 */
	public void saverule(List<Rule> rule) {
		List<Rule> rulelist = this.ruleDao.getAll();
		for(Rule ru : rulelist){
			this.ruleDao.remove(ru);
		}
		for(Rule r : rule){
			this.ruleDao.save(r);
		}
	}


}