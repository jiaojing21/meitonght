package com.itsv.annotation.refund.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.refund.bo.RefundService;
import com.itsv.annotation.refund.vo.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除refund的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class RefundController extends BaseAnnotationController<Refund> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(RefundController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.refund";
	@Autowired
	private RefundService refundService; //逻辑层对象

	public RefundController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/refund/add");
		super.setIndexView("meitong/refund/index");
		super.setEditView("meitong/refund/edit");

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
		Refund refund = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			refund = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", refund);
		} else {
			refund = new Refund();
		}

		this.refundService.queryByVO(records, refund);
	}

	//显示增加refund页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改refund页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Refund refund = this.refundService.queryById(id);
		if (null == refund) {
			showMessage(request, "未找到对应的refund记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, refund);
		}
	}
	//查询前数据
	@RequestMapping("/refund.query.htm")
	public ModelAndView queryRefund(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//查询列表
	@RequestMapping("/refund.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexAppControl(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		String shmc = "";
		try {
			shmc = java.net.URLDecoder
					.decode(ServletRequestUtils.getStringParameter(request,
							"shmc", ""), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Refund refund = new Refund();
		refund.setProposer(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"refund");
		this.refundService.queryByVO(records, refund);
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	//添加前数据准备
	@RequestMapping("/refund.beforeadd.htm")
	public ModelAndView beforeAdd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	public static String lpad(int length, int number) {
	      String f = "%0" + length + "d";
	      return String.format(f, number);
	  }	
	/**
	 * 保存新增refund
	 */
	@RequestMapping("/refund.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName(getAddView());
		Refund refund = null;
		try {
			int ornum = 1;
			refund = param2Object(request);
			String no = lpad(8, ornum+1);
			String refundNumber = "MTMO"+new Date().getTime()+no;
			refund.setRefundTime(new Date());
			refund.setFlag("0");
			refund.setRefundNumber(refundNumber);
			this.refundService.add(refund);

			showMessage(request, "新增退换货成功");
		} catch (AppException e) {
			logger.error("新增退换货[" + refund + "]失败", e);
			showMessage(request, "新增退换货失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给refund
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, refund);
		}

		return mnv;
	}

	/**
	 * 保存修改的refund
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Refund refund = null;
		try {
			refund = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, refund)) {
			//	return edit(request, response);
			//}

			this.refundService.update(refund);
			showMessage(request, "修改refund成功");
		} catch (AppException e) {
			logger.error("修改refund[" + refund + "]失败", e);
			showMessage(request, "修改refund失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的refund
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] refunds = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : refunds) {
				this.refundService.delete(id);
			}
			showMessage(request, "删除refund成功");
		} catch (AppException e) {
			logger.error("批量删除refund时失败", e);
			showMessage(request, "删除refund失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}
	
	//操作参数
	@RequestMapping("/refund.opt.htm")
	@ResponseBody
	public String opt(HttpServletRequest request,HttpServletResponse response,String id){
		String res = "";
		Refund refund = this.refundService.queryById(id);
		res = refund.getFlag();
		return res;
	}
	
	//退换货单状态修改
	@RequestMapping("/refund.save.htm")
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response,String id){
		String res = "";
		try{
			Refund refund = this.refundService.queryById(id);
			refund.setFlag("1");
			this.refundService.update(refund);
			res = "更改成功";
		}catch (Exception e) {
			// TODO: handle exception
			res = "更改失败";
		}
		return res;
	}
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}
}