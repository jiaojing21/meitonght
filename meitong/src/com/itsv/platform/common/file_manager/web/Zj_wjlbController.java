package com.itsv.platform.common.file_manager.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.common.fileMgr.vo.ZjWjlb;
import com.itsv.platform.common.file_manager.bo.Zj_wjlbService;

/**
 * 说明：增加，修改，删除存储类别信息的前端处理类
 * 
 * @author milu
 * @since 2007-07-17
 * @version 1.0
 */
public class Zj_wjlbController extends BaseCURDController<ZjWjlb> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(Zj_wjlbController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.ZjWjlb";

	private Zj_wjlbService zj_wjlbService; // 逻辑层对象

	// 覆盖父类方法，默认执行query()，分页显示数据
	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// 实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		ZjWjlb ZjWjlb = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			ZjWjlb = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", ZjWjlb);
		} else {
			ZjWjlb = new ZjWjlb();
		}

		this.zj_wjlbService.queryByVO(records, ZjWjlb);
	}

	// 显示增加存储类别信息页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// 显示修改存储类别信息页面前，准备数据
	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id;
		try {
			id = ServletRequestUtils.getStringParameter(request, "p_id");
			ZjWjlb ZjWjlb = this.zj_wjlbService.queryById(id);
			if (null == ZjWjlb) {
				showMessage(request, "未找到对应的存储类别信息记录。请重试");
				mnv = query(request, response);
			} else {
				mnv.addObject(WebConfig.DATA_NAME, ZjWjlb);
			}			
			
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存新增存储类别信息
	 */
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		ZjWjlb ZjWjlb = null;
		try {
			ZjWjlb = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, ZjWjlb)) {
			// return new ModelAndView(getAddView(), WebConfig.DATA_NAME,
			// ZjWjlb);
			// }

			this.zj_wjlbService.add(ZjWjlb);

			showMessage(request, "新增存储类别信息成功");
		} catch (AppException e) {
			logger.error("新增存储类别信息[" + ZjWjlb + "]失败", e);
			showMessage(request, "新增存储类别信息失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给存储类别信息
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ZjWjlb);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的存储类别信息
	 */
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		ZjWjlb ZjWjlb = null;
		try {
			ZjWjlb = param2Object(request);

			// 由于数据校验存在问题，暂不启用服务端的数据校验功能
			// Ace8 2006.9.10
			// 数据校验，如失败直接返回
			// if (!validate(request, ZjWjlb)) {
			// return edit(request, response);
			// }

			this.zj_wjlbService.update(ZjWjlb);
			showMessage(request, "修改存储类别信息成功");
		} catch (AppException e) {
			logger.error("修改存储类别信息[" + ZjWjlb + "]失败", e);
			showMessage(request, "修改存储类别信息失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的存储类别信息
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {

		String[] zj_wjlbs = ServletRequestUtils
				.getStringParameters(request, "p_id");
		// 允许部分删除成功
		try {
			for (String id : zj_wjlbs) {
				this.zj_wjlbService.delete(id);
			}
			showMessage(request, "删除存储类别信息成功");
		} catch (AppException e) {
			logger.error("批量删除存储类别信息时失败", e);
			showMessage(request, "删除存储类别信息失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setZj_wjlbService(Zj_wjlbService zj_wjlbService) {
		this.zj_wjlbService = zj_wjlbService;
	}
}