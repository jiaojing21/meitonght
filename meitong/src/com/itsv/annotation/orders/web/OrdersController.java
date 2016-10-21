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
 * ˵�������ӣ��޸ģ�ɾ��orders��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class OrdersController extends BaseAnnotationController<Orders> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(OrdersController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.orders";
	@Autowired
	private OrdersService ordersService; //�߼������

	@Autowired
	private OrdersGoodsService ordersGoodsService; //�߼������
	
	@Autowired
	private GoodsService goodsService; //�߼������

	@Autowired
	private RegionService regionService; //�߼������
	
	@Autowired
	private AddressService addressService; //�߼������
	
	@Autowired
	private SpecService specService; //�߼������
	
	@Autowired
	private FileUploadService fileUploadService;//�߼������
	
	@Autowired
	private LogisticsService logisticsService;
	
	public OrdersController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/orders/add");
		super.setIndexView("meitong/orders/index");
		super.setEditView("meitong/orders/edit");

	}

  /**
   * ע���Զ�������ת���࣬����ת�����ڶ���
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
  }    

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//ʵ�ַ�ҳ��ѯ����
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Orders orders = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			orders = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", orders);
		} else {
			orders = new Orders();
		}

		this.ordersService.queryByVO(records, orders);
	}

	//��ʾ����ordersҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�ordersҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Orders orders = this.ordersService.queryById(id);
		if (null == orders) {
			showMessage(request, "δ�ҵ���Ӧ��orders��¼��������");
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
	 * ��������orders
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Orders orders = null;
		try {
			orders = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, orders)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, orders);
			//}

			this.ordersService.add(orders);

			showMessage(request, "����orders�ɹ�");
		} catch (AppException e) {
			logger.error("����orders[" + orders + "]ʧ��", e);
			showMessage(request, "����ordersʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��orders
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, orders);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�orders
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Orders orders = null;
		try {
			orders = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, orders)) {
			//	return edit(request, response);
			//}

			this.ordersService.update(orders);
			showMessage(request, "�޸�orders�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�orders[" + orders + "]ʧ��", e);
			showMessage(request, "�޸�ordersʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�orders
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] orderss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : orderss) {
				this.ordersService.delete(id);
			}
			showMessage(request, "ɾ��orders�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��ordersʱʧ��", e);
			showMessage(request, "ɾ��ordersʧ�ܣ�" + e.getMessage(), e);
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if(id!=null&&id!=""){
			Orders orders = this.ordersService.queryById(id);
			
			String cjsj = sdf.format(orders.getCreatetime());
			String status = "";
			if("0".equals(orders.getStatus())){
				status = "δ���";
			}else if("1".equals(orders.getStatus())){
				status = "��֧��";
			}else if("2".equals(orders.getStatus())){
				status = "��ȷ�϶���";
			}else if("3".equals(orders.getStatus())){
				status = "�ѷ���";
			}else if("4".equals(orders.getStatus())){
				status = "����ɶ���";
			}else if("5".equals(orders.getStatus())){
				status = "��ȡ������";
			}
			ordersGoods.setOrderNumber(orders.getOrderNumber());
			FileUpload fileUpload = new FileUpload();
			List<OrdersGoods> list = this.ordersGoodsService.queryByVO(ordersGoods);
			sb.append("<table  align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#69D7E4\" class=\"listWt\" style =\"width:60%;\"> ");
			sb.append("<tr><th colspan=\"4\">��������</th></tr>");
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">������</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getOrderNumber()+"</td></tr>");	
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">��ַ</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getAddress()+"</td></tr>");
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">״̬</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+status+"</td></tr>");	
			sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">����ʱ��</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+cjsj+"</td></tr>");	
			if((!"0".equals(orders.getStatus()))&&(!"1".equals(orders.getStatus()))){
				String zfsj = sdf.format(orders.getPayTime());
				String qrsj = sdf.format(orders.getConfirmatTime());
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">֧����ʽ</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayment()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">֧��ƽ̨</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayPlatform()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">֧��ʱ��</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+zfsj+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">ȷ��ʱ��</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+qrsj+"</td></tr>");	
			}else if("1".equals(orders.getStatus())){
				String zfsj = sdf.format(orders.getPayTime());
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">֧����ʽ</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayment()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">֧��ƽ̨</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+orders.getPayPlatform()+"</td></tr>");	
				sb .append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">֧��ʱ��</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+zfsj+"</td></tr>");	
			}
			for(OrdersGoods og : list){
				Goods goods = this.goodsService.queryById(og.getGoodsId());
				orders = this.ordersService.queryByDdh(og.getGoodsNumber());
				Spec spec = this.specService.queryById(goods.getSpecId());
				fileUpload.setFId(goods.getSpecId());
				fulist = this.fileUploadService.queryByVO(fileUpload);
				StringBuffer sbu = new StringBuffer();
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">��Ʒ����</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+spec.getSpecName()+"</td></tr>");
				String picstart = "<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">��Ʒ��ϸ</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"; 
				String picend = "</td></tr>";
				for(FileUpload fu : fulist){
					if(fu.getType().equals("0")){
						sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">��Ʒ����</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\"><img src = \""+fu.getDownloadPath()+"\"></img></td></tr>");
					}else if(fu.getType().equals("1")){
						sbu.append("<img src = \""+fu.getDownloadPath()+"\"></img>");
					}
				}
				if(sbu.toString().length()>0){
					sb.append(picstart+sbu.toString()+picend);
					sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px; \">����</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+og.getGoodsNumber()+"</td></tr>");
					sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">�ܼ�</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+og.getGoodsTotal()+"</td></tr>");	
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
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	//״̬��ѯ
	@RequestMapping("/orders.type.htm")
	@ResponseBody
	public Orders type(HttpServletRequest request,HttpServletResponse response,String pid){
		Orders orders =  this.ordersService.queryById(pid);
		return orders;
	}
	//����У�鶩����
	@RequestMapping("/orders.ordersDirDdh.htm")
	@ResponseBody
	public Orders ddhOnly(final HttpServletRequest request, @RequestParam(value = "orderNumber", required = true) String orderNumber){
		Orders orders =  this.ordersService.queryByDdh(orderNumber);
		return orders;
	}
	
	//��������
	@RequestMapping("/orders.opt.htm")
	@ResponseBody
	public String opt(HttpServletRequest request, HttpServletResponse response,String id){
		String res = "";
		Orders orders = this.ordersService.queryById(id);
		res = orders.getStatus();
		return res;
	}
	
	//����״̬���޸�
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
			res = "���ĳɹ�";
		}catch (Exception e) {
			// TODO: handle exception
			res ="����ʧ��";
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
			res = "���ĳɹ�";
		}catch (Exception e) {
			// TODO: handle exception
			res = "����ʧ��";
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
		            	state = "��;��";
		            }else if("3".equals(jo.get("State"))){
		            	state = "��ǩ��";
		            }else if("3".equals(jo.get("State"))){
		            	state = "�����";
		            }
		            sb.append("<p><span>����״̬:</span><span class=\"mar_l green\">"+state+"</span></p>");   
		            ParseXML px = new ParseXML();
		    		List<Map<String,String>> list = px.WL;
		    		for(Map<String,String> map : list){
		    			if(map.get("code").equals(ls.getCode())){
		    				wuname = map.get("name");
		    			}
		    		}
		            sb.append(" <p><span>������Դ:</span><span class=\"mar_l\">"+wuname+"</span></p>");   
		            sb.append(" <p><span>�˵����:</span><span class=\"mar_l\">"+ls.getLogisticsNumber()+"</span></p>");  
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
	
	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
}