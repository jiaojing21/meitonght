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
 * 说明：查询单位的前端处理类.
 * 
 * 列表数据不查询数据库，而是从缓存中读取。
 * 
 * @author sgb 2007-7-04
 */
public class ChooseUnitController extends BaseCURDController<ChooseUnit> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(ChooseUnitController.class);

	private ChooseUnitService unitService; //逻辑层对象

	private String chooseUnit; //调用选择单选单位的页面
	private String showRadioUnit; //选择单选单位的页面
	private String showCheckUnit; //选择多选单位的页面
	
	//创建菜单对象的树配置信息
	private TreeConfig getUnitTreeConfig() {
		//设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta.setTitle("部门单位");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** 以下为set,get方法 */
	public void setUnitService(ChooseUnitService unitService) {
		this.unitService = unitService;
	}
	
	/**
	 * sgb 070629 add
	 * 进入到选择单选单位树页面
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
	 * 在页面显示单选单位树
	 */
	public ModelAndView showRadioUnit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getShowRadioUnit());

		//设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		//设置树状显式具体列表数据。从缓存里取数
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
	 * 在页面显示单选单位树
	 */
	public ModelAndView showCheckUnit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getShowCheckUnit());

		//设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		//设置树状显式具体列表数据。从缓存里取数
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