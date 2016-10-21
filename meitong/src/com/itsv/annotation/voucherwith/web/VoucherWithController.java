package com.itsv.annotation.voucherwith.web;

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

import com.itsv.annotation.voucherwith.bo.VoucherWithService;
import com.itsv.annotation.voucherwith.vo.VoucherWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除代金券详细的前端处理类
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
@Controller
@RequestMapping("/voucherwith.voucherWith.vsf")
public class VoucherWithController extends BaseAnnotationController<VoucherWith> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(VoucherWithController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.voucherWith";
	@Autowired
	private VoucherWithService voucherWithService; //逻辑层对象

	public VoucherWithController(){

		super.setDefaultCheckToken(true);
		super.setAddView("voucherwith/voucherWith/add");
		super.setIndexView("voucherwith/voucherWith/index");
		super.setEditView("voucherwith/voucherWith/edit");

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
		VoucherWith voucherWith = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			voucherWith = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", voucherWith);
		} else {
			voucherWith = new VoucherWith();
		}

		this.voucherWithService.queryByVO(records, voucherWith);
	}

	//显示增加代金券详细页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改代金券详细页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		VoucherWith voucherWith = this.voucherWithService.queryById(id);
		if (null == voucherWith) {
			showMessage(request, "未找到对应的代金券详细记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, voucherWith);
		}
	}

	/**
	 * 保存新增代金券详细
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		VoucherWith voucherWith = null;
		try {
			voucherWith = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, voucherWith)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherWith);
			//}

			this.voucherWithService.add(voucherWith);

			showMessage(request, "新增代金券详细成功");
		} catch (AppException e) {
			logger.error("新增代金券详细[" + voucherWith + "]失败", e);
			showMessage(request, "新增代金券详细失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给代金券详细
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherWith);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的代金券详细
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		VoucherWith voucherWith = null;
		try {
			voucherWith = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, voucherWith)) {
			//	return edit(request, response);
			//}

			this.voucherWithService.update(voucherWith);
			showMessage(request, "修改代金券详细成功");
		} catch (AppException e) {
			logger.error("修改代金券详细[" + voucherWith + "]失败", e);
			showMessage(request, "修改代金券详细失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的代金券详细
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] voucherWiths = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : voucherWiths) {
				this.voucherWithService.delete(id);
			}
			showMessage(request, "删除代金券详细成功");
		} catch (AppException e) {
			logger.error("批量删除代金券详细时失败", e);
			showMessage(request, "删除代金券详细失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setVoucherWithService(VoucherWithService voucherWithService) {
		this.voucherWithService = voucherWithService;
	}
}