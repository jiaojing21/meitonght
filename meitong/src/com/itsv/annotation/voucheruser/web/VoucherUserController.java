package com.itsv.annotation.voucheruser.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

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

import com.itsv.annotation.voucheruser.bo.VoucherUserService;
import com.itsv.annotation.voucheruser.vo.VoucherUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除用户代金券表的前端处理类
 * 
 * @author yfh
 * @since 2016-04-21
 * @version 1.0
 */
@Controller
@RequestMapping("/voucheruser.voucherUser.vsf")
public class VoucherUserController extends BaseAnnotationController<VoucherUser> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(VoucherUserController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.voucherUser";
	@Autowired
	private VoucherUserService voucherUserService; //逻辑层对象

	public VoucherUserController(){

		super.setDefaultCheckToken(true);
		super.setAddView("voucheruser/voucherUser/add");
		super.setIndexView("voucheruser/voucherUser/index");
		super.setEditView("voucheruser/voucherUser/edit");

	}

  /**
   * 注册自定义类型转换类，用来转换日期对象
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
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
		VoucherUser voucherUser = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			voucherUser = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", voucherUser);
		} else {
			voucherUser = new VoucherUser();
		}

		this.voucherUserService.queryByVO(records, voucherUser);
	}

	//显示增加用户代金券表页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改用户代金券表页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		VoucherUser voucherUser = this.voucherUserService.queryById(id);
		if (null == voucherUser) {
			showMessage(request, "未找到对应的用户代金券表记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, voucherUser);
		}
	}

	/**
	 * 保存新增用户代金券表
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		VoucherUser voucherUser = null;
		try {
			voucherUser = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, voucherUser)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherUser);
			//}

			this.voucherUserService.add(voucherUser);

			showMessage(request, "新增用户代金券表成功");
		} catch (AppException e) {
			logger.error("新增用户代金券表[" + voucherUser + "]失败", e);
			showMessage(request, "新增用户代金券表失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给用户代金券表
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucherUser);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的用户代金券表
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		VoucherUser voucherUser = null;
		try {
			voucherUser = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, voucherUser)) {
			//	return edit(request, response);
			//}

			this.voucherUserService.update(voucherUser);
			showMessage(request, "修改用户代金券表成功");
		} catch (AppException e) {
			logger.error("修改用户代金券表[" + voucherUser + "]失败", e);
			showMessage(request, "修改用户代金券表失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的用户代金券表
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] voucherUsers = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : voucherUsers) {
				this.voucherUserService.delete(id);
			}
			showMessage(request, "删除用户代金券表成功");
		} catch (AppException e) {
			logger.error("批量删除用户代金券表时失败", e);
			showMessage(request, "删除用户代金券表失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setVoucherUserService(VoucherUserService voucherUserService) {
		this.voucherUserService = voucherUserService;
	}
}