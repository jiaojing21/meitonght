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
 * student��������ݷ�����
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
		//���л���У��
		check((Student) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Student) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Student> queryByObject(Student student) {
		return find(buildCriteriaByVO(student));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Student student) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

    //���� 
    if (student.getAge() != null) {
    	dc.add(Restrictions.eq("age", student.getAge()));
      }		

    //���� 
    if (student.getName() != null && student.getName().length() > 0) {
    	dc.add(Restrictions.like("name", student.getName(), MatchMode.ANYWHERE));
    }	

    //�ǳ� 
    if (student.getNick() != null && student.getNick().length() > 0) {
    	dc.add(Restrictions.like("nick", student.getNick(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Student student) throws OrmException {

  }

}
