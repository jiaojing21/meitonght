package com.itsv.platform.common.messageDetail.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.common.messageDetail.vo.Itsv_message_receive;

/**
 * 接收消息对象的数据访问类
 * 
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_message_receiveDao extends HibernatePagedDao<Itsv_message_receive> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Itsv_message_receiveDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Itsv_message_receive) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Itsv_message_receive) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Itsv_message_receive> queryByObject(Itsv_message_receive itsv_message_receive) {
		return find(buildCriteriaByVO(itsv_message_receive));
	}

  /**
   * 分页查询。<br>
   * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
   */
  public IPagedList queryByObject(IPagedList records, Itsv_message_receive itsv_message_receive) {
    //如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(itsv_message_receive));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Itsv_message_receive itsv_message_receive){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (itsv_message_receive.getId() != null ) {
    	dc.add(Restrictions.eq("id", itsv_message_receive.getId()));
    }	

    //消息内容编号 
    if (itsv_message_receive.getMsg_id() != null) {
    	dc.add(Restrictions.eq("msg_id", itsv_message_receive.getMsg_id()));
      }		

    //接收人员编号 
    if (itsv_message_receive.getReceiver_id() != null) {
    	dc.add(Restrictions.eq("receiver_id", itsv_message_receive.getReceiver_id()));
      }		

    //接收状态 
    if (itsv_message_receive.getReceive_status() != null) {
    	dc.add(Restrictions.eq("receive_status", itsv_message_receive.getReceive_status()));
      }		

    //接收时间 
    if (itsv_message_receive.getSender_name() != null) {
    	dc.add(Restrictions.like("sender_name", itsv_message_receive.getSender_name(),
				MatchMode.ANYWHERE));
      }		

    //接收时间 
    if (itsv_message_receive.getReceive_time() != null && itsv_message_receive.getReceive_time().length() > 0) {
    	dc.add(Restrictions.like("receive_time", itsv_message_receive.getReceive_time(),
				MatchMode.ANYWHERE));
      }		

    //最后查看时间 
    if (itsv_message_receive.getLast_view_time() != null && itsv_message_receive.getLast_view_time().length() > 0) {
    	dc.add(Restrictions.like("last_view_time", itsv_message_receive.getLast_view_time(),
				MatchMode.ANYWHERE));
      }		

    //查看次数 
    if (itsv_message_receive.getView_times() != null) {
    	dc.add(Restrictions.eq("view_times", itsv_message_receive.getView_times()));
      }		

    dc.addOrder(Order.desc("receive_time"));
    
    return dc;  
  }

  //数据校验
  private void check(Itsv_message_receive itsv_message_receive) throws OrmException {

  }

}
