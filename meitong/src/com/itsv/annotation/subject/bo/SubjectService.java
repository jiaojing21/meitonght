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
 * ˵���������subject��ҵ�����
 *
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Service @Transactional 
public class SubjectService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private SubjectDao subjectDao;

	/**
	 * ����subject
	 */
	public void add(Subject subject) {
		this.subjectDao.save(subject);
	}

	/**
	 * �޸�subject
	 */
	public void update(Subject subject) {
		this.subjectDao.update(subject);
	}

	/**
	 * ɾ��subject
	 */
	public void delete(Serializable id) {
		this.subjectDao.removeById(id);
	}

	/**
	 * ����ID��ѯsubject����ϸ��Ϣ
	 */
	public Subject queryById(Serializable subjectid) {
		return this.subjectDao.get(subjectid);
	}

	/**
	 * ��ȡ���е�subject����
	 */
	public List<Subject> queryAll() {
		return this.subjectDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Subject> queryByVO(Subject subject) {
		return this.subjectDao.queryByObject(subject);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Subject subject) {
		return this.subjectDao.queryByObject(records, subject);
	}

}