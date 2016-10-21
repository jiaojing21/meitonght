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
 * 说明：增加，修改，删除单位的前端处理类.
 * 
 * 列表数据不查询数据库，而是从缓存中读取。
 * 
 * @author admin 2005-1-26
 */
public class UnitController extends BaseCURDController<Unit> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(UnitController.class);

	private UnitService unitService; //逻辑层对象

	private String treeView; //树状列表的视图

	/**
	 * 在页面左侧显示单位树
	 */
	public ModelAndView showTree(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getTreeView());

		//设置树状显式元数据
		mnv.addObject(WebConfig.META_NAME, getUnitTreeConfig());
		//设置树状显式具体列表数据。从缓存里取数
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
	
	//显示修改页面前，先取出对应单位对象
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
			showMessage(request, "未找到对应的单位记录。请重试");
			mnv.setViewName(getAddView());
		} else {
			mnv.addObject(WebConfig.DATA_NAME, unit);
		}
	}

	/**
	 * 保存新增单位
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Unit unit = null;
		try {
			unit = param2Object(request);
			if(unit.getParentid() != null){
	            Unit qryunit = this.unitService.queryById(unit.getParentid());
	            if(qryunit != null && qryunit.getIdClass() >= 8){
	            	showMessage(request, "只能增加八级和八级以下单位");
	            	mnv.addObject(WebConfig.DATA_NAME, unit);
				}else{
					this.unitService.add(unit);
					showMessage(request, "新增单位成功");
				}
			}else{
				this.unitService.add(unit);
				showMessage(request, "新增单位成功");
			}
		} catch (AppException e) {
			logger.error("新增单位[" + unit + "]失败", e);
			showMessage(request, "新增单位失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给用户
			mnv.addObject(WebConfig.DATA_NAME, unit);
		}

		return mnv;
	}

	/**
	 * 保存修改的单位信息
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView(getAddView());
		Unit unit = null;
		try {
			unit = param2Object(request);
			this.unitService.update(unit);

			showMessage(request, "修改单位成功");
		} catch (AppException e) {
			logger.error("修改单位[" + unit + "]失败", e);
			showMessage(request, "修改单位失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面，因为页面的隐含字段p_others.strParam1需要填充正确的值
			return edit(request, response);
		}

		return mnv;
	}

	/**
	 * 删除选中的单位
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String key = "";
		
		try {
			key = ServletRequestUtils.getStringParameter(request, "p_Unit_id");
			this.unitService.delete(key);
		} catch (AppException e) {
			logger.error("删除单位[" + key + "]失败", e);
			showMessage(request, "删除单位失败：" + e.getMessage(), e);

			//重新显示到编辑页面
			return edit(request, response);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showMessage(request, "删除单单位成功");
		return new ModelAndView(getAddView());
	}

	//创建菜单对象的树配置信息
	private TreeConfig getUnitTreeConfig() {
		//设置树状列表的配置信息
		TreeConfig meta = new TreeConfig();
		meta.setTitle("单位列表");
		meta.setShowCode(true);
		meta.setCodeProp("code");
		meta.setTextProp("name");
		meta.setLevelProp("idClass");
		meta.setRemark1Prop("id");
		meta.setStatusProp("enabled");

		return meta;
	}

	/** 以下为set,get方法 */
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