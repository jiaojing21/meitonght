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
 * ˵�������ӣ��޸ģ�ɾ��logistics��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class LogisticsController extends BaseAnnotationController<Logistics> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(LogisticsController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.logistics";
	@Autowired
	private LogisticsService logisticsService; //�߼������

	@Autowired
	private OrdersService ordersService; //�߼������
	
	public LogisticsController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/logistics/add");
		super.setIndexView("meitong/logistics/index");
		super.setEditView("meitong/logistics/edit");

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
		Logistics logistics = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			logistics = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", logistics);
		} else {
			logistics = new Logistics();
		}

		this.logisticsService.queryByVO(records, logistics);
	}

	//��ʾ����logisticsҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}
	//��ѯ��������׼��
	@RequestMapping("logistics.query.htm")
	public ModelAndView queryLogistics(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//��ѯ�����б�
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
	//��ʾ�޸�logisticsҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Logistics logistics = this.logisticsService.queryById(id);
		if (null == logistics) {
			showMessage(request, "δ�ҵ���Ӧ��logistics��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, logistics);
		}
	}
	//��������׼��
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
				showMessage(request, "û�в鵽������");			
				mnv.setViewName("admin/message");
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return mnv;
	}
	public static String lpad(int length, int number) {
	      String f = "%0" + length + "d";
	      return String.format(f, number);
	  }	
	/**
	 * ��������logistics
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
			logger.error("��������[ logistics ]ʧ��", e);
		}
		return result;
	}

	/**
	 * �����޸ĵ�logistics
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Logistics logistics = null;
		try {
			logistics = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, logistics)) {
			//	return edit(request, response);
			//}

			this.logisticsService.update(logistics);
			showMessage(request, "�޸�logistics�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�logistics[" + logistics + "]ʧ��", e);
			showMessage(request, "�޸�logisticsʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�logistics
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] logisticss = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : logisticss) {
				this.logisticsService.delete(id);
			}
			showMessage(request, "ɾ��logistics�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��logisticsʱʧ��", e);
			showMessage(request, "ɾ��logisticsʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	// ��ѯ�������Ƿ����
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
	
	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setLogisticsService(LogisticsService logisticsService) {
		this.logisticsService = logisticsService;
	}
}