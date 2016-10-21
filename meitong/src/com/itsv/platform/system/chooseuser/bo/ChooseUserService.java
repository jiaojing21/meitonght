package com.itsv.platform.system.chooseuser.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.gbp.core.admin.bo.LoggedService;
import com.itsv.platform.system.chooseuser.dao.ChooseUserDao;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;

/**
 * ������û������ҵ�������<br>
 * 
 * ������Ҫ��ʾ�û����ɫ�ļ�����ѯ�������û�ʱ���Զ�������Ӧ�Ľ�ɫ��Ϣ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����04:14:24
 * @version 1.0
 */
public class ChooseUserService extends LoggedService {

	private ChooseUserDao userDao;

	/**
	 * �����û�
	 */
	public void add(ChooseUser user) {

		this.userDao.save(user);

		//log
		writeLog("�����û�", "�����û�[" + user + "]");
	}

	/**
	 * �޸��û�
	 */
	public void update(ChooseUser user) {
		this.userDao.update(user);

		//log
		writeLog("�޸��û�", "�޸��û�[" + user + "]");
	}

	/**
	 * ɾ���û�
	 */
	public void delete(Serializable userid) {
		this.userDao.removeById(userid);

		// log
		writeLog("�޸��û�", "ɾ���û�[id=" + userid + "]");
	}

	/**
	 * �����û�ID��ѯ�û�����ϸ��Ϣ���ἶ����ѯ���û������еĽ�ɫ��
	 */
	public ChooseUser queryById(String userid) {
		return this.userDao.get(userid);
	}

	/**
	 * �����û�����ѯ�û�����ϸ��Ϣ���ἶ����ѯ���û������еĽ�ɫ��
	 */
	public ChooseUser queryByName(String userName) {
		return this.userDao.findUniqueBy("userName", userName);
	}

	/**
	 * ���ݵ�λ���룬�������õ�λ�µ������û����ἶ����ѯ���û������еĽ�ɫ��
	 */
	public List<ChooseUser> queryByUnitId(String unitid) {
		//return this.userDao.findBy("unitId", unitid);
		ChooseUser user = new ChooseUser();
		user.setUnitId(unitid);
		return this.userDao.queryByObject(user);
	}

	/**
	 * ��ȡ���е��û�����
	 */
	public List<ChooseUser> queryAll() {
		//return this.userDao.getAll();
		return this.userDao.queryByObject(new ChooseUser());
	}


	public void setUserDao(ChooseUserDao userDao) {
		this.userDao = userDao;
	}

}
