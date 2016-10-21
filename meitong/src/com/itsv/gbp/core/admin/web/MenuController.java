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
 * ˵�������ӣ��޸ģ�ɾ���˵���ǰ�˴����ࡣ<br>
 * 
 * ��Ҫ��ʾ��
 * <ol>
 * <li>ͨ��TreeConfig���󣬶�����״�б����ʽ��</li>
 * <li>ͨ��beforeShowXX()������Ϊ�����ʾҳ��׼�����ݡ�</li>
 * <li>ʵ��saveXX()������������Զ������ɾ�Ĳ�����</li>
 * <li>ͨ��ServletRequestUtils�࣬����ػ�ȡ��ز�����</li>
 * <li>ͨ��param2Object()�������������Ĳ������������</li>
 * </ol>
 * 
 * @author admin 2005-1-26
 */
public class MenuController extends BaseCURDController<Menu> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(MenuController.class);

	private MenuService menuService; //�߼������

	private String treeView; //��״�б����ͼ

	/**
	 * ��ҳ�������ʾ�˵���
	 */
	public ModelAndView showTree(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		//������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getMenuTreeConfig());
		//������״��ʽ�����б�����
		mnv.addObject(WebConfig.DATA_NAME, this.menuService.queryAll());

		return mnv;
	}

	//��ʾ�޸�ҳ��ǰ����ȡ����Ӧ�˵�����
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
			showMessage(request, "δ�ҵ���Ӧ�Ĳ˵���¼��������");
			mnv.setViewName(getAddView());
		} else {
			mnv.addObject(WebConfig.DATA_NAME, menu);
		}
	}

	/**
	 * ���������˵�
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Menu menu = null;
		try {
			menu = param2Object(request);
			this.menuService.add(menu);

			showMessage(request, "�����˵��ɹ�");
		} catch (AppException e) {
			logger.error("�����˵�[" + menu + "]ʧ��", e);
			showMessage(request, "�����˵�ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ���û�
			mnv.addObject(WebConfig.DATA_NAME, menu);
		}

		return mnv;
	}

	/**
	 * �����޸ĵĲ˵���Ϣ
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Menu menu = null;
		try {
			menu = param2Object(request);
			this.menuService.update(menu);

			showMessage(request, "�޸Ĳ˵��ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ĳ˵�[" + menu + "]ʧ��", e);
			showMessage(request, "�޸Ĳ˵�ʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ�棬��Ϊҳ��������ֶ�p_others.strParam1��Ҫ�����ȷ��ֵ
			return edit(request, response);
		}

		return mnv;
	}

	/**
	 * ɾ��ѡ�еĲ˵�
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
			logger.error("ɾ���˵�[" + key + "]ʧ��", e);

			//������ʾ���༭ҳ��
			Menu menu = this.menuService.queryById(key);
			ModelAndView mnv = new ModelAndView(); 
			if (null == menu) {
				showMessage(request, "δ�ҵ���Ӧ�Ĳ˵���¼��������");
				mnv.setViewName(getAddView());
			} else {
				mnv.setViewName(getEditView());
				mnv.addObject(WebConfig.DATA_NAME, menu);
			}
			showMessage(request, "ɾ���˵�ʧ�ܣ�" + e.getMessage(), e);
			return mnv;			
		}

		showMessage(request, "ɾ�����˵��ɹ�");
		return new ModelAndView(getAddView());
	}

	//�����˵��������������Ϣ
	private TreeConfig getMenuTreeConfig() {
		//������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta.setTitle("�˵��б�");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** ����Ϊset,get���� */
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