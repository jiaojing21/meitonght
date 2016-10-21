package com.itsv.gbp.core.admin.bo;

import com.itsv.gbp.core.admin.dao.LogDao;
import com.itsv.gbp.core.admin.vo.AppLog;
import com.itsv.gbp.core.orm.paged.IPagedList;

/**
 * logҵ���߼��ࡣ<br>
 * 
 * ֻ�ṩ��ҳ��ѯ���ܡ�
 *  
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����10:15:31
 * @version 1.0
 */
public class LogService {

	private LogDao logDao;

	//��ҳ��ѯ
	public IPagedList queryByVO(IPagedList records, AppLog appLog) {
		return this.logDao.queryByObject(records, appLog);
	}

	/** get,set */
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

}
