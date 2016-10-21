package com.itsv.test.xsgl.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.test.xsgl.vo.Student;

/**
 * student对象的数据访问类
 * 
 * 
 * @author Agicus
 * @since 2008-07-11
 * @version 1.0
 */
public class StudentDao extends HibernatePagedDao<Student> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StudentDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Student) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Student) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Student> queryByObject(Student student) {
		return find(buildCriteriaByVO(student));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Student student) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(student));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Student student){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (student.getId() != null ) {
    	dc.add(Restrictions.eq("id", student.getId()));
    }	

    //年龄 
    if (student.getAge() != null) {
    	dc.add(Restrictions.eq("age", student.getAge()));
      }		

    //姓名 
    if (student.getName() != null && student.getName().length() > 0) {
    	dc.add(Restrictions.like("name", student.getName(), MatchMode.ANYWHERE));
    }	

    //昵称 
    if (student.getNick() != null && student.getNick().length() > 0) {
    	dc.add(Restrictions.like("nick", student.getNick(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Student student) throws OrmException {

  }

}
