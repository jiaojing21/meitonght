package com.itsv.test.xsgl.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.test.xsgl.dao.StudentDao;
import com.itsv.test.xsgl.vo.Student;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * ˵���������student��ҵ�����
 *
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class StudentService extends BaseService {

  //���ݷ��ʲ����
  private StudentDao studentDao;

	/**
	 * ����student
	 */
	public void add(Student student) {
		this.studentDao.save(student);
	}

	/**
	 * �޸�student
	 */
	public void update(Student student) {
		this.studentDao.update(student);
	}

	/**
	 * ɾ��student
	 */
	public void delete(Serializable id) {
		this.studentDao.removeById(id);
	}

	/**
	 * ����ID��ѯstudent����ϸ��Ϣ
	 */
	public Student queryById(Serializable studentid) {
		return this.studentDao.get(studentid);
	}

	/**
	 * ��ȡ���е�student����
	 */
	public List<Student> queryAll() {
		return this.studentDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<Student> queryByVO(Student student) {
		return this.studentDao.queryByObject(student);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, Student student) {
		return this.studentDao.queryByObject(records, student);
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

}