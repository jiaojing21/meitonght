package com.itsv.gbp.core.admin.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.admin.vo.AppLog;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * ��־���ݷ����ࡣ<br>
 * ����������־���ͺ����б����Ǵ����ڸ��б�����Ͷ�����¼��<br>
 * 
 * ������ʾ��ʹ��DetachedCriteria�����츴�����������в�ѯ������<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����03:23:08
 * @version 1.0
 */
public class LogDao extends HibernatePagedDao<AppLog> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogDao.class);

	//��Щ��־���Ͳ���¼
	private List ignoreTypes;

	@Override
	public void save(Object o) throws OrmException {
		AppLog appLog = (AppLog) o;
		if (needRecord(appLog.getType())) {
			super.save(o);
		} else {

			if (logger.isDebugEnabled()) {
				logger.debug("��־[" + appLog + "]�����Լ�¼");
			}
		}
	}

	/**
	 * ��ҳ��ѯ��<br>
	 * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
	 * 
	 * @param records
	 * @param appLog
	 * @return
	 */
	public IPagedList queryByObject(IPagedList records, AppLog appLog) {
		//���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
		if (records.getTotalNum() == -1) {
			DetachedCriteria dc = createDetachedCriteria();
			//��־��������  
			if (appLog.getType() != null && appLog.getType().length() > 0) {
				dc.add(Restrictions.like("type", appLog.getType(), MatchMode.ANYWHERE));
			}
			//��ʼ����
			if (appLog.getOthers().getDateParam1() != null) {
				dc.add(Restrictions.ge("time", appLog.getOthers().getDateParam1()));
			}
			//��������
			if (appLog.getOthers().getDateParam2() != null) {
				dc.add(Restrictions.le("time", appLog.getOthers().getDateParam2()));
			}		    dc.addOrder(Order.desc("time"));
			records.setParam(dc);
		}

		return pagedQuery(records, (DetachedCriteria) records.getParam());
	}

	/**
	 * �жϸ�����־�Ƿ���Ҫ��¼�������־�����ں����б���򷵻�false������¼
	 *  
	 */
	private boolean needRecord(String logType) {
		if (getIgnoreTypes() == null) {
			return true;
		}
		for (Iterator iter = getIgnoreTypes().iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (logType.equals(element)) {
				return false;
			}
		}

		return true;
	}

	public List getIgnoreTypes() {
		return ignoreTypes;
	}

	public void setIgnoreTypes(List ignoreTypes) {
		this.ignoreTypes = ignoreTypes;
	}

}
