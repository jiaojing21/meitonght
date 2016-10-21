package com.itsv.annotation.suggestion.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.suggestion.dao.SuggestionDao;
import com.itsv.annotation.suggestion.vo.Suggestion;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * ˵���������suggestion��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class SuggestionService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SuggestionDao suggestionDao;

	/**
	 * ����suggestion
	 */
	public void add(Suggestion suggestion) {
		this.suggestionDao.save(suggestion);
	}

	/**
	 * �޸�suggestion
	 */
	public void update(Suggestion suggestion) {
		this.suggestionDao.update(suggestion);
	}

	/**
	 * ɾ��suggestion
	 */
	public void delete(Serializable id) {
		this.suggestionDao.removeById(id);
	}

	/**
	 * ����ID��ѯsuggestion����ϸ��Ϣ
	 */
	public Suggestion queryById(Serializable suggestionid) {
		return this.suggestionDao.get(suggestionid);
	}

	/**
	 * ��ȡ���е�suggestion����
	 */
	public List<Suggestion> queryAll() {
		return this.suggestionDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Suggestion> queryByVO(Suggestion suggestion) {
		return this.suggestionDao.queryByObject(suggestion);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Suggestion suggestion) {
		return this.suggestionDao.queryByObject(records, suggestion);
	}

}