package com.itsv.annotation.subGoodGoods.dao;

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
import com.itsv.annotation.subGoodGoods.vo.SubGoodGoods;

/**
 * subgood_goods��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-04-25
 * @version 1.0
 */
 @Repository @Transactional
public class SubGoodGoodsDao extends HibernatePagedDao<SubGoodGoods> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SubGoodGoodsDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((SubGoodGoods) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((SubGoodGoods) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<SubGoodGoods> queryByObject(SubGoodGoods subGoodGoods) {
		return find(buildCriteriaByVO(subGoodGoods));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, SubGoodGoods subGoodGoods) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(subGoodGoods));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(SubGoodGoods subGoodGoods){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (subGoodGoods.getId() != null ) {
    	dc.add(Restrictions.eq("id", subGoodGoods.getId()));
    }	

    //subgoodid 
    if (subGoodGoods.getSubgoodId() != null && subGoodGoods.getSubgoodId().length() > 0) {
    	dc.add(Restrictions.like("subgoodId", subGoodGoods.getSubgoodId(), MatchMode.ANYWHERE));
    }	

    //goodsid 
    if (subGoodGoods.getGoodsId() != null && subGoodGoods.getGoodsId().length() > 0) {
    	dc.add(Restrictions.like("goodsId", subGoodGoods.getGoodsId(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (subGoodGoods.getRemark1() != null && subGoodGoods.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", subGoodGoods.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (subGoodGoods.getRemark2() != null && subGoodGoods.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", subGoodGoods.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(SubGoodGoods subGoodGoods) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
