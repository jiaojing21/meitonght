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
 * ˵�������ӣ��޸ģ�ɾ�����ܵ�ǰ�˴�����
 * 
 * @author admin
 * @since 2007-07-09
 * @version 1.0
 */
public class FunctionController extends BaseCURDController<Function> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(FunctionController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.function";

	private String treeView; // ��״�б����ͼ

	private FunctionService functionService; // �߼������

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
	 * ��ҳ�������ʾ��
	 */
	public ModelAndView menu(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		// ������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		// ������״��ʽ�����б����ݡ��ӻ�����ȡ��
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("menu"));

		return mnv;
	}

	/**
	 * �����˵��������������Ϣ<br>
	 * adminh<br>
	 * 20070711<br>
	 */
	private TreeConfig getUnitTreeConfig() {
		// ������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta.setTitle("�˵�");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		return meta;
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
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "menu_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ���ò�ѯ����
		Function function = new Function();
		function.setMenu_id(id);
		List ret = this.functionService.queryByVO(function);

		// ��������
		mnv.addObject(WebConfig.DATA_NAME, ret);

		// ����
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

		// ����ѯ�������ظ�ҳ��
		mnv.addObject("condition", function);
		this.functionService.queryByVO(records, function);
	}

	/**
	 * ע���Զ�������ת���࣬����ת�����ڶ���
	 */
	protected void registerEditor(DataBinder binder) {
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(formater,
				true));
	}

	// ���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Function function = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			function = param2Object(request);

			// ����ѯ�������ظ�ҳ��
			mnv.addObject("condition", function);
		} else {
			function = new Function();
		}

		this.functionService.queryByVO(records, function);
	}

	// ��ʾ���ӹ���ҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {

	}

	// ��ʾ�޸Ĺ���ҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			Function function = this.functionService.queryById(id);
			if (null == function) {
				showMessage(request, "δ�ҵ���Ӧ�Ĺ��ܼ�¼��������");
				mnv = query(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, function);
			}

		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ������������
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Function function = null;
		try {
			function = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, function)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// function);
			// }

			if (this.functionService.isFxnCodeExist(function.getCode())) {
				showMessage(request, "�����'������'" + function.getCode() + "�Ѵ��ڡ�");
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
						function);
			}

			this.functionService.add(function);

			showMessage(request, "�������ܳɹ�");
		} catch (AppException e) {
			logger.error("��������[" + function + "]ʧ��", e);
			showMessage(request, "��������ʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ������
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, function);
		}

		return list(request, response);
	}

	/**
	 * �����޸ĵĹ���
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Function function = null;
		try {
			function = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, function)) {
			// return edit(request, response);
			// }
			if (this.functionService.isFxnCodeExist(function)) {
				showMessage(request, "�����'������'" + function.getCode() + "�Ѵ��ڡ�");
				return edit(request, response);
			}

			this.functionService.update(function);
			showMessage(request, "�޸Ĺ��ܳɹ�");
		} catch (AppException e) {
			logger.error("�޸Ĺ���[" + function + "]ʧ��", e);
			showMessage(request, "�޸Ĺ���ʧ�ܣ�" + e.getMessage(), e);

			// �޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}
		return list(request, response);
		// return query(request, response);
	}

	/**
	 * ɾ��ѡ�еĹ���
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] functions = ServletRequestUtils.getStringParameters(request,
				"p_id");
		// ������ɾ���ɹ�
		try {
			for (String id : functions) {
				this.functionService.delete(id);
			}
			showMessage(request, "ɾ�����ܳɹ�");
		} catch (AppException e) {
			logger.error("����ɾ������ʱʧ��", e);
			showMessage(request, "ɾ������ʧ�ܣ�" + e.getMessage(), e);
		}
		return list(request, response);
		// return query(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}
}