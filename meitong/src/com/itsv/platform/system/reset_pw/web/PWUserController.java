package com.itsv.platform.system.reset_pw.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;

import com.itsv.gbp.core.util.MD5;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.platform.system.reset_pw.bo.PWUserService;
import com.itsv.platform.system.reset_pw.vo.PWUser;
import com.itsv.platform.system.chooseunit.bo.ChooseUnitService;

/**
 * 说明：增加，修改，删除用户的前端处理类
 * 
 * @author admin 2005-2-1
 */
public class PWUserController extends BaseCURDController<PWUser> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(PWUserController.class);

	private PWUserService userService; // 逻辑层对象

	private ChooseUnitService unitService; // 单位逻辑层对象

	private String treeView; // 树状列表的视图

	private String listView; // 用户列表显示视图
	
	private String repareView; // 修改密码视图

	/**
	 * 在页面左侧显示单位树。与单位管理不同的是，根节点可点击
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// 设置树状显式具体列表数据。从缓存里取数
		mnv.addObject(WebConfig.DATA_NAME, this.unitService.queryAll());

		return mnv;
	}

	/**
	 * 用户列表显示页面。显示指定单位下的所有用户
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getListView());

		String unitid="";
		try {
			unitid = ServletRequestUtils.getStringParameter(request, "unitId");
			if (unitid==null) {
				unitid = ServletRequestUtils.getStringParameter(request, "p_unitId");
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		// 如果指定了单位，则显示当前单位的用户。否则显示全部用户
		if (unitid!=null&&!unitid.equals("")) {
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
	}

	// 显示修改用户页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	/**
	 * 保存新增用户
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 保存修改的用户
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 删除选中的用户
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 打开修改密码界面
	 */
	public ModelAndView reparePwd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		mnv.addObject(WebConfig.DATA_NAME, userService.getUserInfo());
		mnv.setViewName(this.getRepareView());
		mnv.addObject("reparePwd", "0");
		return mnv;
	}
	
	/**
	 * 修改密码
	 */
	public ModelAndView savePwd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		mnv.setViewName(this.getRepareView());
		PWUser user = this.param2Object(request);
		PWUser newUser = this.userService.queryById(user.getId());
		
		
		MD5 md = new MD5();
		String oldPassMD5 = newUser.getPassword();
		String confirmPass =request.getParameter("p_PWUser_input");
		if(confirmPass!=null&&md.getMD5ofStr(confirmPass).equalsIgnoreCase(oldPassMD5)){
			newUser.setPassword(md.getMD5ofStr(user.getPassword()).toLowerCase());
			this.userService.update(newUser);
			showMessage(request, "用户密码修改完成！");
		}else{
			showMessage(request, "原始密码错误！！");
		}
		mnv.addObject(WebConfig.DATA_NAME, userService.getUserInfo());
		mnv.addObject("reparePwd", "0");
		return mnv;
	}
	
	// 创建菜单对象的树配置信息
	private TreeConfig getUnitTreeConfig() {
		// 设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta
				.setTitle("<span onclick='javascript:parent.hitTree(0, 0, 0, 0, 0);' style='cursor:hand;''>单位列表</span>");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");
		return meta;
	}

	/**
	 * 重置用户密码
	 */
	public ModelAndView resetPW(HttpServletRequest request,
			HttpServletResponse response) {
		String[] userIds = new String[0];
		
		/***
		 * ace <br>
		 * 新增用户的默认密码使用md5保持<br>
		 * 小写
		 */
		MD5 md = new MD5();
		String s ="123456";
		String initPW =md.getMD5ofStr(s).toLowerCase();
		//String initPW = "123456";
		
		PWUser user = null;
		try {
			userIds = request.getParameterValues("p_id");
			//if (request.getParameter("initPW") != null)
			//	initPW = request.getParameter("initPW");
			for (int i = 0; i < userIds.length; i++) {
				user = this.userService.queryById(userIds[i]);
				user.setPassword(initPW);
				this.userService.update(user);
			}
			showMessage(request, "重置用户密码成功");
		} catch (AppException e) {
			logger.error("重置用户密码失败", e);
			showMessage(request, "重置用户密码失败：" + e.getMessage(), e);

		}
		return list(request, response);
	}

	/** 以下为set,get方法 */
	public void setUserService(PWUserService userService) {
		this.userService = userService;
	}

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
	public String getRepareView() {
		return repareView;
	}

	public void setRepareView(String repareView) {
		this.repareView = repareView;
	}

}