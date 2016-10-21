package com.itsv.annotation.orders.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.address.bo.AddressService;
import com.itsv.annotation.address.vo.Address;
import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.goods.bo.GoodsService;
import com.itsv.annotation.goods.vo.Goods;
import com.itsv.annotation.logistics.bo.LogisticsService;
import com.itsv.annotation.logistics.vo.Logistics;
import com.itsv.annotation.orders.bo.OrdersService;
import com.itsv.annotation.orders.vo.Orders;
import com.itsv.annotation.ordersGoods.bo.OrdersGoodsService;
import com.itsv.annotation.ordersGoods.vo.OrdersGoods;
import com.itsv.annotation.region.bo.RegionService;
import com.itsv.annotation.region.vo.Region;
import com.itsv.annotation.spec.bo.SpecService;
import com.itsv.annotation.spec.vo.Spec;
import com.itsv.annotation.util.ParseXML;
import com.itsv.annotation.util.TRACK;
import com.itsv.annotation.voucher.vo.Voucher;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除orders的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class OrdersController extends BaseAnnotationController<Orders> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(OrdersController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.orders";
	@Autowired
	private OrdersService ordersService; //逻辑层对象

	@Autowired
	private OrdersGoodsService ordersGoodsService; //逻辑层对象
	
	@Autowired
	private GoodsService goodsService; //逻辑层对象

	@Autowired
	private RegionService regionService; //逻辑层对象
	
	@Autowired
	private AddressService addressService; //逻辑层对象
	
	@Autowired
	private SpecService specService; //逻辑层对象
	
	@Autowired
	private FileUploadService fileUploadService;//逻辑层对象
	
	@Autowired
	private LogisticsService logisticsService;
	
	public OrdersController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/orders/add");
		super.setIndexView("meitong/orders/index");
		super.setEditView("meitong/orders/edit");

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
		Orders orders = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			orders = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", orders);
		} else {
			orders = new Orders();
		}

		this.ordersService.queryByVO(records, orders);
	}

	//显示增加orders页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改orders页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Orders orders = this.ordersService.queryById(id);
		if (null == orders) {
			showMessage(request, "未找到对应的orders记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, orders);
		}
	}
	@RequestMapping("/orders.query.htm")
	public ModelAndView queryOrders(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	@RequestMapping("/orders.BeforeData.htm")
	@ResponseBody
	public Map<String,Object> indexControl(HttpServletRequest request,HttpServletResponse response) throws AppException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		String shmc = "";
		Address ress = new Address();
		String orderNumber  = "";
		try {
			orderNumber = ServletRequestUtils.getStringParameter(request, "orderNumber");
			shmc = java.net.URLDecoder
					.decode(ServletRequestUtils.getStringParameter(request,
							"shmc", ""), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Orders orders = new Orders();
		orders.setOrderNumber(orderNumber);
		//orders.setAddress(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"orders");
		this.ordersService.queryByVO(records, orders);
		
		for (Orders o : (List<Orders>) records.getSource()) {
			ress = this.addressService.queryById(o.getAddress());
			if(null!=ress){
				List<String> list = new ArrayList<String>();
				Region region = new Region();
				region.setRegionCode(ress.getAddressRegion());
				List<Region> regionrest = this.regionService.queryByVO(region);
				if(regionrest.size()>0){
					region = regionrest.get(0);
					list.add(region.getRegionName());
					while(null!=region&&!region.getParentId().equals("0")){
						region = this.regionService.queryById(region.getParentId());
						list.add(region.getRegionName());
					}
				}
				
				StringBuffer sb = new StringBuffer();
				for(int i = list.size()-1;i >= 0;i--){
					sb.append(list.get(i));
				}
				o.setAddress(sb.toString() + "  " +ress.getAddress());
			}
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	/**
	 * 保存新增orders
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Orders orders = null;
		try {
			orders = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, orders)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, orders);
			//}

			this.ordersService.add(orders);

			showMessage(request, "新增orders成功");
		} catch (AppException e) {
			logger.error("新增orders[" + orders + "]失败", e);
			showMessage(request, "新增orders失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给orders
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, orders);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的orders
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Orders orders = null;
		try {
			orders = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, orders)) {
			//	return edit(request, response);
			//}

			this.ordersService.update(orders);
			showMessage(request, "修改orders成功");
		} catch (AppException e) {
			logger.error("修改orders[" + orders + "]失败", e);
			showMessage(request, "修改orders失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的orders
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] orderss = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : orderss) {
				this.ordersService.delete(id);
			}
			showMessage(request, "删除orders成功");
		} catch (AppException e) {
			logger.error("批量删除orders时失败", e);
			showMessage(request, "删除orders失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}
	@RequestMapping("/orders.detail.htm")
	public ModelAndView detail(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mnv = new ModelAndView();
		String id ="";
		StringBuffer sb = new StringBuffer();
		OrdersGoods ordersGoods = new OrdersGoods();
		List<FileUpload> fulist = new ArrayList<FileUpload>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(id!=null&&id!=""){
			Orders orders = this.ordersService.queryById(id);
			
			String cjsj = sdf.format(orders.getCreatetime());
			String status = "";
			if("0".equals(orders.getStatus())){
				status = "未完成";
			}else if("1".equals(orders.getStatus())){
				status = "已支付";
			}else if("2".equals(orders.getStatus())){
				status = "已确认订单";
			}else if("3".equals(orders.getStatus())){
				status = "已发货";
			}else if("4".equals(orders.getStatus())){
				status = "已完成订单";
			}else if("5".equals(orders.getStatus())){
				status = "已取消订单";
			}
			ordersGoods.setOrderNumber(orders.getOrderNumber());
			FileUpload fileUpload = new FileUpload();
			List<OrdersGoods> list = this.ordersGoodsService.queryByVO(ordersGoods);
			sb.append("<table  align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#69D7E4\" class=\"listWt\" style =\"width:60%;\"> ");
			sb.append("<tr><th colspan=\"4\">订单详情</th></tr>");
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">订单号</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getOrderNumber()+"</td></tr>");	
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">地址</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getAddress()+"</td></tr>");
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">状态</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+status+"</td></tr>");	
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">创建时间</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+cjsj+"</td></tr>");	
			if((!"0".equals(orders.getStatus()))&&(!"1".equals(orders.getStatus()))){
				String zfsj = sdf.format(orders.getPayTime());
				String qrsj = sdf.format(orders.getConfirmatTime());
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">支付方式</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayment()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">支付平台</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayPlatform()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">支付时间</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+zfsj+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">确认时间</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+qrsj+"</td></tr>");	
			}else if("1".equals(orders.getStatus())){
				String zfsj = sdf.format(orders.getPayTime());
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">支付方式</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayment()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">支付平台</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayPlatform()+"</td></tr>");	
				sb .append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">支付时间</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+zfsj+"</td></tr>");	
			}
			for(OrdersGoods og : list){
				Goods goods = this.goodsService.queryById(og.getGoodsId());
				orders = this.ordersService.queryByDdh(og.getGoodsNumber());
				Spec spec = this.specService.queryById(goods.getSpecId());
				fileUpload.setFId(goods.getSpecId());
				fulist = this.fileUploadService.queryByVO(fileUpload);
				StringBuffer sbu = new StringBuffer();
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品名称</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+spec.getSpecName()+"</td></tr>");
				String picstart = "<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品详细</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"; 
				String picend = "</td></tr>";
				for(FileUpload fu : fulist){
					if(fu.getType().equals("0")){
						sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品封面</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\"><img src = \""+fu.getDownloadPath()+"\"></img></td></tr>");
					}else if(fu.getType().equals("1")){
						sbu.append("<img src = \""+fu.getDownloadPath()+"\"></img>");
					}
				}
				if(sbu.toString().length()>0){
					sb.append(picstart+sbu.toString()+picend);
					sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px; \">数量</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+og.getGoodsNumber()+"</td></tr>");
					sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">总价</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+og.getGoodsTotal()+"</td></tr>");	
				}
			}
			sb.append("</table>");		
			try {
				request.setAttribute("body", sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mnv.setViewName("/meitong/orders/detail");
		}else{
			showMessage(request, "没有查到该数据");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	//状态查询
	@RequestMapping("/orders.type.htm")
	@ResponseBody
	public Orders type(HttpServletRequest request,HttpServletResponse response,String pid){
		Orders orders =  this.ordersService.queryById(pid);
		return orders;
	}
	//物流校验订单号
	@RequestMapping("/orders.ordersDirDdh.htm")
	@ResponseBody
	public Orders ddhOnly(final HttpServletRequest request, @RequestParam(value = "orderNumber", required = true) String orderNumber){
		Orders orders =  this.ordersService.queryByDdh(orderNumber);
		return orders;
	}
	
	//操作参数
	@RequestMapping("/orders.opt.htm")
	@ResponseBody
	public String opt(HttpServletRequest request, HttpServletResponse response,String id){
		String res = "";
		Orders orders = this.ordersService.queryById(id);
		res = orders.getStatus();
		return res;
	}
	
	//订单状态的修改
	@RequestMapping("/orders.save.htm")
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response,String id){
		String res = "";
		try{
			Orders orders = this.ordersService.queryById(id);
			if("1".equals(orders.getStatus())){
				orders.setStatus("2");
				orders.setConfirmatTime(new Date());
			}
			this.ordersService.update(orders);
			res = "更改成功";
		}catch (Exception e) {
			// TODO: handle exception
			res ="更改失败";
		}
		return res;
	}
	@RequestMapping("/orders.save1.htm")
	@ResponseBody
	public String save1(HttpServletRequest request,HttpServletResponse response,String id,String opt){
		String res = "";
		try{
			Orders orders = this.ordersService.queryById(id);
			if("3".equals(orders.getStatus())&&"4".equals(opt)){
				orders.setStatus("4");
			}else if("3".equals(orders.getStatus())&&"5".equals(opt)){
				orders.setStatus("5");
			}
			this.ordersService.update(orders);
			res = "更改成功";
		}catch (Exception e) {
			// TODO: handle exception
			res = "更改失败";
		}
		return res;
	}
	
	@RequestMapping("/logistics.view.htm")
	public ModelAndView queryLogistics(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv,String pid){
		Orders order = this.ordersService.queryById(pid);
		JSONObject jo = new JSONObject();
		StringBuffer sb = new StringBuffer();
		if(null!=order){
			Logistics logistics = new Logistics();
			logistics.setOrderNumber(order.getOrderNumber());
			List<Logistics> lgoisticslist = this.logisticsService.queryByVO(logistics);
			Logistics ls = null;
			if(null!=lgoisticslist&&lgoisticslist.size()>0){
				ls = lgoisticslist.get(0);
				TRACK api = new TRACK();
				try {
					String result = api.getOrderTracesByJson(ls.getCode(),
							ls.getLogisticsNumber());
					jo = JSONObject.fromObject(result);
					String traces = jo.get("Traces").toString();
					JSONArray ja = JSONArray.fromObject(traces);
					int flag = 0;
		            String state = "";
		            String wuname = "";
		            if("2".equals(jo.get("State"))){
		            	state = "在途中";
		            }else if("3".equals(jo.get("State"))){
		            	state = "已签收";
		            }else if("3".equals(jo.get("State"))){
		            	state = "问题件";
		            }
		            sb.append("<p><span>物流状态:</span><span class=\"mar_l green\">"+state+"</span></p>");   
		            ParseXML px = new ParseXML();
		    		List<Map<String,String>> list = px.WL;
		    		for(Map<String,String> map : list){
		    			if(map.get("code").equals(ls.getCode())){
		    				wuname = map.get("name");
		    			}
		    		}
		            sb.append(" <p><span>承运来源:</span><span class=\"mar_l\">"+wuname+"</span></p>");   
		            sb.append(" <p><span>运单编号:</span><span class=\"mar_l\">"+ls.getLogisticsNumber()+"</span></p>");  
		            sb.append(" <div class=\"mh-cont mh-list-wrap mh-unfold\"><div class=\"mh-list\">");  
		            sb.append(" <ul>");  
					for(int i=ja.size()-1;i>=0;i--){
						JSONObject json = ja.getJSONObject(i);
						if(flag==0){
							sb.append("<li class=\"first\">");
							sb.append("<p>");
							sb.append(json.get("AcceptTime"));
							sb.append("<p>");
							sb.append("<p>");
							sb.append(json.get("AcceptStation"));
							sb.append("<p>");
							sb.append("<span class=\"before\"></span><span class=\"after\"></span><i class=\"mh-icon mh-icon-new\"></i></li>");
						}else{
							sb.append("<li>");
							sb.append("<p>");
							sb.append(json.get("AcceptTime"));
							sb.append("<p>");
							sb.append("<p>");
							sb.append(json.get("AcceptStation"));
							sb.append("<p>");
							sb.append("<span class=\"before\"></span><span class=\"after\"></span></li>");
						}
						flag++;
					}
					sb.append("</ul></div></div>");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mnv.addObject("sb", sb.toString());
		mnv.setViewName("meitong/orders/viewLogistics");
		return mnv;
	}
	
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
}