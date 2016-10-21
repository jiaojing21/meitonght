package com.itsv.gbp.core.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.MenuService;
import com.itsv.gbp.core.admin.vo.Menu;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

/**
 * 说明：增加，修改，删除菜单的前端处理类。<br>
 * 
 * 主要演示：
 * <ol>
 * <li>通过TreeConfig对象，定制树状列表的显式。</li>
 * <li>通过beforeShowXX()方法，为相关显示页面准备数据。</li>
 * <li>实现saveXX()方法，来处理对对象的增删改操作。</li>
 * <li>通过ServletRequestUtils类，方便地获取相关参数。</li>
 * <li>通过param2Object()方法，将传来的参数打包进对象。</li>
 * </ol>
 * 
 * @author admin 2005-1-26
 */
public class MenuController extends BaseCURDController<Menu> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(MenuController.class);

	private MenuService menuService; //逻辑层对象

	private String treeView; //树状列表的视图

	/**
	 * 在页面左侧显示菜单树
	 */
	public ModelAndView showTree(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		//设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getMenuTreeConfig());
		//设置树状显式具体列表数据
		mnv.addObject(WebConfig.DATA_NAME, this.menuService.queryAll());

		return mnv;
	}

	//显示修改页面前，先取出对应菜单对象
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String key = "";
		try {
			key = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Menu menu = this.menuService.queryById(key);
		if (null == menu) {
			showMessage(request, "未找到对应的菜单记录。请重试");
			mnv.setViewName(getAddView());
		} else {
			mnv.addObject(WebConfig.DATA_NAME, menu);
		}
	}

	/**
	 * 保存新增菜单
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Menu menu = null;
		try {
			menu = param2Object(request);
			this.menuService.add(menu);

			showMessage(request, "新增菜单成功");
		} catch (AppException e) {
			logger.error("新增菜单[" + menu + "]失败", e);
			showMessage(request, "新增菜单失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给用户
			mnv.addObject(WebConfig.DATA_NAME, menu);
		}

		return mnv;
	}

	/**
	 * 保存修改的菜单信息
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Menu menu = null;
		try {
			menu = param2Object(request);
			this.menuService.update(menu);

			showMessage(request, "修改菜单成功");
		} catch (AppException e) {
			logger.error("修改菜单[" + menu + "]失败", e);
			showMessage(request, "修改菜单失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面，因为页面的隐含字段p_others.strParam1需要填充正确的值
			return edit(request, response);
		}

		return mnv;
	}

	/**
	 * 删除选中的菜单
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String key = "";
		try {
			key = ServletRequestUtils.getStringParameter(request, "p_Menu_id");
		} catch (ServletRequestBindingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.menuService.delete(key);
		} catch (AppException e) {
			logger.error("删除菜单[" + key + "]失败", e);

			//重新显示到编辑页面
			Menu menu = this.menuService.queryById(key);
			ModelAndView mnv = new ModelAndView(); 
			if (null == menu) {
				showMessage(request, "未找到对应的菜单记录。请重试");
				mnv.setViewName(getAddView());
			} else {
				mnv.setViewName(getEditView());
				mnv.addObject(WebConfig.DATA_NAME, menu);
			}
			showMessage(request, "删除菜单失败：" + e.getMessage(), e);
			return mnv;			
		}

		showMessage(request, "删除单菜单成功");
		return new ModelAndView(getAddView());
	}

	//创建菜单对象的树配置信息
	private TreeConfig getMenuTreeConfig() {
		//设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta.setTitle("菜单列表");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** 以下为set,get方法 */
	public String getTreeView() {
		return treeView;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}