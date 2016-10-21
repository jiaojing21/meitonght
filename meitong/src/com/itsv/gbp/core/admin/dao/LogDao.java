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
 * 日志数据访问类。<br>
 * 可以设置日志类型忽略列表，凡是存在于该列表的类型都不记录。<br>
 * 
 * 该类演示了使用DetachedCriteria来构造复杂条件，进行查询检索。<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:23:08
 * @version 1.0
 */
public class LogDao extends HibernatePagedDao<AppLog> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogDao.class);

	//哪些日志类型不记录
	private List ignoreTypes;

	@Override
	public void save(Object o) throws OrmException {
		AppLog appLog = (AppLog) o;
		if (needRecord(appLog.getType())) {
			super.save(o);
		} else {

			if (logger.isDebugEnabled()) {
				logger.debug("日志[" + appLog + "]被忽略记录");
			}
		}
	}

	/**
	 * 分页查询。<br>
	 * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
	 * 
	 * @param records
	 * @param appLog
	 * @return
	 */
	public IPagedList queryByObject(IPagedList records, AppLog appLog) {
		//如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
		if (records.getTotalNum() == -1) {
			DetachedCriteria dc = createDetachedCriteria();
			//日志类型条件  
			if (appLog.getType() != null && appLog.getType().length() > 0) {
				dc.add(Restrictions.like("type", appLog.getType(), MatchMode.ANYWHERE));
			}
			//起始日期
			if (appLog.getOthers().getDateParam1() != null) {
				dc.add(Restrictions.ge("time", appLog.getOthers().getDateParam1()));
			}
			//截至日期
			if (appLog.getOthers().getDateParam2() != null) {
				dc.add(Restrictions.le("time", appLog.getOthers().getDateParam2()));
			}		    dc.addOrder(Order.desc("time"));
			records.setParam(dc);
		}

		return pagedQuery(records, (DetachedCriteria) records.getParam());
	}

	/**
	 * 判断该类日志是否需要记录。如果日志类型在忽略列表里，则返回false，不记录
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
