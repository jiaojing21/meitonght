package com.itsv.annotation.refundGoods.dao;

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
import com.itsv.annotation.refundGoods.vo.RefundGoods;

/**
 * refund_goods��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class RefundGoodsDao extends HibernatePagedDao<RefundGoods> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RefundGoodsDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((RefundGoods) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((RefundGoods) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<RefundGoods> queryByObject(RefundGoods refundGoods) {
		return find(buildCriteriaByVO(refundGoods));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, RefundGoods refundGoods) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(refundGoods));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(RefundGoods refundGoods){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (refundGoods.getId() != null ) {
    	dc.add(Restrictions.eq("id", refundGoods.getId()));
    }	

    //order_number 
    if (refundGoods.getOrderNumber() != null && refundGoods.getOrderNumber().length() > 0) {
    	dc.add(Restrictions.like("orderNumber", refundGoods.getOrderNumber(), MatchMode.ANYWHERE));
    }	

    //goodsid 
    if (refundGoods.getGoodsId() != null && refundGoods.getGoodsId().length() > 0) {
    	dc.add(Restrictions.like("goodsId", refundGoods.getGoodsId(), MatchMode.ANYWHERE));
    }	

    //goods_number 
    if (refundGoods.getGoodsNumber() != null && refundGoods.getGoodsNumber().length() > 0) {
    	dc.add(Restrictions.like("goodsNumber", refundGoods.getGoodsNumber(), MatchMode.ANYWHERE));
    }	

    //goods_total 
    if (refundGoods.getGoodsTotal() != null && refundGoods.getGoodsTotal().length() > 0) {
    	dc.add(Restrictions.like("goodsTotal", refundGoods.getGoodsTotal(), MatchMode.ANYWHERE));
    }	

    //statue 
    if (refundGoods.getStatue() != null && refundGoods.getStatue().length() > 0) {
    	dc.add(Restrictions.like("statue", refundGoods.getStatue(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (refundGoods.getRemark1() != null && refundGoods.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", refundGoods.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (refundGoods.getRemark2() != null && refundGoods.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", refundGoods.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(RefundGoods refundGoods) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
