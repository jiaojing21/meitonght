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
 * ˵��������Դ���ȯ�����ҵ�����
 *
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
 @Service @Transactional 
public class RuleService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private RuleDao ruleDao;

	/**
	 * ���Ӵ���ȯ����
	 */
	public void add(Rule rule) {
		this.ruleDao.save(rule);
	}

	/**
	 * �޸Ĵ���ȯ����
	 */
	public void update(Rule rule) {
		this.ruleDao.update(rule);
	}

	/**
	 * ɾ������ȯ����
	 */
	public void delete(Serializable id) {
		this.ruleDao.removeById(id);
	}

	/**
	 * ����ID��ѯ����ȯ�������ϸ��Ϣ
	 */
	public Rule queryById(Serializable ruleid) {
		return this.ruleDao.get(ruleid);
	}

	/**
	 * ��ȡ���еĴ���ȯ�������
	 */
	public List<Rule> queryAll() {
		return this.ruleDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Rule> queryByVO(Rule rule) {
		return this.ruleDao.queryByObject(rule);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Rule rule) {
		return this.ruleDao.queryByObject(records, rule);
	}
	
	/**
	 * ��ӹ���
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