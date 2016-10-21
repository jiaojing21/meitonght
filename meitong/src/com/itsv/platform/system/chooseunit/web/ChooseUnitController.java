package com.itsv.platform.system.chooseunit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.web.TreeConfig;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.system.chooseunit.bo.ChooseUnitService;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;

/**
 * ˵������ѯ��λ��ǰ�˴�����.
 * 
 * �б����ݲ���ѯ���ݿ⣬���Ǵӻ����ж�ȡ��
 * 
 * @author sgb 2007-7-04
 */
public class ChooseUnitController extends BaseCURDController<ChooseUnit> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(ChooseUnitController.class);

	private ChooseUnitService unitService; //�߼������

	private String chooseUnit; //����ѡ��ѡ��λ��ҳ��
	private String showRadioUnit; //ѡ��ѡ��λ��ҳ��
	private String showCheckUnit; //ѡ���ѡ��λ��ҳ��
	
	//�����˵��������������Ϣ
	private TreeConfig getUnitTreeConfig() {
		//������״�б��������Ϣ
		TreeConfig meta = new TreeConfig();
		meta.setTitle("���ŵ�λ");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** ����Ϊset,get���� */
	public void setUnitService(ChooseUnitService unitService) {
		this.unitService = unitService;
	}
	
	/**
	 * sgb 070629 add
	 * ���뵽ѡ��ѡ��λ��ҳ��
	 */
	public ModelAndView chooseUnit(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getChooseUnit());
		beforeShowList(request, response, mnv);
		return mnv;
	}
	/**
	 * sgb 070629 add
	 */
	public String getChooseUnit() {
		return chooseUnit;
	}
	/**
	 * sgb 070629 add
	 */
	public void setChooseUnit(String chooseUnit) {
		this.chooseUnit = chooseUnit;
	}
	/**
	 * sgb 070629 add
	 * ��ҳ����ʾ��ѡ��λ��
	 */
	public ModelAndView showRadioUnit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getShowRadioUnit());

		//������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		//������״��ʽ�����б����ݡ��ӻ�����ȡ��
		//mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));
		mnv.addObject(WebConfig.DATA_NAME, this.unitService.queryEnabledUnit());

		return mnv;
	}
	/**
	 * sgb 070629 add
	 */
	public String getShowRadioUnit() {
		return showRadioUnit;
	}
	/**
	 * sgb 070629 add
	 */
	public void setShowRadioUnit(String showRadioUnit) {
		this.showRadioUnit = showRadioUnit;
	}
	/**
	 * sgb 070629 add
	 * ��ҳ����ʾ��ѡ��λ��
	 */
	public ModelAndView showCheckUnit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getShowCheckUnit());

		//������״��ʽԪ����
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		//������״��ʽ�����б����ݡ��ӻ�����ȡ��
		//mnv.addObject(WebConfig.DATA_NAME, MirrorCacheTool.getAll("unit"));
		mnv.addObject(WebConfig.DATA_NAME, this.unitService.queryEnabledUnit());

		return mnv;
	}
	/**
	 * sgb 070629 add
	 */
	public String getShowCheckUnit() {
		return showCheckUnit;
	}
	/**
	 * sgb 070629 add
	 */
	public void setShowCheckUnit(String showCheckUnit) {
		this.showCheckUnit = showCheckUnit;
	}
}