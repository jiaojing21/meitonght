package com.itsv.platform.system.reset_pw.bo;

import java.util.List;

import com.itsv.platform.system.reset_pw.dao.PWUserDao;
import com.itsv.platform.system.reset_pw.vo.PWUser;
import com.itsv.gbp.core.admin.bo.LoggedService;

;

/**
 * ������û������ҵ�������<br>
 * 
 * ������Ҫ��ʾ�û����ɫ�ļ�����ѯ�������û�ʱ���Զ�������Ӧ�Ľ�ɫ��Ϣ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����04:14:24
 * @version 1.0
 */
public class PWUserService extends LoggedService {

	private PWUserDao userDao;

	public PWUser getUserInfo() {
		PWUser user = new PWUser();
		user.setId(this.getUserId());
		user.setRealName(this.getCurrentUser().getRealName());
		user.setPassword(this.getCurrentUser().getPassword());
		return user;
	}
	
	/**
	 * �޸��û�
	 */
	public void update(PWUser user) {
		this.userDao.update(user);

		// log
		writeLog("�޸��û�", "�޸��û�[" + user + "]");
	}

	/**
	 * �����û�ID��ѯ�û�����ϸ��Ϣ���ἶ����ѯ���û������еĽ�ɫ��
	 */
	public PWUser queryById(String userid) {
		return this.userDao.get(userid);
	}

	/**
	 * ���ݵ�λ���룬�������õ�λ�µ������û����ἶ����ѯ���û������еĽ�ɫ��
	 */
	public List<PWUser> queryByUnitId(String unitid) {
		return this.userDao.findBy("unitId", unitid);
	}

	/**
	 * ��ȡ���е��û�����
	 */
	public List<PWUser> queryAll() {
		return this.userDao.getAll();
	}

	public void setUserDao(PWUserDao userDao) {
		this.userDao = userDao;
	}

}
