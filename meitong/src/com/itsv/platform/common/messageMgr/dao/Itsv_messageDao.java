package com.itsv.platform.common.messageMgr.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.common.messageMgr.vo.Itsv_message;

/**
 * ��Ϣ��������ݷ�����
 * 
 * 
 * @author milu
 * @since 2007-07-21
 * @version 1.0
 */
public class Itsv_messageDao extends HibernatePagedDao<Itsv_message> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Itsv_messageDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Itsv_message) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Itsv_message) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Itsv_message> queryByObject(Itsv_message itsv_message) {
		return find(buildCriteriaByVO(itsv_message));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Itsv_message itsv_message) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(itsv_message));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Itsv_message itsv_message){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (itsv_message.getId() != null ) {
    	dc.add(Restrictions.eq("id", itsv_message.getId()));
    }	

    //����ʱ�� 
    if (itsv_message.getCreate_time() != null && itsv_message.getCreate_time().length() > 0) {
    	dc.add(Restrictions.like("create_time", itsv_message.getCreate_time(),
				MatchMode.ANYWHERE));
      }		

    //����ʱ�� 
    if (itsv_message.getSent_time() != null && itsv_message.getSent_time().length() > 0) {
    	dc.add(Restrictions.like("sent_time", itsv_message.getSent_time(),
				MatchMode.ANYWHERE));
      }		

    //��Ϣ״̬ 
    if (itsv_message.getMsg_status() != null) {
    	dc.add(Restrictions.eq("msg_status", itsv_message.getMsg_status()));
      }		

    //��Ϣ���� 
    if (itsv_message.getMsg_type() != null) {
    	dc.add(Restrictions.eq("msg_type", itsv_message.getMsg_type()));
      }		

    //������Ϣ��� 
    if (itsv_message.getParent_msg_id() != null) {
    	dc.add(Restrictions.eq("parent_msg_id", itsv_message.getParent_msg_id()));
      }		

    //��Ϣ���� 
    if (itsv_message.getMsg_title() != null && itsv_message.getMsg_title().length() > 0) {
    	dc.add(Restrictions.like("msg_title", itsv_message.getMsg_title(), MatchMode.ANYWHERE));
    }	

    //��Ϣ���� 
    if (itsv_message.getMsg_content() != null && itsv_message.getMsg_content().length() > 0) {
    	dc.add(Restrictions.like("msg_content", itsv_message.getMsg_content(), MatchMode.ANYWHERE));
    }	

    //��������б� 
    if (itsv_message.getFile_ids() != null && itsv_message.getFile_ids().length() > 0) {
    	dc.add(Restrictions.like("file_ids", itsv_message.getFile_ids(), MatchMode.ANYWHERE));
    }	

    //���������б� 
    if (itsv_message.getFile_names() != null && itsv_message.getFile_names().length() > 0) {
    	dc.add(Restrictions.like("file_names", itsv_message.getFile_names(), MatchMode.ANYWHERE));
    }	

    //ID
    if (itsv_message.getUserid() != null ) {
    	dc.add(Restrictions.eq("userid", itsv_message.getUserid()));
    }	
    
    dc.addOrder(Order.desc("create_time"));

    return dc;  
  }

  //����У��
  private void check(Itsv_message itsv_message) throws OrmException {

  }

}
