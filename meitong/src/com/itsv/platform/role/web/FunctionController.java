package com.itsv.platform.role.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.role.bo.FunctionService;
import com.itsv.platform.role.vo.Function;

/**
 * 说明：增加，修改，删除功能的前端处理类
 * 
 * @author admin
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionController extends BaseCURDController<Function> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(FunctionController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.function";

	private String treeView; // 树状列表的视图

	private FunctionService functionService; // 逻辑层对象

	public String getTreeView() {
		return treeView;
	}

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

	/**
	 * adminh<br>
	 * 20070711<br>
	 * 在页面左侧显示树
	 */
	public ModelAndView menu(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// 设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// 设置树状显式具体列表数据。从缓存里取数
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("menu"));

		return mnv;
	}

	/**
	 * 创建菜单对象的树配置信息<br>
	 * adminh<br>
	 * 20070711<br>
	 */
	private TreeConfig getUnitTreeConfig() {
		// 设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta.setTitle("菜单");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		return meta;
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
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "menu_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 设置查询条件
		Function function = new Function();
		function.setMenu_id(id);
		List ret = this.functionService.queryByVO(function);

		// 加载数据
		mnv.addObject(WebConfig.DATA_NAME, ret);

		// 返回
		return mnv;
	}

	/**
	 * houxiaochen <br>
	 * 20070713 <br>
	 * 
	 * @param request
	 * @param response
	 * @param mnv
	 * @param records
	 * @param function
	 */
	public void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records, Function function) {

		//
		function = param2Object(request);

		// 将查询参数返回给页面
		mnv.addObject("condition", function);
		this.functionService.queryByVO(records, function);
	}

	/**
	 * 注册自定义类型转换类，用来转换日期对象
	 */
	protected void registerEditor(DataBinder binder) {
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(formater,
				true));
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
		Function function = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			function = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", function);
		} else {
			function = new Function();
		}

		this.functionService.queryByVO(records, function);
	}

	// 显示增加功能页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {

	}

	// 显示修改功能页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			Function function = this.functionService.queryById(id);
			if (null == function) {
				showMessage(request, "未找到对应的功能记录。请重试");
				mnv = query(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, function);
			}

		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存新增功能
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Function function = null;
		try {
			function = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, function)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// function);
			// }

			if (this.functionService.isFxnCodeExist(function.getCode())) {
				showMessage(request, "输入的'功能码'" + function.getCode() + "已存在。");
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
						function);
			}

			this.functionService.add(function);

			showMessage(request, "新增功能成功");
		} catch (AppException e) {
			logger.error("新增功能[" + function + "]失败", e);
			showMessage(request, "新增功能失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给功能
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, function);
		}

		return list(request, response);
	}

	/**
	 * 保存修改的功能
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Function function = null;
		try {
			function = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, function)) {
			// return edit(request, response);
			// }
			if (this.functionService.isFxnCodeExist(function)) {
				showMessage(request, "输入的'功能码'" + function.getCode() + "已存在。");
				return edit(request, response);
			}

			this.functionService.update(function);
			showMessage(request, "修改功能成功");
		} catch (AppException e) {
			logger.error("修改功能[" + function + "]失败", e);
			showMessage(request, "修改功能失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return edit(request, response);
		}
		return list(request, response);
		// return query(request, response);
	}

	/**
	 * 删除选中的功能
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] functions = ServletRequestUtils.getStringParameters(request,
				"p_id");
		// 允许部分删除成功
		try {
			for (String id : functions) {
				this.functionService.delete(id);
			}
			showMessage(request, "删除功能成功");
		} catch (AppException e) {
			logger.error("批量删除功能时失败", e);
			showMessage(request, "删除功能失败：" + e.getMessage(), e);
		}
		return list(request, response);
		// return query(request, response);
	}

	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}
}