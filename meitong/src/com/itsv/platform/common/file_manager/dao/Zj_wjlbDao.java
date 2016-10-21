package com.itsv.platform.common.file_manager.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.common.fileMgr.vo.ZjWjlb;

/**
 * 存储类别信息对象的数据访问类
 * 
 * 
 * @author milu
 * @since 2007-07-17
 * @version 1.0
 */
public class Zj_wjlbDao extends HibernatePagedDao<ZjWjlb> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Zj_wjlbDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((ZjWjlb) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((ZjWjlb) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<ZjWjlb> queryByObject(ZjWjlb ZjWjlb) {
		return find(buildCriteriaByVO(ZjWjlb));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, ZjWjlb ZjWjlb) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(ZjWjlb));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(ZjWjlb ZjWjlb){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (ZjWjlb.getId() != null ) {
    	dc.add(Restrictions.eq("id", ZjWjlb.getId()));
    }	

    //存储类别名称 
    if (ZjWjlb.getCclbmc() != null && ZjWjlb.getCclbmc().length() > 0) {
    	dc.add(Restrictions.like("cclbmc", ZjWjlb.getCclbmc(), MatchMode.ANYWHERE));
    }	

    //存储类别版本 
    if (ZjWjlb.getLbmcbb() != null) {
    	dc.add(Restrictions.eq("lbmcbb", ZjWjlb.getLbmcbb()));
      }		

    //存储根目录 
    if (ZjWjlb.getCcgml() != null && ZjWjlb.getCcgml().length() > 0) {
    	dc.add(Restrictions.like("ccgml", ZjWjlb.getCcgml(), MatchMode.ANYWHERE));
    }	

    //二级目录规则 
    if (ZjWjlb.getEjmlgz() != null && ZjWjlb.getEjmlgz().length() > 0) {
    	dc.add(Restrictions.like("ejmlgz", ZjWjlb.getEjmlgz(), MatchMode.ANYWHERE));
    }	

    //状态 
    if (ZjWjlb.getZt() != null && ZjWjlb.getZt().length() > 0) {
    	dc.add(Restrictions.like("zt", ZjWjlb.getZt(), MatchMode.ANYWHERE));
    }	

    //备注 
    if (ZjWjlb.getBz() != null && ZjWjlb.getBz().length() > 0) {
    	dc.add(Restrictions.like("bz", ZjWjlb.getBz(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //数据校验
  private void check(ZjWjlb ZjWjlb) throws OrmException {

  }

}
