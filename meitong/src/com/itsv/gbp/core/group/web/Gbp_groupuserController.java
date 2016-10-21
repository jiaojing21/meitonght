package com.itsv.gbp.core.group.web;

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

import com.itsv.gbp.core.group.bo.Gbp_groupuserService;
import com.itsv.gbp.core.group.vo.Gbp_groupuser;

/**
 * 说明：增加，修改，删除gbp_groupuser的前端处理类
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupuserController extends BaseCURDController<Gbp_groupuser> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(Gbp_groupuserController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.gbp_groupuser";

	private Gbp_groupuserService gbp_groupuserService; //逻辑层对象

	//覆盖父类方法，默认执行query()，分页显示数据
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Gbp_groupuser gbp_groupuser = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			gbp_groupuser = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", gbp_groupuser);
		} else {
			gbp_groupuser = new Gbp_groupuser();
		}

		this.gbp_groupuserService.queryByVO(records, gbp_groupuser);
	}

	//显示增加gbp_groupuser页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改gbp_groupuser页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		long id = ServletRequestUtils.getLongParameter(request, "p_id", -1);
		Gbp_groupuser gbp_groupuser = this.gbp_groupuserService.queryById(id);
		if (null == gbp_groupuser) {
			showMessage(request, "未找到对应的gbp_groupuser记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, gbp_groupuser);
		}
	}

	/**
	 * 保存新增gbp_groupuser
	 */
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Gbp_groupuser gbp_groupuser = null;
		try {
			gbp_groupuser = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ZhengWangli 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, gbp_groupuser)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, gbp_groupuser);
			//}

			this.gbp_groupuserService.add(gbp_groupuser);

			showMessage(request, "新增gbp_groupuser成功");
		} catch (AppException e) {
			logger.error("新增gbp_groupuser[" + gbp_groupuser + "]失败", e);
			showMessage(request, "新增gbp_groupuser失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给gbp_groupuser
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, gbp_groupuser);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的gbp_groupuser
	 */
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Gbp_groupuser gbp_groupuser = null;
		try {
			gbp_groupuser = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ZhengWangli 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, gbp_groupuser)) {
			//	return edit(request, response);
			//}

			this.gbp_groupuserService.update(gbp_groupuser);
			showMessage(request, "修改gbp_groupuser成功");
		} catch (AppException e) {
			logger.error("修改gbp_groupuser[" + gbp_groupuser + "]失败", e);
			showMessage(request, "修改gbp_groupuser失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的gbp_groupuser
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		long[] gbp_groupusers = ServletRequestUtils.getLongParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (long id : gbp_groupusers) {
				this.gbp_groupuserService.delete(id);
			}
			showMessage(request, "删除gbp_groupuser成功");
		} catch (AppException e) {
			logger.error("批量删除gbp_groupuser时失败", e);
			showMessage(request, "删除gbp_groupuser失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setGbp_groupuserService(Gbp_groupuserService gbp_groupuserService) {
		this.gbp_groupuserService = gbp_groupuserService;
	}
}