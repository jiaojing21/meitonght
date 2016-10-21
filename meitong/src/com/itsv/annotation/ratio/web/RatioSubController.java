package com.itsv.annotation.ratio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.annotation.ratio.bo.RatioSubService;
import com.itsv.annotation.ratio.vo.RatioSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除比例图子表的前端处理类
 * 
 * @author quyf
 * @since 2014-12-30
 * @version 1.0
 */
@Controller
@RequestMapping("/ratio.ratioSub.vsf")
public class RatioSubController extends BaseAnnotationController<RatioSub> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(RatioSubController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.ratioSub";
	@Autowired
	private RatioSubService ratioSubService; //逻辑层对象

	public RatioSubController(){

		super.setDefaultCheckToken(true);
		super.setAddView("ratio/ratioSub/add");
		super.setIndexView("ratio/ratioSub/index");
		super.setEditView("ratio/ratioSub/edit");

	}

	//覆盖父类方法，默认执行query()，分页显示数据

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		RatioSub ratioSub = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			ratioSub = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", ratioSub);
		} else {
			ratioSub = new RatioSub();
		}

		this.ratioSubService.queryByVO(records, ratioSub);
	}

	//显示增加比例图子表页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改比例图子表页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		RatioSub ratioSub = this.ratioSubService.queryById(id);
		if (null == ratioSub) {
			showMessage(request, "未找到对应的比例图子表记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, ratioSub);
		}
	}

	/**
	 * 保存新增比例图子表
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		RatioSub ratioSub = null;
		try {
			ratioSub = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, ratioSub)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ratioSub);
			//}

			this.ratioSubService.add(ratioSub);

			showMessage(request, "新增比例图子表成功");
		} catch (AppException e) {
			logger.error("新增比例图子表[" + ratioSub + "]失败", e);
			showMessage(request, "新增比例图子表失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给比例图子表
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ratioSub);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的比例图子表
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		RatioSub ratioSub = null;
		try {
			ratioSub = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, ratioSub)) {
			//	return edit(request, response);
			//}

			this.ratioSubService.update(ratioSub);
			showMessage(request, "修改比例图子表成功");
		} catch (AppException e) {
			logger.error("修改比例图子表[" + ratioSub + "]失败", e);
			showMessage(request, "修改比例图子表失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的比例图子表
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] ratioSubs = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : ratioSubs) {
				this.ratioSubService.delete(id);
			}
			showMessage(request, "删除比例图子表成功");
		} catch (AppException e) {
			logger.error("批量删除比例图子表时失败", e);
			showMessage(request, "删除比例图子表失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setRatioSubService(RatioSubService ratioSubService) {
		this.ratioSubService = ratioSubService;
	}
}