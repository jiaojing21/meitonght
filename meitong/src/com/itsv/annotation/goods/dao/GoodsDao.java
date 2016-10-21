package com.itsv.annotation.goods.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import com.itsv.annotation.goods.vo.Goods;
import com.itsv.annotation.spec.vo.Spec;
import com.itsv.annotation.subject.vo.Subject;

/**
 * goods对象的数据访问类
 * 
 * 
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
 @Repository @Transactional
public class GoodsDao extends HibernatePagedDao<Goods> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GoodsDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Goods) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Goods) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Goods> queryByObject(Goods goods) {
		return find(buildCriteriaByVO(goods));
	}


	@Resource(name = "sessionFactory")    
	private SessionFactory sessionFactory;
	public Goods querySubjectByPrice(String specid){
		String hql = "select * from goods g where g.specid = '"+specid+"' order by g.price+0 ASC LIMIT 0,1 ";
		List<Goods> goods = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(Goods.class).list();
		Goods good = null;
		if(goods.size()>0){
			good = goods.get(0);
		}
		return good;
	}
	public List<Goods> querySumSubjectByPrice(){
		String hql = "select * from goods g,(select specid ms,min(price) mp from goods group by specid) gm where specid=gm.ms and g.price=gm.mp;";
		List<Goods> list = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(Goods.class).list();
		return list;
	}
	public String querySumBySpecid(String specid) {
		String hql = "select sum(goods_number) from goods g where specid='"+specid+"'";
		String count = sessionFactory.getCurrentSession().createSQLQuery(hql).toString();
		return count;
	}
	public List<Goods> queryByType(String specid){
		String hql = "select * from goods g where specid='"+specid+"'";
		List<Goods> list = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(Goods.class).list();
		return list;
	}
	public List<Goods> queryBySpecName(String specname){
		List<Goods> list = new ArrayList<Goods>();
		String hql = "select * from spec where specname like '"+specname+"'";
		List<Spec> slist = sessionFactory.getCurrentSession().createSQLQuery(hql).addEntity(Spec.class).list();
		for(Spec s : slist){
			List<Goods>  glist = queryByType(s.getId());
			for(Goods g : glist){
				list.add(g);
			}
		}
		return list;
	}
  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Goods goods) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(goods));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Goods goods){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (goods.getId() != null ) {
    	dc.add(Restrictions.eq("id", goods.getId()));
    }	

    //specid 
    if (goods.getSpecId() != null && goods.getSpecId().length() > 0) {
    	dc.add(Restrictions.eq("specId", goods.getSpecId()));
    }	

    //name 
    if (goods.getName() != null && goods.getName().length() > 0) {
    	dc.add(Restrictions.like("name", goods.getName(), MatchMode.ANYWHERE));
    }	
    
    //flag 
    if (goods.getFlag() != null && goods.getFlag().length() > 0) {
    	dc.add(Restrictions.eq("flag", goods.getFlag()));
    }	
    
  //goodNo 
    if (goods.getGoodNo() != null && goods.getGoodNo().length() > 0) {
    	dc.add(Restrictions.eq("goodNo", goods.getGoodNo()));
    }

    //goods_number 
    if (goods.getGoodsNumber() != null && goods.getGoodsNumber().length() > 0) {
    	dc.add(Restrictions.like("goodsNumber", goods.getGoodsNumber(), MatchMode.ANYWHERE));
    }	

    //price 
    if (goods.getPrice() != null && goods.getPrice().length() > 0) {
    	dc.add(Restrictions.like("price", goods.getPrice(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (goods.getRemark1() != null && goods.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", goods.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (goods.getRemark2() != null && goods.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", goods.getRemark2(), MatchMode.ANYWHERE));
    }	
    //state 
    if (goods.getState() != null && goods.getState().length() > 0) {
    	dc.add(Restrictions.eq("state", goods.getState()));
    }	
    return dc;  
  }

  //数据校验
  private void check(Goods goods) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
