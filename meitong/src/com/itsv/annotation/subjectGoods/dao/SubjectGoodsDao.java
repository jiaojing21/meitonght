package com.itsv.annotation.subjectGoods.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import com.itsv.annotation.subjectGoods.vo.SubjectGoods;

/**
 * subject_goods��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class SubjectGoodsDao extends HibernatePagedDao<SubjectGoods> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SubjectGoodsDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((SubjectGoods) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((SubjectGoods) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<SubjectGoods> queryByObject(SubjectGoods subjectGoods) {
		return find(buildCriteriaByVO(subjectGoods));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, SubjectGoods subjectGoods) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(subjectGoods));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(SubjectGoods subjectGoods){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (subjectGoods.getId() != null ) {
    	dc.add(Restrictions.eq("id", subjectGoods.getId()));
    }	

    //subjectid 
    if (subjectGoods.getSubjectId() != null && subjectGoods.getSubjectId().length() > 0) {
    	dc.add(Restrictions.like("subjectId", subjectGoods.getSubjectId(), MatchMode.ANYWHERE));
    }	

    //goodsid 
    if (subjectGoods.getGoodsId() != null && subjectGoods.getGoodsId().length() > 0) {
    	dc.add(Restrictions.like("goodsId", subjectGoods.getGoodsId(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (subjectGoods.getRemark1() != null && subjectGoods.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", subjectGoods.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (subjectGoods.getRemark2() != null && subjectGoods.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", subjectGoods.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(SubjectGoods subjectGoods) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
