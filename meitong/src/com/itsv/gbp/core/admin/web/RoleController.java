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
 * ˵�������ӣ��޸ģ�ɾ����ɫ��ǰ�˴�����<br>
 * 
 * @author admin 2005-2-1
 */
public class RoleController extends BaseCURDController<Role> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(RoleController.class);

	private RoleService roleService; //Ȩ���߼���

	
	
	/**
	 * ע���Զ�������ת���࣬�����ŷֿ����ַ���ת��ΪList����
	 */
	protected void registerEditor(DataBinder binder) {
		//ָ��ת�������ԣ�menuIds
		binder.registerCustomEditor(List.class, "menuIds", new StringArrayListPropertyEditor());
	}

	//��ʾ��ҳǰ��׼���б�����
	@Override
	protected void beforeShowList(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		mnv.addObject(WebConfig.DATA_NAME, this.roleService.queryAll());
	}

	//��ʾ�޸�ҳ��ǰ����ȡ��Ӧ�Ľ�ɫ���󣬽�ɫ��Ӧ�Ĳ˵�ID��Ϣ
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
			showMessage(request, "δ�ҵ���Ӧ�Ľ�ɫ��������");
			mnv.setViewName(getAddView());
		} else {
			mnv.addObject(WebConfig.DATA_NAME, role);
		}
		//���ñ���ɫ��Ӧ�Ĳ˵�ID�б�
		mnv.addObject("selectedMenuIds", this.roleService.queryMenuIds(key));
	}

	/**
	 * ����������ɫ
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Role role = null;
		try {
			role = param2Object(request);
			this.roleService.add(role, ServletRequestUtils.getStringParameters(request, "menuIds"));

			showMessage(request, "������ɫ�ɹ�");
			
			/**
			 * ace8<br>
			 * ����acegi�н�ɫ��˵���Դ�İ󶨹�ϵ<br>
			 * 20080909
			 */
			FilterSecurityInterceptor f =(FilterSecurityInterceptor)WebApplicationContextUtils
			.getRequiredWebApplicationContext(
					request.getSession().getServletContext()).getBean("security.filter.filterInvocation");
			ObjectDefinitionSource o = f.getObjectDefinitionSource();
			f.setObjectDefinitionSource(this.roleService.getUpdatedSecurityPropertiesSet());
		} catch (AppException e) {
			logger.error("������ɫ[" + role + "]ʧ��", e);
			showMessage(request, "������ɫʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ���û�
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, role);
		}catch(Exception e){
			e.printStackTrace();
		}

		return index(request, response);
	}

	/**
	 * �����޸ĵĽ�ɫ��Ϣ
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Role role = null;
		try {
			role = param2Object(request);
			this.roleService.update(role, ServletRequestUtils.getStringParameters(request, "menuIds"));

			showMessage(request, "�޸Ľ�ɫ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ľ�ɫ[" + role + "]ʧ��", e);
			showMessage(request, "�޸Ľ�ɫʧ�ܣ�" + e.getMessage(), e);

			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, role);
		}

		return index(request, response);
	}

	/**
	 * ɾ��ѡ�еĽ�ɫ
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String[] roles = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : roles) {
				this.roleService.delete(id);
			}
			showMessage(request, "ɾ����ɫ�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ����ɫʱʧ��", e);
			showMessage(request, "ɾ����ɫʧ�ܣ�" + e.getMessage(), e);
		}

		return index(request, response);
	}

	/** ����Ϊset,get���� */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}