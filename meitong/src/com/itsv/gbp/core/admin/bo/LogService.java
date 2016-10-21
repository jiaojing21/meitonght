package com.itsv.gbp.core.admin.bo;

import com.itsv.gbp.core.admin.dao.LogDao;
import com.itsv.gbp.core.admin.vo.AppLog;
import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * log业务逻辑类。<br>
 * 
 * 只提供分页查询功能。
 *  
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午10:15:31
 * @version 1.0
 */
public class LogService {

	private LogDao logDao;

	//分页查询
	public IPagedList queryByVO(IPagedList records, AppLog appLog) {
		return this.logDao.queryByObject(records, appLog);
	}

	/** get,set */
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

}
