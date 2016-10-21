package com.itsv.gbp.core.admin.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itsv.gbp.core.admin.dao.RoleDao;
import com.itsv.gbp.core.admin.dao.UserDao;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.SessionUser;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.group.dao.Gbp_groupDao;
import com.itsv.gbp.core.group.dao.Gbp_grouproleDao;
import com.itsv.gbp.core.group.dao.Gbp_groupuserDao;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.util.MD5;
import com.itsv.platform.KeyGenerator;
import com.itsv.platform.role.dao.FunctionDao;
import com.itsv.platform.role.dao.FunctionRoleDao;

/**
 * ������û������ҵ�������<br>
 * 
 * ������Ҫ��ʾ�û����ɫ�ļ�����ѯ�������û�ʱ���Զ�������Ӧ�Ľ�ɫ��Ϣ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����04:14:24
 * @version 1.0
 */
public class UserService extends LoggedService {

	private UserDao userDao;
	
	//��ťȨ��
    private FunctionRoleDao functionRoleDao;
    private FunctionDao functionDao;
    
    //��Ȩ��
	private Gbp_groupuserDao gbp_groupuserDao;
	private Gbp_grouproleDao gbp_grouproleDao;
	private Gbp_groupDao gbp_groupDao;	
	private RoleDao roleDao;
    
	
	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setFunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }
	
    public void setFunctionRoleDao(FunctionRoleDao functionRoleDao) {
        this.functionRoleDao = functionRoleDao;
    }


    public Gbp_groupDao getGbp_groupDao() {
		return gbp_groupDao;
	}

	public void setGbp_groupDao(Gbp_groupDao gbp_groupDao) {
		this.gbp_groupDao = gbp_groupDao;
	}

	public Gbp_grouproleDao getGbp_grouproleDao() {
		return gbp_grouproleDao;
	}

	public void setGbp_grouproleDao(Gbp_grouproleDao gbp_grouproleDao) {
		this.gbp_grouproleDao = gbp_grouproleDao;
	}

	public Gbp_groupuserDao getGbp_groupuserDao() {
		return gbp_groupuserDao;
	}

	public void setGbp_groupuserDao(Gbp_groupuserDao gbp_groupuserDao) {
		this.gbp_groupuserDao = gbp_groupuserDao;
	}

	public void getUnit(User user) {
        user.setUnit(this.userDao.getUnit(user));
    }

    /**
	 * �����û�
	 */
	public void add(User user) {

		/***
		 * ace <br>
		 * �����û���Ĭ������ʹ��md5����<br>
		 * Сд
		 */
		MD5 md = new MD5();
		String s ="123456";
		user.setPassword(md.getMD5ofStr(s).toLowerCase());
		
		user.setSortno(Long.valueOf(KeyGenerator.getInstance().getNextKey(user.getClass())));
		this.userDao.save(user);

		//log
		writeLog("�����û�", "�����û�[" + user + "]");
	}

	/**
	 * �޸��û�
	 */
	public void update(User user) {
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
	public User queryById(String userid) {
		return this.userDao.get(userid);
	}

	/**
	 * �����û�����ѯ�û�����ϸ��Ϣ���ἶ����ѯ���û������еĽ�ɫ��
	 */
	public User queryByName(String userName) {
		return this.userDao.findUniqueBy("userName", userName);
	}

	/**
	 * ���ݵ�λ���룬�������õ�λ�µ������û����ἶ����ѯ���û������еĽ�ɫ��
	 */
	
	public List<User> queryByUnitId(String unitid) {
		//return this.userDao.findBy("unitId", unitid); //������ע��
		User user = new User();
		user.setUnitId(unitid);
		return this.userDao.queryByObject(user);
	}

	/**
	 * ��ȡ���е��û�����
	 */
	public List<User> queryAll() {
		//return this.userDao.getAll();//������ע��
		return this.userDao.queryByObject(new User());
	}

	//���ش��˵���Ϣ���û����� 
	public SessionUser loadUserWithMenu(String id) {
		User user = this.userDao.get(id);
		
		
		/**
		 * ace8<br>
		 * 2008-09-06
		 * ���Ӵ�����ȡ�ý�ɫ����
		 */
		List<Role> trs = new ArrayList<Role>();
		try{

			//��ǰ�û�ͨ�����õĽ�ɫ
			List <Gbp_grouprole> grolelist = this.gbp_groupDao.findGroupRolesByUserId(user.getId());
			for(Gbp_grouprole gr:grolelist){
				//
				Role r = this.roleDao.get(gr.getRoleid());
				if(!trs.contains(r)){
					trs.add(r);
				}
			}
			//��ȴ�û���ӵ�еĽ�ɫ
			List<Role> curs = user.getRoles();
			for(Role r:curs){
				if(!trs.contains(r)){
					trs.add(r);
				}
			}
			//System.out.println("=======================================");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//ȡ��menu
		SessionUser sessionUser = new SessionUser(user);
		
		sessionUser.setGrouproles(trs);
		
		//sessionUser.setRoles(trs);
		sessionUser.setMenus(this.userDao.findMenusByUser(trs));
		////sessionUser.setMenus(this.userDao.findMenusByUser(user));
		
		
        /**
         * adminh <br>
         * 20070716<br>
         * �����û���Ϣ�еĹ���Ȩ���б�
         */
        //��ȡ�����й��ܵ�id
        ////List<String> functionIds = this.functionRoleDao.findFunctionsByRoles(user.getRoles());
		List<String> functionIds = this.functionRoleDao.findFunctionsByRoles(trs);
        //�ɹ���idȡ������code������ֵ��SessionUser
        sessionUser.setFunctions(this.functionDao.findFunctionCodeByIds(functionIds));
        
		return sessionUser;
	}

	private void testPrintRoles(User user) {
		List<Role> rlist = user.getRoles();
		
		//
		for(Role r : rlist){
			System.out.println("@@@@@@@@@@>"+r.getId());
			System.out.println("@@@@@@@@@@>"+r.getName());
		}
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
