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
 * ������Ϣ��������ݷ�����
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
		//���л���У��
		check((Itsv_message_receive) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Itsv_message_receive) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Itsv_message_receive> queryByObject(Itsv_message_receive itsv_message_receive) {
		return find(buildCriteriaByVO(itsv_message_receive));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Itsv_message_receive itsv_message_receive) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

    //��Ϣ���ݱ�� 
    if (itsv_message_receive.getMsg_id() != null) {
    	dc.add(Restrictions.eq("msg_id", itsv_message_receive.getMsg_id()));
      }		

    //������Ա��� 
    if (itsv_message_receive.getReceiver_id() != null) {
    	dc.add(Restrictions.eq("receiver_id", itsv_message_receive.getReceiver_id()));
      }		

    //����״̬ 
    if (itsv_message_receive.getReceive_status() != null) {
    	dc.add(Restrictions.eq("receive_status", itsv_message_receive.getReceive_status()));
      }		

    //����ʱ�� 
    if (itsv_message_receive.getSender_name() != null) {
    	dc.add(Restrictions.like("sender_name", itsv_message_receive.getSender_name(),
				MatchMode.ANYWHERE));
      }		

    //����ʱ�� 
    if (itsv_message_receive.getReceive_time() != null && itsv_message_receive.getReceive_time().length() > 0) {
    	dc.add(Restrictions.like("receive_time", itsv_message_receive.getReceive_time(),
				MatchMode.ANYWHERE));
      }		

    //���鿴ʱ�� 
    if (itsv_message_receive.getLast_view_time() != null && itsv_message_receive.getLast_view_time().length() > 0) {
    	dc.add(Restrictions.like("last_view_time", itsv_message_receive.getLast_view_time(),
				MatchMode.ANYWHERE));
      }		

    //�鿴���� 
    if (itsv_message_receive.getView_times() != null) {
    	dc.add(Restrictions.eq("view_times", itsv_message_receive.getView_times()));
      }		

    dc.addOrder(Order.desc("receive_time"));
    
    return dc;  
  }

  //����У��
  private void check(Itsv_message_receive itsv_message_receive) throws OrmException {

  }

}
