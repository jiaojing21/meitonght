package com.itsv.gbp.core.group.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;

/**
 * gbp_grouprole对象的数据访问类
 * 
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_grouproleDao extends HibernatePagedDao<Gbp_grouprole> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Gbp_grouproleDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Gbp_grouprole) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Gbp_grouprole) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Gbp_grouprole> queryByObject(Gbp_grouprole gbp_grouprole) {
		return find(buildCriteriaByVO(gbp_grouprole));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Gbp_grouprole gbp_grouprole) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(gbp_grouprole));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Gbp_grouprole gbp_grouprole){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (gbp_grouprole.getId() != null ) {
    	dc.add(Restrictions.eq("id", gbp_grouprole.getId()));
    }	

    //groupid 
    if (gbp_grouprole.getGroupid() != null && gbp_grouprole.getGroupid().length() > 0) {
    	dc.add(Restrictions.like("groupid", gbp_grouprole.getGroupid(), MatchMode.ANYWHERE));
    }	

    //roleid 
    if (gbp_grouprole.getRoleid() != null && gbp_grouprole.getRoleid().length() > 0) {
    	dc.add(Restrictions.like("roleid", gbp_grouprole.getRoleid(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(Gbp_grouprole gbp_grouprole) throws OrmException {

  }

public void removeAllByGroupId(String groupId) {
	Gbp_grouprole gbp_grouprole = new Gbp_grouprole();
	gbp_grouprole.setGroupid(groupId);
	try {
		List <Gbp_grouprole> list = this.queryByObject(gbp_grouprole);
		if(list!=null){
			for(Gbp_grouprole gr:list){
				this.remove(gr);
			}
		}
		
	} catch (Exception e) {
		logger.error("removeByGroupId时出错", e);
		e.printStackTrace();
	};
}

}
