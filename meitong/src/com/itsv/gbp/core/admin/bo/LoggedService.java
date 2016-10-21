package com.itsv.gbp.core.admin.bo;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.admin.dao.LogDao;
import com.itsv.gbp.core.admin.vo.AppLog;

/**
 * ����̨����Ӧ��ϵͳ�Ĺ���ҵ���߼��ࡣ<br>
 * �����¼�����Բ�����־��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����03:56:11
 * @version 1.0
 */
public class LoggedService extends BaseService {

	private LogDao logDao;

	private boolean enableLog = true; //�Ƿ�����¼��־���ܣ�Ĭ�Ͽ���

	/**
	 * ��¼ϵͳ��־
	 * 
	 * @param type ��־���
	 * @param message �������־��Ϣ
	 */
	protected void writeLog(String type, String message) {
		if (isEnableLog()) {
			AppLog appLog = new AppLog(getUserId(), type, message);
			this.logDao.save(appLog);
		}
	}

	/** ����Ϊget,set����*/
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
