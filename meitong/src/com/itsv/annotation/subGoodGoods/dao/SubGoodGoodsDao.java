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
 * subgood_goods对象的数据访问类
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
		//进行基本校验
		check((SubGoodGoods) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((SubGoodGoods) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<SubGoodGoods> queryByObject(SubGoodGoods subGoodGoods) {
		return find(buildCriteriaByVO(subGoodGoods));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, SubGoodGoods subGoodGoods) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
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

  //数据校验
  private void check(SubGoodGoods subGoodGoods) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
