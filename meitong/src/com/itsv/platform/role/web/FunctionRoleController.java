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
 * ˵�������ӣ��޸ģ�ɾ�����ܽ�ɫ��ǰ�˴�����
 * 
 * @author admin
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionRoleController extends BaseCURDController<FunctionRole> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(FunctionRoleController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.functionRole";

	private FunctionRoleService functionRoleService; // �߼������

	private String treeView; // ��״�б����ͼ

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
	 * ��ҳ�������ʾ��
	 */
	public ModelAndView menu(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// ȡ�ý�ɫID
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �ɲ˵�id��cache��ȡ�ò˵���¼
		// MirrorCacheTool.getAll("menu","id")��������ʹ
		List<Menu> listMenus = (List) MirrorCacheTool.getAll("menu");

		// ȡ�ô˽�ɫ��Ӧ���в˵�id
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

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getMenuTreeConfig());
		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		mnv.addObject(WebConfig.DATA_NAME, menus);
		mnv.addObject("role_id", id);
		return mnv;
	}

	/**
	 * ��ѯָ���˵�id��Ӧ�ġ����ܱ��롯��¼<br>
	 * adminh<br>
	 * 20070713<br>
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		// ȡ����ת��MNV
		ModelAndView mnv = new ModelAndView(getListView());

		// ȡ�ò˵�ID
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

		// ȡ�����дˡ���ɫ���ڴˡ��˵������ѷ���ġ����ܡ���¼
		FunctionRole fr = new FunctionRole();
		fr.setMenu_id(menu_id);
		fr.setRole_id(role_id);
		List<FunctionRole> list = this.functionRoleService.queryByVO(fr);

		// ���ò�ѯ����,ȡ�����д˲˵���Ӧ�ġ����ܡ���¼
		Function function = new Function();
		function.setMenu_id(menu_id);
		List tmp = this.functionRoleService.queryFunctionByVO(function);

		List ret = decorate(list, tmp);

		// ��������
		mnv.addObject(WebConfig.DATA_NAME, ret);
		mnv.addObject("menu_id", menu_id);
		mnv.addObject("role_id", role_id);
		// ����
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
					// ����˽�ɫ�д˹�����ѡ��
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
	 * ��ʾ
	 */
	// ���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getIndexView());

		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("role"));
		//
		return mnv;
	}

	/*
	 * public ModelAndView index(HttpServletRequest request, HttpServletResponse
	 * response) throws AppException { return super.query(request, response); }
	 */

	// ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		FunctionRole functionRole = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			functionRole = param2Object(request);

			// ����ѯ�������ظ�ҳ��
			mnv.addObject("condition", functionRole);
		} else {
			functionRole = new FunctionRole();
		}

		this.functionRoleService.queryByVO(records, functionRole);
	}

	// ��ʾ���ӹ��ܽ�ɫҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// ��ʾ�޸Ĺ��ܽ�ɫҳ��ǰ��׼������
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
	 * �����˵��������������Ϣ<br>
	 * adminh<br>
	 * 20070711<br>
	 */
	private TreeConfig getMenuTreeConfig() {
		// ������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta.setTitle("��ɫ�˵�");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		return meta;
	}

	/**
	 * �����������ܽ�ɫ
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		FunctionRole functionRole = null;
		try {
			functionRole = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, functionRole)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// functionRole);
			// }

			this.functionRoleService.add(functionRole);

			showMessage(request, "�������ܽ�ɫ�ɹ�");
		} catch (AppException e) {
			logger.error("�������ܽ�ɫ[" + functionRole + "]ʧ��", e);
			showMessage(request, "�������ܽ�ɫʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ�����ܽ�ɫ
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					functionRole);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵĹ��ܽ�ɫ
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		FunctionRole functionRole = null;
		try {
			functionRole = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, functionRole)) {
			// return edit(request, response);
			// }

			this.functionRoleService.update(functionRole);
			showMessage(request, "�޸Ĺ��ܽ�ɫ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ĺ��ܽ�ɫ[" + functionRole + "]ʧ��", e);
			showMessage(request, "�޸Ĺ��ܽ�ɫʧ�ܣ�" + e.getMessage(), e);

			// �޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 20070715<br>
	 * houxiaochen<br>
	 * ��ѡ�еĽ�ɫ���书��
	 */
	public ModelAndView assign(HttpServletRequest request,
			HttpServletResponse response) {

		// ����ѡ�еĹ���ID
		String[] functionRoles = ServletRequestUtils.getStringParameters(
				request, "p_id");
		// ����Ľ�ɫID
		String role_id = "";

		// �������ܲ˵�
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
				// �����¹���
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
			showMessage(request, "���书�ܳɹ�");
		} catch (AppException e) {
			logger.error("�������书��ʱʧ��", e);
			showMessage(request, "���书��ʧ�ܣ�" + e.getMessage(), e);
		}

		return list(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setFunctionRoleService(FunctionRoleService functionRoleService) {
		this.functionRoleService = functionRoleService;
	}
}