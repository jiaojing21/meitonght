package com.itsv.annotation.customer.web;

import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;


import com.itsv.annotation.customer.bo.CustomerService;
import com.itsv.annotation.customer.vo.Customer;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;



/**
 * 说明：增加，修改，删除customer的前端处理类
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
@Controller
public class CustomerController extends BaseAnnotationController<Customer> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(CustomerController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.customer";
	@Autowired
	private CustomerService customerService; //逻辑层对象

	public CustomerController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/customer/add");
		super.setIndexView("meitong/customer/index");
		super.setEditView("meitong/customer/edit");

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
		Customer customer = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			customer = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", customer);
		} else {
			customer = new Customer();
		}

		this.customerService.queryByVO(records, customer);
	}

	//显示增加customer页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改customer页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Customer customer = this.customerService.queryById(id);
		if (null == customer) {
			showMessage(request, "未找到对应的customer记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, customer);
		}
	}
	//查询数据准备
		@RequestMapping("/customer.query.htm")
		public ModelAndView queryBrand(HttpServletRequest request,HttpServletResponse response, ModelAndView mnv){
			mnv.setViewName(getIndexView());
			return mnv;
		}
		//查询列表
		@RequestMapping("/customer.BeforeData.htm")
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
			Customer customer = new Customer();
			customer.setRealname(shmc);
			CachePagedList records = PagedListTool.getEuiPagedList(request,
					"customer");
			this.customerService.queryByVO(records, customer);
			map.put("total", records.getTotalNum());
			map.put("rows", records.getSource());
			return ResponseUtils.sendMap(map);
		}
		
		//查询所有数据
		@RequestMapping("/customer.BeforeALLData.htm")
		@ResponseBody
		public List<Customer> allDataControl(HttpServletRequest request,
				HttpServletResponse response) throws AppException {
			List<Customer> cuslist = this.customerService.queryAll();
			return cuslist;
		}
	/**
	 * 保存新增customer
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = null;
		try {
			customer = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, customer)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, customer);
			//}

			this.customerService.add(customer);

			showMessage(request, "新增customer成功");
		} catch (AppException e) {
			logger.error("新增customer[" + customer + "]失败", e);
			showMessage(request, "新增customer失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给customer
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, customer);
		}

		return query(request, response);
	}
	//修改前数据准备
	@RequestMapping("/customer.editData.htm")
	public ModelAndView editData(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String customerId = "";
		try {
			customerId = ServletRequestUtils.getStringParameter(request, "customerid");
			Customer customer = customerService.queryById(customerId);
			mnv.addObject("data",customer);
			mnv.setViewName(getEditView());
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mnv;
	}
	/**
	 * 保存修改的customer
	 */
	@RequestMapping("/customer.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = null;
		try {
			customer = param2Object(request);
			String sex = request.getParameter("p_Customer_sex");
			if(sex==null){
				customer.setSex("2");
			}
			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, customer)) {
			//	return edit(request, response);
			//}

			this.customerService.update(customer);
			showMessage(request, "修改客户成功");
		} catch (AppException e) {
			logger.error("修改客户[" + customer + "]失败", e);
			showMessage(request, "修改客户失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, customer);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, customer);	}

	/**
	 * 删除选中的customer
	 */
	@RequestMapping("/customer.delete.htm")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response,@RequestParam String pid) {
		String result = "success";
		try {
			String [] customers = pid.split(",");
			for (String id : customers) {
				this.customerService.delete(id);
			}
			showMessage(request, "删除客户成功");
		} catch (AppException e) {
			logger.error("批量删除客户时失败", e);
			showMessage(request, "删除客户失败：" + e.getMessage(), e);
		}
		
		return result;
	}
	//详情
	@RequestMapping("/customer.detail.htm")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv){
		String id ="";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Customer customer = this.customerService.queryById(id);
			mnv.addObject("data", customer);
			mnv.setViewName("/meitong/customer/detail");
		}else{
			showMessage(request, "没有查到该数据");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
}