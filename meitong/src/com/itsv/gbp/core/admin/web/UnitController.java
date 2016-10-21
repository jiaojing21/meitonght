package com.itsv.gbp.core.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.UnitService;
import com.itsv.gbp.core.admin.vo.Unit;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

/**
 * ˵�������ӣ��޸ģ�ɾ����λ��ǰ�˴�����.
 * 
 * �б����ݲ���ѯ���ݿ⣬���Ǵӻ����ж�ȡ��
 * 
 * @author admin 2005-1-26
 */
public class UnitController extends BaseCURDController<Unit> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(UnitController.class);

	private UnitService unitService; //�߼������

	private String treeView; //��״�б����ͼ

	/**
	 * ��ҳ�������ʾ��λ��
	 */
	public ModelAndView showTree(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		//������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		//������״��ʽ�����б����ݡ��ӻ�����ȡ��
		mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));

		return mnv;
	}

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		Unit unit = null;
		try {
			unit = param2Object(request);
			Unit newunit = new Unit();
			newunit.setParentid(unit.getId());
			newunit.setParentname(unit.getName());
			mnv.addObject(WebConfig.DATA_NAME, newunit);
		} catch (AppException e) {
		}
	}
	
	//��ʾ�޸�ҳ��ǰ����ȡ����Ӧ��λ����
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String key = "";
		try {
			key = ServletRequestUtils.getStringParameter(request, "p_id");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Unit unit = this.unitService.queryById(key);
		if (null == unit) {
			showMessage(request, "δ�ҵ���Ӧ�ĵ�λ��¼��������");
			mnv.setViewName(getAddView());
		} else {
			mnv.addObject(WebConfig.DATA_NAME, unit);
		}
	}

	/**
	 * ����������λ
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Unit unit = null;
		try {
			unit = param2Object(request);
			if(unit.getParentid() != null){
	            Unit qryunit = this.unitService.queryById(unit.getParentid());
	            if(qryunit != null && qryunit.getIdClass() >= 8){
	            	showMessage(request, "ֻ�����Ӱ˼��Ͱ˼����µ�λ");
	            	mnv.addObject(WebConfig.DATA_NAME, unit);
				}else{
					this.unitService.add(unit);
					showMessage(request, "������λ�ɹ�");
				}
			}else{
				this.unitService.add(unit);
				showMessage(request, "������λ�ɹ�");
			}
		} catch (AppException e) {
			logger.error("������λ[" + unit + "]ʧ��", e);
			showMessage(request, "������λʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ���û�
			mnv.addObject(WebConfig.DATA_NAME, unit);
		}

		return mnv;
	}

	/**
	 * �����޸ĵĵ�λ��Ϣ
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Unit unit = null;
		try {
			unit = param2Object(request);
			this.unitService.update(unit);

			showMessage(request, "�޸ĵ�λ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸ĵ�λ[" + unit + "]ʧ��", e);
			showMessage(request, "�޸ĵ�λʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ�棬��Ϊҳ��������ֶ�p_others.strParam1��Ҫ�����ȷ��ֵ
			return edit(request, response);
		}

		return mnv;
	}

	/**
	 * ɾ��ѡ�еĵ�λ
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String key = "";
		
		try {
			key = ServletRequestUtils.getStringParameter(request, "p_Unit_id");
			this.unitService.delete(key);
		} catch (AppException e) {
			logger.error("ɾ����λ[" + key + "]ʧ��", e);
			showMessage(request, "ɾ����λʧ�ܣ�" + e.getMessage(), e);

			//������ʾ���༭ҳ��
			return edit(request, response);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showMessage(request, "ɾ������λ�ɹ�");
		return new ModelAndView(getAddView());
	}

	//�����˵��������������Ϣ
	private TreeConfig getUnitTreeConfig() {
		//������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta.setTitle("��λ�б�");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** ����Ϊset,get���� */
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public String getTreeView() {
		return treeView;
	}

	public void setTreeView(String treeView) {
		this.treeView = treeView;
	}
}