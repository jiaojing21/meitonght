package com.itsv.platform.role.web;

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
import com.itsv.gbp.core.admin.vo.Menu;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.role.bo.FunctionRoleService;
import com.itsv.platform.role.vo.Function;
import com.itsv.platform.role.vo.FunctionRole;

/**
 * 说明：增加，修改，删除功能角色的前端处理类
 * 
 * @author admin
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionRoleController extends BaseCURDController<FunctionRole> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(FunctionRoleController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.functionRole";

	private FunctionRoleService functionRoleService; // 逻辑层对象

	private String treeView; // 树状列表的视图

	private String listView;

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public String getTreeView() {
		return treeView;
	}

	/**
	 * adminh<br>
	 * 20070714<br>
	 * 在页面左侧显示树
	 */
	public ModelAndView menu(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// 取得角色ID
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 由菜单id从cache中取得菜单记录
		// MirrorCacheTool.getAll("menu","id")方法不好使
		List<Menu> listMenus = (List) MirrorCacheTool.getAll("menu");

		// 取得此角色对应所有菜单id
		List list = this.functionRoleService.queryMenuIds(id);
		List<Menu> menus = new ArrayList<Menu>();
		Menu tmp = null;

		for (int i = 0; i < list.size(); i++) {
			String lMenuId = (String) list.get(i);
			for (int j = 0; j < listMenus.size(); j++) {
				tmp = listMenus.get(j);
				if (lMenuId.equals(tmp.getId())) {
					menus.add(tmp);
				}
			}
			tmp = null;
		}

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getMenuTreeConfig());
		// 设置树状显式具体列表数据。从缓存里取数
		mnv.addObject(WebConfig.DATA_NAME, menus);
		mnv.addObject("role_id", id);
		return mnv;
	}

	/**
	 * 查询指定菜单id对应的‘功能编码’记录<br>
	 * adminh<br>
	 * 20070713<br>
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		// 取得跳转的MNV
		ModelAndView mnv = new ModelAndView(getListView());

		// 取得菜单ID
		String menu_id = "";

		String role_id = "";

		try {
			menu_id = ServletRequestUtils
					.getStringParameter(request, "menu_id");
			role_id = ServletRequestUtils
					.getStringParameter(request, "role_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 取得所有此‘角色’在此‘菜单’下已分配的‘功能’记录
		FunctionRole fr = new FunctionRole();
		fr.setMenu_id(menu_id);
		fr.setRole_id(role_id);
		List<FunctionRole> list = this.functionRoleService.queryByVO(fr);

		// 设置查询条件,取得所有此菜单对应的‘功能’记录
		Function function = new Function();
		function.setMenu_id(menu_id);
		List tmp = this.functionRoleService.queryFunctionByVO(function);

		List ret = decorate(list, tmp);

		// 加载数据
		mnv.addObject(WebConfig.DATA_NAME, ret);
		mnv.addObject("menu_id", menu_id);
		mnv.addObject("role_id", role_id);
		// 返回
		return mnv;
	}

	/**
	 * @param frlist
	 * @param flist
	 * @return
	 */
	private List decorate(List<FunctionRole> frlist, List<Function> flist) {
		List<Function> ret = new ArrayList<Function>();
		for (Function f : flist) {
			for (FunctionRole functionRole : frlist) {
				if (functionRole.getFunction_id().equals(f.getId())) {
					// 如果此角色有此功能则选中
					f.setVc(1);
				}
			}
			ret.add(f);
		}
		return ret;
	}

	/**
	 * adminh<br>
	 * 20070714<br>
	 * 显示
	 */
	// 覆盖父类方法，默认执行query()，分页显示数据
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getIndexView());

		// 设置树状显式具体列表数据。从缓存里取数
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("role"));
		//
		return mnv;
	}

	/*
	 * public ModelAndView index(HttpServletRequest request, HttpServletResponse
	 * response) throws AppException { return super.query(request, response); }
	 */

	// 实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		FunctionRole functionRole = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			functionRole = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", functionRole);
		} else {
			functionRole = new FunctionRole();
		}

		this.functionRoleService.queryByVO(records, functionRole);
	}

	// 显示增加功能角色页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// 显示修改功能角色页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {

		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			mnv.addObject("role_id", id);
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建菜单对象的树配置信息<br>
	 * adminh<br>
	 * 20070711<br>
	 */
	private TreeConfig getMenuTreeConfig() {
		// 设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta.setTitle("角色菜单");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		return meta;
	}

	/**
	 * 保存新增功能角色
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		FunctionRole functionRole = null;
		try {
			functionRole = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, functionRole)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// functionRole);
			// }

			this.functionRoleService.add(functionRole);

			showMessage(request, "新增功能角色成功");
		} catch (AppException e) {
			logger.error("新增功能角色[" + functionRole + "]失败", e);
			showMessage(request, "新增功能角色失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给功能角色
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					functionRole);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的功能角色
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		FunctionRole functionRole = null;
		try {
			functionRole = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, functionRole)) {
			// return edit(request, response);
			// }

			this.functionRoleService.update(functionRole);
			showMessage(request, "修改功能角色成功");
		} catch (AppException e) {
			logger.error("修改功能角色[" + functionRole + "]失败", e);
			showMessage(request, "修改功能角色失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 20070715<br>
	 * houxiaochen<br>
	 * 给选中的角色分配功能
	 */
	public ModelAndView assign(HttpServletRequest request,
			HttpServletResponse response) {

		// 所有选中的功能ID
		String[] functionRoles = ServletRequestUtils.getStringParameters(
				request, "p_id");
		// 分配的角色ID
		String role_id = "";

		// 操作功能菜单
		String menu_id = "";
		try {
			menu_id = ServletRequestUtils
					.getStringParameter(request, "menu_id");
			role_id = ServletRequestUtils
					.getStringParameter(request, "role_id");
		} catch (ServletRequestBindingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//
		FunctionRole functionRole = null;
		List<FunctionRole> listFR = new ArrayList<FunctionRole>();

		try {

			/**
			 * 
			 */
			for (String id : functionRoles) {
				// 增加新关联
				functionRole = new FunctionRole();
				functionRole.setFunction_id(id);
				functionRole.setRole_id(role_id);
				functionRole.setMenu_id(menu_id);
				listFR.add(functionRole);
				functionRole = null;
			}

			//
			this.functionRoleService.updateForMenu(listFR, menu_id, role_id);

			//
			showMessage(request, "分配功能成功");
		} catch (AppException e) {
			logger.error("批量分配功能时失败", e);
			showMessage(request, "分配功能失败：" + e.getMessage(), e);
		}

		return list(request, response);
	}

	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setFunctionRoleService(FunctionRoleService functionRoleService) {
		this.functionRoleService = functionRoleService;
	}
}