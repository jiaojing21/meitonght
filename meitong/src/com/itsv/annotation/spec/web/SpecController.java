package com.itsv.annotation.spec.web;

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

import com.itsv.annotation.spec.bo.SpecService;
import com.itsv.annotation.spec.vo.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除spec的前端处理类
 * 
 * @author swk
 * @since 2016-04-01
 * @version 1.0
 */
@Controller
@RequestMapping("/spec.spec.vsf")
public class SpecController extends BaseAnnotationController<Spec> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(SpecController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.spec";
	@Autowired
	private SpecService specService; //逻辑层对象

	public SpecController(){

		super.setDefaultCheckToken(true);
		super.setAddView("spec/spec/add");
		super.setIndexView("spec/spec/index");
		super.setEditView("spec/spec/edit");

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
		Spec spec = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			spec = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", spec);
		} else {
			spec = new Spec();
		}

		this.specService.queryByVO(records, spec);
	}

	//显示增加spec页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改spec页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Spec spec = this.specService.queryById(id);
		if (null == spec) {
			showMessage(request, "未找到对应的spec记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, spec);
		}
	}

	/**
	 * 保存新增spec
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Spec spec = null;
		try {
			spec = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, spec)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, spec);
			//}

			this.specService.add(spec);

			showMessage(request, "新增spec成功");
		} catch (AppException e) {
			logger.error("新增spec[" + spec + "]失败", e);
			showMessage(request, "新增spec失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给spec
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, spec);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的spec
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Spec spec = null;
		try {
			spec = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, spec)) {
			//	return edit(request, response);
			//}

			this.specService.update(spec);
			showMessage(request, "修改spec成功");
		} catch (AppException e) {
			logger.error("修改spec[" + spec + "]失败", e);
			showMessage(request, "修改spec失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的spec
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] specs = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : specs) {
				this.specService.delete(id);
			}
			showMessage(request, "删除spec成功");
		} catch (AppException e) {
			logger.error("批量删除spec时失败", e);
			showMessage(request, "删除spec失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}
}