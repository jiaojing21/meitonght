package com.itsv.platform.system.chooseuser.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
import com.itsv.platform.system.chooseunit.bo.ChooseUnitService;
import com.itsv.platform.system.chooseuser.bo.ChooseUserService;
import com.itsv.platform.system.chooseuser.vo.UserRole;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;
import com.itsv.platform.system.chooseuser.bo.UserRoleService;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;

/**
 * 说明：增加，修改，删除用户的前端处理类
 * 
 * @author sgb 2007-07-07
 */
public class ChooseUserController extends BaseCURDController<ChooseUser> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(ChooseUserController.class);

	private ChooseUserService userService; // 逻辑层对象

	private UserRoleService roleService; // 角色

	private ChooseUnitService unitService; // 单位逻辑层对象
	
	private Itsv_dictionaryService dictionaryService; // 字典对象

	private String treeView; // 树状列表的视图

	private String listView; // 用户列表显示视图

	// sgb add 070629
	private String showCheckUser; // 选择多选单位用户的页面

	private String showRadioUser; // 选择单选单位用户的页面

	String USER_LIST = "userList";

	// sgb end

	/**
	 * 在页面左侧显示单位树。与单位管理不同的是，根节点可点击
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// 设置树状显式具体列表数据。从缓存里取数
		// mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));
		mnv.addObject(WebConfig.DATA_NAME, this.unitService.queryAll());

		return mnv;
	}

	/**
	 * 用户列表显示页面。显示指定单位下的所有用户
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getListView());

		String unitid = "";
		try {
			unitid = ServletRequestUtils.getStringParameter(request, "unitId");
			if (unitid !=null) {
				unitid = ServletRequestUtils.getStringParameter(request, "p_unitId");
			}			
			
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 如果指定了单位，则显示当前单位的用户。否则显示全部用户
		if (unitid !=null) {
			mnv.addObject("unitId", unitid);
			mnv.addObject(WebConfig.DATA_NAME, this.userService
					.queryByUnitId(unitid));
		} else {
			mnv.addObject(WebConfig.DATA_NAME, this.userService.queryAll());
		}
		return mnv;
	}

	// 创建菜单对象的树配置信息
	private TreeConfig getUnitTreeConfig() {
		// 设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta
				.setTitle("<a href='javascript:parent.hitTree(0,0,0,0,0);'>用户列表</a>");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** 以下为set,get方法 */
	public void setUserService(ChooseUserService userService) {
		this.userService = userService;
	}

	/** 以下为set,get方法 */
	public void setRoleService(
			com.itsv.platform.system.chooseuser.bo.UserRoleService roleService) {
		this.roleService = roleService;
	}

	/** 以下为set,get方法 */
	public void setUnitService(ChooseUnitService unitService) {
		this.unitService = unitService;
	}

	public String getTreeView() {
		return treeView;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	/**
	 * sgb 070703 add 在页面显示多选单位\角色\所有用户树
	 */
	public ModelAndView showCheckUnitUser(HttpServletRequest request,
			HttpServletResponse response) {

		String type = request.getParameter("type");// 标识是多选还是单选
		ModelAndView mnv = null;
		if (type != null && type.equals("radio"))
			mnv = new ModelAndView(getShowRadioUser());
		else
			mnv = new ModelAndView(getShowCheckUser());


		
		
		
		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());

		// 获得单位列表
		java.util.ArrayList unitList = (java.util.ArrayList) this.unitService
				.queryAll();

		// 获得人员列表
		java.util.ArrayList userList = (java.util.ArrayList) this.userService
				.queryAll();

		ChooseUser user = null; // 用户对象
		ChooseUnit unit = null; // 单位对象
		ChooseUnit unitObj = null;// 单位对象

		String unit_id = "";// 单位id
		String code = "";// 单位编码
		String tmpcode = "";// 组合人员时生成单位编码
		int idClass = 0;// 单位层级
		String userunit_id = "";// 用户表中的单位id

		java.util.ArrayList unitUserList = new java.util.ArrayList();
		
		// 循环单位列表,存储编码,以便下边不要生成重复编码
		java.util.HashMap map = new java.util.HashMap();
		for (int i = 0; i < unitList.size(); i++) {
			unit = (com.itsv.platform.system.chooseunit.vo.ChooseUnit) unitList
					.get(i);// 获得单位对象
			code = unit.getCode();// 获得单位编码
			map.put(code, code);
		}

		// 循环单位列表
		for (int i = 0; i < unitList.size(); i++) {
			int unitStart = 0;// 组合单位编码
			unit = (com.itsv.platform.system.chooseunit.vo.ChooseUnit) unitList
					.get(i);// 获得单位对象
			unit.setLeaf(false);
			unitUserList.add(unit);
			
			unit_id = unit.getId();// 获得单位id
			code = unit.getCode();// 获得单位编码
			idClass = unit.getIdClass();
			
			// 循环人员列表
			for (int n = 0; n < userList.size(); n++) {
				user = (com.itsv.platform.system.chooseuser.vo.ChooseUser) userList
						.get(n);// 获得用户对象
				userunit_id = user.getUnitId();// 获得用户对应的单位id
				if (unit_id.equals(userunit_id))// 如果当前用户是否属于当前单位
				{

					unitObj = new ChooseUnit();// 实例化单位对象
					unitObj.setId(user.getId());// id
					// 一级编码不会大于999
					while (unitStart < 999) {
						unitStart++;
						// 组合编码
						tmpcode = String.valueOf(code)
						+ "000".substring(3 - String.valueOf(unitStart)
								.length() - 1)
						+ String.valueOf(unitStart);
						if (!map.containsKey(tmpcode))
							break;
					}
					unitObj.setCode(tmpcode);// 编码
					unitObj.setName(user.getRealName());// 名称
					unitObj.setLeaf(true);// 是否叶子节点
					unitObj.setIdClass(idClass + 1);// 层级
					unitObj.setEnabled(user.getEnabled());//用户是否可用
					unitUserList.add(unitObj);// 存储数据对象
				}
			}
		}

		// 获得人员角色列表
		java.util.ArrayList roleList = (java.util.ArrayList) this.roleService
				.queryAll();
		UserRole sysRole = null;// 系统角色列表中的角色对象
		UserRole userRole = null;// 用户角色列表中的角色对象
		java.util.ArrayList roleUserList = new java.util.ArrayList();// 存储数据对象列表
		ChooseUnit nodeObj = null;// 临时节点变量
		int rolecode = 100;// 初始化角色编码
		String rolecodestr = "";// 初始化角色编码
		int roleidClass = 1;// 层级
		java.util.ArrayList userRoleList = new java.util.ArrayList();// 用户对应角色列表
		// 循环角色列表
		for (int i = 0; i < roleList.size(); i++) {
			int roleStart = 0;// 每个角色之下人员序号辅助变量
			rolecode++;// 角色层级编码
			sysRole = (com.itsv.platform.system.chooseuser.vo.UserRole) roleList
					.get(i);// 获得角色对象

			nodeObj = new ChooseUnit();// 实例化单位对象
			nodeObj.setId(sysRole.getId());// 编号
			nodeObj.setCode(String.valueOf(rolecode));// 编码
			nodeObj.setName(sysRole.getName());// 名称
			nodeObj.setLeaf(false);// 是否叶子节点
			nodeObj.setIdClass(roleidClass);// 层级
			roleUserList.add(nodeObj);// 存储数据对象

			// 循环人员列表
			for (int n = 0; n < userList.size(); n++) {
				// 获得用户对象
				user = (com.itsv.platform.system.chooseuser.vo.ChooseUser) userList
						.get(n);
				// 循环用户中的角色
				for (int s = 0; s < user.getRoles().size(); s++) {
					userRole = (com.itsv.platform.system.chooseuser.vo.UserRole) user
							.getRoles().get(s);// 角色对象					
					if (sysRole.getId().equals(userRole.getId()))
							// 如果当前用户是否属于当前角色
					{
						nodeObj = new com.itsv.platform.system.chooseunit.vo.ChooseUnit();// 实例节点对象

						nodeObj.setId(user.getId());// 编号
						roleStart++; //人员序号递增
						rolecodestr = String.valueOf(rolecode)
								+ "000".substring(String.valueOf(roleStart)
										.length())
								+ String.valueOf(roleStart);
						nodeObj.setCode(rolecodestr);// 编码
						nodeObj.setName(user.getRealName());// 名称
						nodeObj.setLeaf(true);// 是否叶子节点
						nodeObj.setIdClass(roleidClass + 1);// 层级
						nodeObj.setEnabled(user.getEnabled());//用户是否可用

						roleUserList.add(nodeObj);// 存储节点对象
						break;
					}
				}
			}
		}

		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setDictname("用户状态");		
		List<Itsv_dictionary> dictList = dictionaryService.queryByVO(itsv_dictionary);
		itsv_dictionary = (Itsv_dictionary)dictList.get(0);
		
		Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
		qryitsv_dictionary.setParentcode(itsv_dictionary.getCode());
		List<Itsv_dictionary> statusList = dictionaryService.queryByVO(qryitsv_dictionary);
		
		int statuscode = 500;
		roleidClass = 1;
		java.util.ArrayList statusUserList = new java.util.ArrayList();// 存储数据对象列表
		for(Itsv_dictionary dictObj: statusList){
			int statusStart = 0;// 每个角色之下人员序号辅助变量
			statuscode++;// 角色层级编码
			nodeObj = new ChooseUnit();// 实例化单位对象
			nodeObj.setId(dictObj.getId());// 编号
			nodeObj.setCode(String.valueOf(statuscode));// 编码
			nodeObj.setName(dictObj.getDictname());// 名称
			nodeObj.setLeaf(false);// 是否叶子节点
			nodeObj.setIdClass(roleidClass);// 层级
			statusUserList.add(nodeObj);// 存储数据对象
			for (int n = 0; n < userList.size(); n++) {
				// 获得用户对象
				user = (com.itsv.platform.system.chooseuser.vo.ChooseUser) userList
						.get(n);
				if(dictObj.getHardcode().equalsIgnoreCase(user.getStatus())){
					nodeObj = new com.itsv.platform.system.chooseunit.vo.ChooseUnit();
					nodeObj.setId(user.getId());// 编号
					statusStart++; //人员序号递增
					rolecodestr = String.valueOf(statuscode)
							+ "000".substring(String.valueOf(statusStart)
									.length())
							+ String.valueOf(statusStart);
					nodeObj.setCode(rolecodestr);// 编码
					nodeObj.setName(user.getRealName());// 名称
					nodeObj.setLeaf(true);// 是否叶子节点
					nodeObj.setIdClass(roleidClass + 1);// 层级
					nodeObj.setEnabled(user.getEnabled());//用户是否可用

					statusUserList.add(nodeObj);// 存储节点对象
				}
			}
		}

		mnv.addObject(WebConfig.DATA_NAME, unitUserList);// 单位人员列表
		mnv.addObject(USER_LIST, userList);// 人员列表
		mnv.addObject("roleList", roleUserList);// 角色人员列表
		mnv.addObject("statusUserList", statusUserList);// 状态人员列表

		return mnv;
	}

	/**
	 * sgb 070629 add
	 */
	public String getShowCheckUser() {
		return showCheckUser;
	}

	/**
	 * sgb 070629 add
	 */
	public void setShowCheckUser(String showCheckUser) {
		this.showCheckUser = showCheckUser;
	}

	/**
	 * sgb 070629 add
	 */
	public String getShowRadioUser() {
		return showRadioUser;
	}

	/**
	 * sgb 070629 add
	 */
	public void setShowRadioUser(String showRadioUser) {
		this.showRadioUser = showRadioUser;
	}

	public Itsv_dictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(Itsv_dictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}