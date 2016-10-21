package com.itsv.annotation.message.dao;

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
import com.itsv.annotation.message.vo.Message;

/**
 * message��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
 @Repository @Transactional
public class MessageDao extends HibernatePagedDao<Message> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessageDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Message) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Message) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Message> queryByObject(Message message) {
		return find(buildCriteriaByVO(message));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, Message message) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(message));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(Message message){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (message.getId() != null ) {
    	dc.add(Restrictions.eq("id", message.getId()));
    }	

    //title 
    if (message.getTitle() != null && message.getTitle().length() > 0) {
    	dc.add(Restrictions.like("title", message.getTitle(), MatchMode.ANYWHERE));
    }	

    //content 
    if (message.getContent() != null && message.getContent().length() > 0) {
    	dc.add(Restrictions.like("content", message.getContent(), MatchMode.ANYWHERE));
    }	

    //createtime 
    if (message.getCreatetime() != null) {
    	dc.add(Restrictions.eq("createtime", message.getCreatetime()));
      }		

    //sender 
    if (message.getSender() != null && message.getSender().length() > 0) {
    	dc.add(Restrictions.like("sender", message.getSender(), MatchMode.ANYWHERE));
    }	

    //recevier 
    if (message.getRecevier() != null && message.getRecevier().length() > 0) {
    	dc.add(Restrictions.like("recevier", message.getRecevier(), MatchMode.ANYWHERE));
    }	

    //type 
    if (message.getType() != null && message.getType().length() > 0) {
    	dc.add(Restrictions.like("type", message.getType(), MatchMode.ANYWHERE));
    }	

    //remark_1 
    if (message.getRemark1() != null && message.getRemark1().length() > 0) {
    	dc.add(Restrictions.like("remark1", message.getRemark1(), MatchMode.ANYWHERE));
    }	

    //remark_2 
    if (message.getRemark2() != null && message.getRemark2().length() > 0) {
    	dc.add(Restrictions.like("remark2", message.getRemark2(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(Message message) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
