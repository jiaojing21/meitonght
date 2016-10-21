package com.itsv.test.xsgl.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.test.xsgl.dao.StudentDao;
import com.itsv.test.xsgl.vo.Student;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对student的业务操作
 *
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class StudentService extends BaseService {

  //数据访问层对象
  private StudentDao studentDao;

	/**
	 * 增加student
	 */
	public void add(Student student) {
		this.studentDao.save(student);
	}

	/**
	 * 修改student
	 */
	public void update(Student student) {
		this.studentDao.update(student);
	}

	/**
	 * 删除student
	 */
	public void delete(Serializable id) {
		this.studentDao.removeById(id);
	}

	/**
	 * 根据ID查询student的详细信息
	 */
	public Student queryById(Serializable studentid) {
		return this.studentDao.get(studentid);
	}

	/**
	 * 获取所有的student对象
	 */
	public List<Student> queryAll() {
		return this.studentDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Student> queryByVO(Student student) {
		return this.studentDao.queryByObject(student);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Student student) {
		return this.studentDao.queryByObject(records, student);
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

}