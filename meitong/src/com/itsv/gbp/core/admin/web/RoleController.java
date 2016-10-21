package com.itsv.gbp.core.admin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.intercept.ObjectDefinitionSource;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionMap;
import org.acegisecurity.intercept.web.FilterSecurityInterceptor;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.RoleService;
import com.itsv.gbp.core.admin.security.RightProviderFactory;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.security.auth.RightInfoProviderFactory;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.propertyeditors.StringArrayListPropertyEditor;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

/**
 * 说明：增加，修改，删除角色的前端处理类<br>
 * 
 * @author admin 2005-2-1
 */
public class RoleController extends BaseCURDController<Role> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(RoleController.class);

	private RoleService roleService; //权限逻辑类

	
	
	/**
	 * 注册自定义类型转换类，将逗号分开的字符串转换为List对象。
	 */
	protected void registerEditor(DataBinder binder) {
		//指定转换的属性：menuIds
		binder.registerCustomEditor(List.class, "menuIds", new StringArrayListPropertyEditor());
	}

	//显示首页前，准备列表数据
	@Override
	protected void beforeShowList(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		mnv.addObject(WebConfig.DATA_NAME, this.roleService.queryAll());
	}

	//显示修改页面前，获取对应的角色对象，角色对应的菜单ID信息
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String key = "";
		try {
			key = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Role role = this.roleService.queryById(key);
		if (null == role) {
			showMessage(request, "未找到对应的角色。请重试");
			mnv.setViewName(getAddView());
		} else {
			mnv.addObject(WebConfig.DATA_NAME, role);
		}
		//设置本角色对应的菜单ID列表
		mnv.addObject("selectedMenuIds", this.roleService.queryMenuIds(key));
	}

	/**
	 * 保存新增角色
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Role role = null;
		try {
			role = param2Object(request);
			this.roleService.add(role, ServletRequestUtils.getStringParameters(request, "menuIds"));

			showMessage(request, "新增角色成功");
			
			/**
			 * ace8<br>
			 * 更新acegi中角色与菜单资源的绑定关系<br>
			 * 20080909
			 */
			FilterSecurityInterceptor f =(FilterSecurityInterceptor)WebApplicationContextUtils
			.getRequiredWebApplicationContext(
					request.getSession().getServletContext()).getBean("security.filter.filterInvocation");
			ObjectDefinitionSource o = f.getObjectDefinitionSource();
			f.setObjectDefinitionSource(this.roleService.getUpdatedSecurityPropertiesSet());
		} catch (AppException e) {
			logger.error("新增角色[" + role + "]失败", e);
			showMessage(request, "新增角色失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给用户
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, role);
		}catch(Exception e){
			e.printStackTrace();
		}

		return index(request, response);
	}

	/**
	 * 保存修改的角色信息
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Role role = null;
		try {
			role = param2Object(request);
			this.roleService.update(role, ServletRequestUtils.getStringParameters(request, "menuIds"));

			showMessage(request, "修改角色成功");
		} catch (AppException e) {
			logger.error("修改角色[" + role + "]失败", e);
			showMessage(request, "修改角色失败：" + e.getMessage(), e);

			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, role);
		}

		return index(request, response);
	}

	/**
	 * 删除选中的角色
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String[] roles = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : roles) {
				this.roleService.delete(id);
			}
			showMessage(request, "删除角色成功");
		} catch (AppException e) {
			logger.error("批量删除角色时失败", e);
			showMessage(request, "删除角色失败：" + e.getMessage(), e);
		}

		return index(request, response);
	}

	/** 以下为set,get方法 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}