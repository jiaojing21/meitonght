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
 * 说明：处理对suggestion的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class SuggestionService extends BaseService {

  //数据访问层对象
  @Autowired
  private SuggestionDao suggestionDao;

	/**
	 * 增加suggestion
	 */
	public void add(Suggestion suggestion) {
		this.suggestionDao.save(suggestion);
	}

	/**
	 * 修改suggestion
	 */
	public void update(Suggestion suggestion) {
		this.suggestionDao.update(suggestion);
	}

	/**
	 * 删除suggestion
	 */
	public void delete(Serializable id) {
		this.suggestionDao.removeById(id);
	}

	/**
	 * 根据ID查询suggestion的详细信息
	 */
	public Suggestion queryById(Serializable suggestionid) {
		return this.suggestionDao.get(suggestionid);
	}

	/**
	 * 获取所有的suggestion对象
	 */
	public List<Suggestion> queryAll() {
		return this.suggestionDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Suggestion> queryByVO(Suggestion suggestion) {
		return this.suggestionDao.queryByObject(suggestion);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Suggestion suggestion) {
		return this.suggestionDao.queryByObject(records, suggestion);
	}

}