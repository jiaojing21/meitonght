package com.itsv.platform.common.dictionary.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.annotation.brand.bo.BrandService;
import com.itsv.annotation.brand.vo.Brand;
import com.itsv.annotation.util.ServiceUitl;
import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

/**
 * ˵�������ӣ��޸ģ�ɾ�������ֵ��ǰ�˴�����
 * 
 * @author milu
 * @since 2007-07-22
 * @version 1.0
 */
public class Itsv_dictionaryController extends
		BaseCURDController<Itsv_dictionary> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory
			.getLog(Itsv_dictionaryController.class);

	// ��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.itsv_dictionary";

	private Itsv_dictionaryService itsv_dictionaryService; // �߼������
	
	private String treeView; // ��״�б����ͼ

	private String listView; // �û��б���ʾ��ͼ

	/**
	 * ��ҳ�������ʾ��λ�����뵥λ����ͬ���ǣ����ڵ�ɵ��
	 */
	public ModelAndView showTree(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());
		mnv.addObject(WebConfig.DATA_NAME, this.itsv_dictionaryService
				.queryByVO(new Itsv_dictionary()));
		mnv.addObject("refreshCode", request.getParameter("refreshCode"));
		return mnv;
	}

	/**
	 * �û��б���ʾҳ�档��ʾָ����λ�µ������û�
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getListView());
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		mnv.addObject("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		mnv.addObject("codeclass", codeclass);
		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setParentcode(parentcode);
		mnv.addObject(WebConfig.DATA_NAME, this.itsv_dictionaryService
				.queryByVO(itsv_dictionary));
		return mnv;
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
		Itsv_dictionary itsv_dictionary = null;

		// ����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			itsv_dictionary = param2Object(request);

			// ����ѯ�������ظ�ҳ��
			mnv.addObject("condition", itsv_dictionary);
		} else {
			itsv_dictionary = new Itsv_dictionary();
		}

		this.itsv_dictionaryService.queryByVO(records, itsv_dictionary);
	}

	// ��ʾ���������ֵ�ҳ��ǰ��׼���������
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		mnv.addObject("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		mnv.addObject("codeclass", codeclass);
		String code = "";
		if(parentcode.length()>3){
			code = parentcode.substring(0, 3);
		}
		String brandcode = "";
		List<Brand> list = null;
		if(parentcode.length()>6){
			brandcode = parentcode.substring(0, 6);
			list = ServiceUitl.getBrandType(brandcode);
		}
		mnv.addObject("list", list);
		mnv.addObject("code", code);
	}

	// ��ʾ�޸������ֵ�ҳ��ǰ��׼������
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		mnv.addObject("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		mnv.addObject("codeclass", codeclass);
		
		String code = "";
		if(parentcode.length()>3){
			code = parentcode.substring(0, 3);
		}
		String brandcode = "";
		List<Brand> list = null;
		if(parentcode.length()>6){
			brandcode = parentcode.substring(0, 6);
			list = ServiceUitl.getBrandType(brandcode);
		}
		mnv.addObject("list", list);
		mnv.addObject("code", code);
		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			Itsv_dictionary itsv_dictionary = this.itsv_dictionaryService
					.queryById(id);
			if (null == itsv_dictionary) {
				showMessage(request, "δ�ҵ���Ӧ�������ֵ��¼��������");
				mnv = list(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, itsv_dictionary);
			}

		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * �������������ֵ�
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_dictionary itsv_dictionary = null;
		try {
			itsv_dictionary = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, itsv_dictionary)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// itsv_dictionary);
			// }
			String parentcode = ServletRequestUtils.getStringParameter(request,
					"parentcode", "");
			request.setAttribute("parentcode", parentcode);
			String codeclass = ServletRequestUtils.getStringParameter(request,
					"codeclass", "");
			request.setAttribute("codeclass", codeclass);

			Itsv_dictionary nameItsv_dictionary = new Itsv_dictionary();
			nameItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			nameItsv_dictionary.setDictname(itsv_dictionary.getDictname());
			List namelist = this.itsv_dictionaryService
					.queryByVO(nameItsv_dictionary);
			if (namelist != null && namelist.size() > 0) {
				showMessage(request, "�ֵ����Ʋ����ظ���");
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
						itsv_dictionary);
			}

			Itsv_dictionary codeItsv_dictionary = new Itsv_dictionary();
			codeItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			codeItsv_dictionary.setHardcode(itsv_dictionary.getHardcode());
			List codelist = this.itsv_dictionaryService
					.queryByVO(codeItsv_dictionary);
			if (codelist != null && codelist.size() > 0) {
				showMessage(request, "ҵ����벻���ظ���");
				return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
						itsv_dictionary);
			}
			this.itsv_dictionaryService.add(itsv_dictionary);
			request.setAttribute("refreshTree", "1");
			request
					.setAttribute("refreshCode", itsv_dictionary
							.getParentcode());
			showMessage(request, "���������ֵ�ɹ�");
		} catch (AppException e) {
			logger.error("���������ֵ�[" + itsv_dictionary + "]ʧ��", e);
			showMessage(request, "���������ֵ�ʧ�ܣ�" + e.getMessage(), e);

			// ����ʧ�ܺ�Ӧ������д������������ʾ�������ֵ�
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
					itsv_dictionary);
		}

		return list(request, response);
	}

	/**
	 * �����޸ĵ������ֵ�
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Itsv_dictionary itsv_dictionary = null;
		try {
			itsv_dictionary = param2Object(request);

			// ��������У��������⣬�ݲ����÷���˵�����У�鹦��
			// Ace8 2006.9.10
			// ����У�飬��ʧ��ֱ�ӷ���
			// if (!validate(request, itsv_dictionary)) {
			// return edit(request, response);
			// }

			String parentcode = ServletRequestUtils.getStringParameter(request,
					"parentcode", "");
			request.setAttribute("parentcode", parentcode);
			String codeclass = ServletRequestUtils.getStringParameter(request,
					"codeclass", "");
			request.setAttribute("codeclass", codeclass);

			Itsv_dictionary nameItsv_dictionary = new Itsv_dictionary();
			nameItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			nameItsv_dictionary.setDictname(itsv_dictionary.getDictname());
			List namelist = this.itsv_dictionaryService
					.queryByVO(nameItsv_dictionary);
			if (namelist != null && namelist.size() > 0) {
				if (!itsv_dictionary.getId().equals(
						((Itsv_dictionary) namelist.get(0)).getId())) {
					showMessage(request, "�ֵ����Ʋ����ظ���");
					return new ModelAndView(getEditView(), WebConfig.DATA_NAME,
							itsv_dictionary);
				}
			}

			Itsv_dictionary codeItsv_dictionary = new Itsv_dictionary();
			codeItsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
			codeItsv_dictionary.setHardcode(itsv_dictionary.getHardcode());
			List codelist = this.itsv_dictionaryService
					.queryByVO(codeItsv_dictionary);
			if (codelist != null && codelist.size() > 0) {
				if (!itsv_dictionary.getId().equals(
						((Itsv_dictionary) codelist.get(0)).getId())) {
					showMessage(request, "ҵ����벻���ظ���");
					return new ModelAndView(getEditView(), WebConfig.DATA_NAME,
							itsv_dictionary);
				}
			}

			this.itsv_dictionaryService.update(itsv_dictionary);
			request.setAttribute("refreshTree", "1");
			request
					.setAttribute("refreshCode", itsv_dictionary
							.getParentcode());
			showMessage(request, "�޸������ֵ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸������ֵ�[" + itsv_dictionary + "]ʧ��", e);
			showMessage(request, "�޸������ֵ�ʧ�ܣ�" + e.getMessage(), e);

			// �޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return list(request, response);
	}

	/**
	 * ɾ��ѡ�е������ֵ�
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] itsv_dictionarys = ServletRequestUtils.getStringParameters(
				request, "p_id");
		String parentcode = ServletRequestUtils.getStringParameter(request,
				"parentcode", "");
		request.setAttribute("parentcode", parentcode);
		String codeclass = ServletRequestUtils.getStringParameter(request,
				"codeclass", "");
		request.setAttribute("codeclass", codeclass);
		Itsv_dictionary itsv_dictionary = this.itsv_dictionaryService
				.queryById(itsv_dictionarys[0]);
		// ������ɾ���ɹ�
		try {
			for (String id : itsv_dictionarys) {
				Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
				qryitsv_dictionary.setParentcode(itsv_dictionary.getCode());
				List list = this.itsv_dictionaryService
						.queryByVO(qryitsv_dictionary);
				if (list.size() > 0) {
					showMessage(request, "������ɾ�������ֵ��¼������ɾ�����������ֵ䣡");
					return list(request, response);
				} else {
					this.itsv_dictionaryService.delete(id);
				}
			}
			request.setAttribute("refreshTree", "1");
			request
					.setAttribute("refreshCode", itsv_dictionary
							.getParentcode());
			showMessage(request, "ɾ�������ֵ�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ�������ֵ�ʱʧ��", e);
			showMessage(request, "ɾ�������ֵ�ʧ�ܣ�" + e.getMessage(), e);
		}
		return list(request, response);
	}

	// ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setItsv_dictionaryService(
			Itsv_dictionaryService itsv_dictionaryService) {
		this.itsv_dictionaryService = itsv_dictionaryService;
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