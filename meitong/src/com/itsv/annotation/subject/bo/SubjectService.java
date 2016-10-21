package com.itsv.annotation.subject.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.subject.dao.SubjectDao;
import com.itsv.annotation.subject.vo.Subject;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对subject的业务操作
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubjectService extends BaseService {

  //数据访问层对象
  @Autowired
  private SubjectDao subjectDao;

	/**
	 * 增加subject
	 */
	public void add(Subject subject) {
		this.subjectDao.save(subject);
	}

	/**
	 * 修改subject
	 */
	public void update(Subject subject) {
		this.subjectDao.update(subject);
	}

	/**
	 * 删除subject
	 */
	public void delete(Serializable id) {
		this.subjectDao.removeById(id);
	}

	/**
	 * 根据ID查询subject的详细信息
	 */
	public Subject queryById(Serializable subjectid) {
		return this.subjectDao.get(subjectid);
	}

	/**
	 * 获取所有的subject对象
	 */
	public List<Subject> queryAll() {
		return this.subjectDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Subject> queryByVO(Subject subject) {
		return this.subjectDao.queryByObject(subject);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Subject subject) {
		return this.subjectDao.queryByObject(records, subject);
	}

}