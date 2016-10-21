package com.itsv.gbp.core.group.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.group.bo.Gbp_groupService;
import com.itsv.gbp.core.group.vo.Gbp_group;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;
import com.itsv.platform.system.chooseuser.vo.UserRole;

/**
 * 说明：增加，修改，删除组的前端处理类
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupController extends BaseCURDController<Gbp_group> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(Gbp_groupController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.gbp_group";

	private Gbp_groupService gbp_groupService; // 逻辑层对象

	private String treeView; // 树状列表的视图

	private String showCheckUser; // 选择多选单位用户的页面

	private String showRadioUser; // 选择单选单位用户的页面
	
	private String showCheckRole;

	String USER_LIST = "userList";

	public void setShowCheckUser(String showCheckUser) {
		this.showCheckUser = showCheckUser;
	}

	public void setShowRadioUser(String showRadioUser) {
		this.showRadioUser = showRadioUser;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public void setUSER_LIST(String user_list) {
		USER_LIST = user_list;
	}

	// 覆盖父类方法，默认执行query()，分页显示数据
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// 实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Gbp_group gbp_group = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			gbp_group = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", gbp_group);
		} else {
			gbp_group = new Gbp_group();
		}

		this.gbp_groupService.queryByVO(records, gbp_group);
	}

	// 显示增加组页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// 显示修改组页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			Gbp_group gbp_group = this.gbp_groupService.queryById(id);
			if (null == gbp_group) {
				showMessage(request, "未找到对应的组记录。请重试");
				mnv = query(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, gbp_group);
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存新增组
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Gbp_group gbp_group = null;
		try {
			gbp_group = param2Object(request);

			this.gbp_groupService.add(gbp_group);

			showMessage(request, "新增组成功");
		} catch (AppException e) {
			logger.error("新增组[" + gbp_group + "]失败", e);
			showMessage(request, "新增组失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给组
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					gbp_group);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的组
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Gbp_group gbp_group = null;
		try {
			gbp_group = param2Object(request);

			this.gbp_groupService.update(gbp_group);
			showMessage(request, "修改组成功");
		} catch (AppException e) {
			logger.error("修改组[" + gbp_group + "]失败", e);
			showMessage(request, "修改组失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的组
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] gbp_groups = ServletRequestUtils.getStringParameters(request,
				"p_id");
		// 允许部分删除成功
		try {
			for (String id : gbp_groups) {
				this.gbp_groupService.delete(id);
			}
			showMessage(request, "删除组成功");
		} catch (AppException e) {
			logger.error("批量删除组时失败", e);
			showMessage(request, "删除组失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setGbp_groupService(Gbp_groupService gbp_groupService) {
		this.gbp_groupService = gbp_groupService;
	}

	public String getTreeView() {
		return treeView;
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

	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// 设置树状显式具体列表数据。从缓存里取数
		// mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));
		mnv
				.addObject(WebConfig.DATA_NAME, this.gbp_groupService
						.queryAllUnit());

		return mnv;
	}

	/**
	 * 添加用户组关系
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addUserGroup(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mnv = null;
		try {
			// 取得选择的用户id
			String s = ServletRequestUtils.getStringParameter(request, "uids");

			// 取得进行操作的组id
			String groupId = ServletRequestUtils.getStringParameter(request,
					"groupId");

			// stub
			System.out.println("Group ID>>>>" + groupId);
			System.out.println("User IDs>>>>" + s);

			//
			if (s != null && groupId != null) {
				String sa[] = s.split(",");
				for (int i = 0; i < sa.length; i++) {
					String userid = sa[i];
					try {
						if (userid != null && userid.length() == 32) {
							if (i == 0) {
								// 删除所有原有Group User关系
								this.gbp_groupService
										.deleteAllGroupUserByGroupId(groupId);
							}
							//拼装Vo
							Gbp_groupuser tmpGu = new Gbp_groupuser();
							tmpGu.setGroupid(groupId);
							tmpGu.setUserid(userid);
							this.gbp_groupService.addGroupUser(tmpGu);

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return mnv;
	}
	/**
	 * 添加组角色关系
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addRoleGroup(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mnv = null;
		try {
			// 取得选择的用户id
			String s = ServletRequestUtils.getStringParameter(request, "rids");

			// 取得进行操作的组id
			String groupId = ServletRequestUtils.getStringParameter(request,
					"groupId");

			// stub
			System.out.println("Group ID>>>>" + groupId);
			System.out.println("role IDs>>>>" + s);
			
			//
			if (s != null && groupId != null) {
				String sa[] = s.split(",");
				for (int i = 0; i < sa.length; i++) {
					String roleid = sa[i];
					try {
						if (roleid != null && roleid.length() == 32) {
							if (i == 0) {
								// 删除所有原有Group Role关系
								this.gbp_groupService
										.deleteAllGroupRoleByGroupId(groupId);
							}
							//拼装Vo
							Gbp_grouprole tmpGr = new Gbp_grouprole();
							tmpGr.setGroupid(groupId);
							tmpGr.setRoleid(roleid);
							this.gbp_groupService.addGroupRole(tmpGr);

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			
			
			
			
			
			
			
			
			
		
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return mnv;
	}
	
	
	public ModelAndView showCheckRole(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mnv = null;

			mnv = new ModelAndView(getShowCheckRole());

		// 取得当前操作组所对应的用户信息
		String groupId = (String) request.getParameter("gid");
		List checkedRoleIdList = new ArrayList();
		if (groupId != null) {
			checkedRoleIdList = this.gbp_groupService
					.queryRoleIdsByGroupId(groupId);

		}		
		
		List roleList = this.gbp_groupService.queryAllRole();
		mnv.addObject("groupId", groupId);// 
		mnv.addObject("roleList", roleList);// 角色列表
		mnv.addObject("checkedRoleIdList", checkedRoleIdList);
		
		return mnv;
		
	}
	
	
	
	
	
	/**
	 * sgb 070703 add 在页面显示多选单位\角色\所有用户树
	 */
	public ModelAndView showCheckUnitUser(HttpServletRequest request,
			HttpServletResponse response) {

		// ModelAndView depmnv =this.showTree(request, response);
		String type = request.getParameter("type");// 标识是多选还是单选
		ModelAndView mnv = null;
		if (type != null && type.equals("radio"))
			mnv = new ModelAndView(getShowRadioUser());
		else
			mnv = new ModelAndView(getShowCheckUser());

		// 取得当前操作组所对应的用户信息
		String groupId = (String) request.getParameter("gid");
		List checkedUserIdList = new ArrayList();
		if (groupId != null) {
			checkedUserIdList = this.gbp_groupService
					.queryUserIdByGroupId(groupId);

		}

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());

		// 获得单位列表
		java.util.ArrayList unitList = (java.util.ArrayList) this.gbp_groupService
				.queryAllUnit();

		// 获得人员列表
		java.util.ArrayList userList = (java.util.ArrayList) this.gbp_groupService
				.queryAllUser();

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
					unitObj.setEnabled(user.getEnabled());// 用户是否可用
					unitUserList.add(unitObj);// 存储数据对象
				}
			}
		}

		// 获得人员角色列表
		java.util.ArrayList roleList = (java.util.ArrayList) this.gbp_groupService
				.queryAllRole();
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
						roleStart++; // 人员序号递增
						rolecodestr = String.valueOf(rolecode)
								+ "000".substring(String.valueOf(roleStart)
										.length()) + String.valueOf(roleStart);
						nodeObj.setCode(rolecodestr);// 编码
						nodeObj.setName(user.getRealName());// 名称
						nodeObj.setLeaf(true);// 是否叶子节点
						nodeObj.setIdClass(roleidClass + 1);// 层级
						nodeObj.setEnabled(user.getEnabled());// 用户是否可用

						roleUserList.add(nodeObj);// 存储节点对象
						break;
					}
				}
			}
		}

		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setDictname("用户状态");
		List<Itsv_dictionary> dictList = gbp_groupService
				.queryDictByVO(itsv_dictionary);
		itsv_dictionary = (Itsv_dictionary) dictList.get(0);

		Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
		qryitsv_dictionary.setParentcode(itsv_dictionary.getCode());
		List<Itsv_dictionary> statusList = gbp_groupService
				.queryDictByVO(qryitsv_dictionary);

		int statuscode = 500;
		roleidClass = 1;
		java.util.ArrayList statusUserList = new java.util.ArrayList();// 存储数据对象列表
		for (Itsv_dictionary dictObj : statusList) {
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
				if (dictObj.getHardcode().equalsIgnoreCase(user.getStatus())) {
					nodeObj = new com.itsv.platform.system.chooseunit.vo.ChooseUnit();
					nodeObj.setId(user.getId());// 编号
					statusStart++; // 人员序号递增
					rolecodestr = String.valueOf(statuscode)
							+ "000".substring(String.valueOf(statusStart)
									.length()) + String.valueOf(statusStart);
					nodeObj.setCode(rolecodestr);// 编码
					nodeObj.setName(user.getRealName());// 名称
					nodeObj.setLeaf(true);// 是否叶子节点
					nodeObj.setIdClass(roleidClass + 1);// 层级
					nodeObj.setEnabled(user.getEnabled());// 用户是否可用

					statusUserList.add(nodeObj);// 存储节点对象
				}
			}
		}

		mnv.addObject(WebConfig.DATA_NAME, unitUserList);// 单位人员列表
		mnv.addObject(USER_LIST, userList);// 人员列表
		mnv.addObject("roleList", roleUserList);// 角色人员列表
		mnv.addObject("statusUserList", statusUserList);// 状态人员列表
		mnv.addObject("UserIdList", checkedUserIdList);// 已选择的用户id
		mnv.addObject("groupId", groupId);
		return mnv;
	}

	public String getShowCheckUser() {
		return showCheckUser;
	}

	public String getShowRadioUser() {
		return showRadioUser;
	}

	public String getShowCheckRole() {
		return showCheckRole;
	}

	public void setShowCheckRole(String showCheckRole) {
		this.showCheckRole = showCheckRole;
	}

}