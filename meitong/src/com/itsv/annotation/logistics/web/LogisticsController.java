package com.itsv.annotation.logistics.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.logistics.bo.LogisticsService;
import com.itsv.annotation.logistics.vo.Logistics;
import com.itsv.annotation.orders.bo.OrdersService;
import com.itsv.annotation.orders.vo.Orders;
import com.itsv.annotation.util.ParseXML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除logistics的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class LogisticsController extends BaseAnnotationController<Logistics> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(LogisticsController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.logistics";
	@Autowired
	private LogisticsService logisticsService; //逻辑层对象

	@Autowired
	private OrdersService ordersService; //逻辑层对象
	
	public LogisticsController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/logistics/add");
		super.setIndexView("meitong/logistics/index");
		super.setEditView("meitong/logistics/edit");

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
		Logistics logistics = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			logistics = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", logistics);
		} else {
			logistics = new Logistics();
		}

		this.logisticsService.queryByVO(records, logistics);
	}

	//显示增加logistics页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}
	//查询数据数据准备
	@RequestMapping("logistics.query.htm")
	public ModelAndView queryLogistics(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//查询物流列表
	@RequestMapping("/logistics.BeforeData.htm")
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
		Logistics logistics = new Logistics();
		logistics.setName(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"logistics");
		this.logisticsService.queryByVO(records,logistics);
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	//显示修改logistics页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Logistics logistics = this.logisticsService.queryById(id);
		if (null == logistics) {
			showMessage(request, "未找到对应的logistics记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, logistics);
		}
	}
	//新增数据准备
	@RequestMapping("/logistics.beforeadd.htm")
	public ModelAndView beforeAdd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		try {
			String id = ServletRequestUtils.getStringParameter(request, "pid");
			ParseXML px = new ParseXML();
			List<Map<String,String>> list = px.WL;
			mnv.addObject("list",list);
			if(id!=""&&id!=null){
				Orders orders =this.ordersService.queryById(id);
				mnv.addObject("orders", orders);
				mnv.setViewName("/meitong/logistics/add");
			}else{
				showMessage(request, "没有查到该数据");			
				mnv.setViewName("admin/message");
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return mnv;
	}
	public static String lpad(int length, int number) {
	      String f = "%0" + length + "d";
	      return String.format(f, number);
	  }	
	/**
	 * 保存新增logistics
	 */
	@RequestMapping("/logistics.saveadd.htm")
	@ResponseBody
	public String saveAdd(HttpServletRequest request,@RequestParam String id,@RequestParam String orderNumber,@RequestParam String name,@RequestParam String logisticsNumber) {
		String result = "success";
		try {
			Orders orders = this.ordersService.queryById(id);
			orders.setStatus("3");
			this.ordersService.update(orders);
			Logistics logistics = new Logistics();
			logistics.setLogisticsNumber(logisticsNumber);
			logistics.setOrderNumber(orderNumber);
			logistics.setName(name);
			String logisname = logistics.getName();
			ParseXML px = new ParseXML();
			List<Map<String,String>> list = px.WL;
			String lgoiscode = "";
			for(Map<String,String> map : list){
				if(logisname.equals(map.get("code"))){
					logisname = map.get("name");
					lgoiscode = map.get("code");
					break;
				}
			}
			logistics.setCode(lgoiscode);
			logistics.setName(logisname);
			this.logisticsService.add(logistics);
		} catch (Exception e) {
			result = "error";
			logger.error("新增物流[ logistics ]失败", e);
		}
		return result;
	}

	/**
	 * 保存修改的logistics
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Logistics logistics = null;
		try {
			logistics = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, logistics)) {
			//	return edit(request, response);
			//}

			this.logisticsService.update(logistics);
			showMessage(request, "修改logistics成功");
		} catch (AppException e) {
			logger.error("修改logistics[" + logistics + "]失败", e);
			showMessage(request, "修改logistics失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的logistics
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] logisticss = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : logisticss) {
				this.logisticsService.delete(id);
			}
			showMessage(request, "删除logistics成功");
		} catch (AppException e) {
			logger.error("批量删除logistics时失败", e);
			showMessage(request, "删除logistics失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	// 查询订单号是否存在
	@RequestMapping("/logictics.logisticsOnlyDdh.htm")
	@ResponseBody
	public int dirbh(HttpServletRequest request, HttpServletResponse response,
			String orderNumber) throws AppException {
		int res = 0;
		Logistics logistics = new Logistics();
		logistics.setOrderNumber(orderNumber);
		List<Logistics> loglist = this.logisticsService.queryByVO(logistics);
		if (loglist.size() > 0) {
			res = loglist.size();
		}
		return res;
	}
	
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setLogisticsService(LogisticsService logisticsService) {
		this.logisticsService = logisticsService;
	}
}