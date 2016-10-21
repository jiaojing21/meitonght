package com.itsv.gbp.core.admin.web;

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
import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.KeyGenerator;

/**
 * 说明：增加，修改，删除用户的前端处理类
 * 
 * @author admin 2005-2-1
 */
public class UserController extends BaseCURDController<User> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(UserController.class);

	private UserService userService; // 逻辑层对象

	private String treeView; // 树状列表的视图

	private String listView; // 用户列表显示视图

	/**
	 * 在页面左侧显示单位树。与单位管理不同的是，根节点可点击
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// 设置树状显式具体列表数据。从缓存里取数
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));

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

		} catch (ServletRequestBindingException e) {
			if (unitid == null || (unitid != null && unitid.equals(""))) {
				try {
					unitid = ServletRequestUtils.getStringParameter(request,
							"p_User_unitId");
				} catch (ServletRequestBindingException e1) {
					e1.printStackTrace();
				}
			}
		}

		// 如果指定了单位，则显示当前单位的用户。否则显示全部用户
		if (unitid != null && !unitid.equals("")) {
			mnv.addObject("unitId", unitid);
			mnv.addObject(WebConfig.DATA_NAME, this.userService
					.queryByUnitId(unitid));
		} else {
			mnv.addObject(WebConfig.DATA_NAME, this.userService.queryAll());
		}
		return mnv;
	}

	// 显示增加用户页面前，准备数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);

		mnv.addObject("unitId", uid);
	}

	// 显示修改用户页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);
		mnv.addObject("unitId", uid);
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = this.userService.queryById(id);
		if (null == user) {
			showMessage(request, "未找到对应的用户记录。请重试");
			mnv = list(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, user);
		}
	}

	/**
	 * 保存新增用户
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		String uid = null;
		User user = null;
		try {
			uid = ServletRequestUtils.getStringParameter(request, "p_unitId");

			request.setAttribute("unitId", uid);

			user = param2Object(request);

			// 数据校验，如失败直接返回
			if (!validate(request, user)) {
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME, user);
			}
			
			// 设置用户对应的角色
			fillRoles(user, ServletRequestUtils.getStringParameters(request,
					"roleIds"));
			this.userService.add(user);

			showMessage(request, "新增用户成功");
		} catch (AppException e) {
			logger.error("新增用户[" + user + "]失败", e);
			showMessage(request, "新增用户失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给用户
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, user);
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

		return list(request, response);
	}

	/**
	 * 保存修改的用户
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);
		request.setAttribute("unitId", uid);
		User user = null;
		try {
			user = param2Object(request);
			// 数据校验，如失败直接返回
			if (!validate(request, user)) {
				return edit(request, response);
			}

			// 设置用户对应的角色
			fillRoles(user, ServletRequestUtils.getStringParameters(request,
					"roleIds"));
			User pwduser = this.userService.queryById(user.getId());
			user.setPassword(pwduser.getPassword());
			this.userService.update(user);

			showMessage(request, "修改用户成功");
		} catch (AppException e) {
			logger.error("修改用户[" + user + "]失败", e);
			showMessage(request, "修改用户失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return list(request, response);
	}

	/**
	 * 根据传来的角色ID数组填充用户对应的角色对象
	 * 
	 * @param user
	 * @param intParameters
	 */
	private void fillRoles(User user, String[] roleIds) {
		if (roleIds == null || roleIds.length == 0)
			return;
		List<Role> roles = new ArrayList<Role>();
		for (String id : roleIds) {
			Role role = new Role();
			role.setId(id);
			roles.add(role);
		}
		user.setRoles(roles);
	}

	/**
	 * 删除选中的用户
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		int uid = ServletRequestUtils.getIntParameter(request, "unitId", 0);
		request.setAttribute("unitId", uid);

		String[] users = ServletRequestUtils.getStringParameters(request, "p_id");
		// 允许部分删除成功
		try {
			for (String id : users) {
				this.userService.delete(id);
			}
			showMessage(request, "删除用户成功");
		} catch (AppException e) {
			logger.error("批量删除用户时失败", e);
			showMessage(request, "删除用户失败：" + e.getMessage(), e);
		}

		return list(request, response);
	}

	// 创建菜单对象的树配置信息
	private TreeConfig getUnitTreeConfig() {
		// 设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta.setTitle("单位列表");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/**
	 * 调整用户显示顺序
	 */
	public ModelAndView sort(HttpServletRequest request,
			HttpServletResponse response) {
		User user = null;
		try {
			int type = 0;
			try {
				type = ServletRequestUtils.getIntParameter(request, "p_type");
			} catch (Exception ex) {
			}
			user = param2Object(request);
			request.setAttribute("unitId", user.getUnitId());
			List<User> list = this.userService.queryByUnitId(user.getUnitId());
			User obj = null;
			for (int i = 0; i < list.size(); i++) {
				obj = (User) list.get(i);
				if (obj.getId().equals(user.getId())) {
					if (type < 0) {
						// 上移
						if (list.size() != 1 && i > 0) {
							// 循环移动
							for (int k = 1; k <= (-1 * type); k++) {
								if (i - k < 0)
									break;
								Long sortno = obj.getSortno();
								User upobj = (User) list.get(i - k);
								obj.setSortno(upobj.getSortno());
								upobj.setSortno(sortno);
								this.userService.update(upobj);
							}
							this.userService.update(obj);
						}
					} else if (type > 0) {
						// 下移
						if (list.size() != 1 && i < list.size() - 1) {
							// 循环移动
							for (int k = 1; k <= type; k++) {
								if (i + k >= list.size())
									break;
								Long sortno = obj.getSortno();
								User downobj = (User) list.get(i + k);
								obj.setSortno(downobj.getSortno());
								downobj.setSortno(sortno);
								this.userService.update(downobj);
							}
							this.userService.update(obj);
						}
					}
					break;
				}
			}

		} catch (AppException e) {
		}

		return list(request, response);
	}

	/** 以下为set,get方法 */
	public void setUserService(UserService userService) {
		this.userService = userService;
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
}