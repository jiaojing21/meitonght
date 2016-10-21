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
 * 处理对用户对象的业务操作。<br>
 * 
 * 该类主要演示用户与角色的级联查询，检索用户时会自动填充其对应的角色信息。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午04:14:24
 * @version 1.0
 */
public class UserService extends LoggedService {

	private UserDao userDao;
	
	//按钮权限
    private FunctionRoleDao functionRoleDao;
    private FunctionDao functionDao;
    
    //组权限
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
	 * 增加用户
	 */
	public void add(User user) {

		/***
		 * ace <br>
		 * 新增用户的默认密码使用md5保持<br>
		 * 小写
		 */
		MD5 md = new MD5();
		String s ="123456";
		user.setPassword(md.getMD5ofStr(s).toLowerCase());
		
		user.setSortno(Long.valueOf(KeyGenerator.getInstance().getNextKey(user.getClass())));
		this.userDao.save(user);

		//log
		writeLog("增加用户", "新增用户[" + user + "]");
	}

	/**
	 * 修改用户
	 */
	public void update(User user) {
		this.userDao.update(user);

		//log
		writeLog("修改用户", "修改用户[" + user + "]");
	}

	/**
	 * 删除用户
	 */
	public void delete(Serializable userid) {
		this.userDao.removeById(userid);

		// log
		writeLog("修改用户", "删除用户[id=" + userid + "]");
	}

	/**
	 * 根据用户ID查询用户的详细信息。会级联查询到用户所具有的角色。
	 */
	public User queryById(String userid) {
		return this.userDao.get(userid);
	}

	/**
	 * 根据用户名查询用户的详细信息。会级联查询到用户所具有的角色。
	 */
	public User queryByName(String userName) {
		return this.userDao.findUniqueBy("userName", userName);
	}

	/**
	 * 根据单位编码，检索出该单位下的所有用户。会级联查询到用户所具有的角色。
	 */
	
	public List<User> queryByUnitId(String unitid) {
		//return this.userDao.findBy("unitId", unitid); //杨文彦注释
		User user = new User();
		user.setUnitId(unitid);
		return this.userDao.queryByObject(user);
	}

	/**
	 * 获取所有的用户对象
	 */
	public List<User> queryAll() {
		//return this.userDao.getAll();//杨文彦注释
		return this.userDao.queryByObject(new User());
	}

	//加载带菜单信息的用户对象 
	public SessionUser loadUserWithMenu(String id) {
		User user = this.userDao.get(id);
		
		
		/**
		 * ace8<br>
		 * 2008-09-06
		 * 增加从组中取得角色代码
		 */
		List<Role> trs = new ArrayList<Role>();
		try{

			//当前用户通过组获得的角色
			List <Gbp_grouprole> grolelist = this.gbp_groupDao.findGroupRolesByUserId(user.getId());
			for(Gbp_grouprole gr:grolelist){
				//
				Role r = this.roleDao.get(gr.getRoleid());
				if(!trs.contains(r)){
					trs.add(r);
				}
			}
			//但却用户所拥有的角色
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
		
		
		//取得menu
		SessionUser sessionUser = new SessionUser(user);
		
		sessionUser.setGrouproles(trs);
		
		//sessionUser.setRoles(trs);
		sessionUser.setMenus(this.userDao.findMenusByUser(trs));
		////sessionUser.setMenus(this.userDao.findMenusByUser(user));
		
		
        /**
         * adminh <br>
         * 20070716<br>
         * 增加用户信息中的功能权限列表
         */
        //先取得所有功能的id
        ////List<String> functionIds = this.functionRoleDao.findFunctionsByRoles(user.getRoles());
		List<String> functionIds = this.functionRoleDao.findFunctionsByRoles(trs);
        //由功能id取得所有code，并赋值到SessionUser
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
