package com.itsv.gbp.core.admin.bo;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.admin.dao.LogDao;
import com.itsv.gbp.core.admin.vo.AppLog;

/**
 * 本后台管理应用系统的公共业务逻辑类。<br>
 * 允许记录事务性操作日志。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:56:11
 * @version 1.0
 */
public class LoggedService extends BaseService {

	private LogDao logDao;

	private boolean enableLog = true; //是否开启记录日志功能，默认开启

	/**
	 * 记录系统日志
	 * 
	 * @param type 日志类别
	 * @param message 具体的日志信息
	 */
	protected void writeLog(String type, String message) {
		if (isEnableLog()) {
			AppLog appLog = new AppLog(getUserId(), type, message);
			this.logDao.save(appLog);
		}
	}

	/** 以下为get,set方法*/
	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	public boolean isEnableLog() {
		return enableLog;
	}

	public void setEnableLog(boolean enableLog) {
		this.enableLog = enableLog;
	}
}
