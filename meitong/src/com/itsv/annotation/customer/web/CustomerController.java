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
 * ˵�������ӣ��޸ģ�ɾ��customer��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-04-06
 * @version 1.0
 */
@Controller
public class CustomerController extends BaseAnnotationController<Customer> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(CustomerController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.customer";
	@Autowired
	private CustomerService customerService; //�߼������

	public CustomerController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/customer/add");
		super.setIndexView("meitong/customer/index");
		super.setEditView("meitong/customer/edit");

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
		Customer customer = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			customer = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", customer);
		} else {
			customer = new Customer();
		}

		this.customerService.queryByVO(records, customer);
	}

	//��ʾ����customerҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�customerҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Customer customer = this.customerService.queryById(id);
		if (null == customer) {
			showMessage(request, "δ�ҵ���Ӧ��customer��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, customer);
		}
	}
	//��ѯ����׼��
		@RequestMapping("/customer.query.htm")
		public ModelAndView queryBrand(HttpServletRequest request,HttpServletResponse response, ModelAndView mnv){
			mnv.setViewName(getIndexView());
			return mnv;
		}
		//��ѯ�б�
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
		
		//��ѯ��������
		@RequestMapping("/customer.BeforeALLData.htm")
		@ResponseBody
		public List<Customer> allDataControl(HttpServletRequest request,
				HttpServletResponse response) throws AppException {
			List<Customer> cuslist = this.customerService.queryAll();
			return cuslist;
		}
	/**
	 * ��������customer
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = null;
		try {
			customer = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, customer)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, customer);
			//}

			this.customerService.add(customer);

			showMessage(request, "����customer�ɹ�");
		} catch (AppException e) {
			logger.error("����customer[" + customer + "]ʧ��", e);
			showMessage(request, "����customerʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��customer
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, customer);
		}

		return query(request, response);
	}
	//�޸�ǰ����׼��
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
	 * �����޸ĵ�customer
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
			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, customer)) {
			//	return edit(request, response);
			//}

			this.customerService.update(customer);
			showMessage(request, "�޸Ŀͻ��ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ŀͻ�[" + customer + "]ʧ��", e);
			showMessage(request, "�޸Ŀͻ�ʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, customer);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, customer);	}

	/**
	 * ɾ��ѡ�е�customer
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
			showMessage(request, "ɾ���ͻ��ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ���ͻ�ʱʧ��", e);
			showMessage(request, "ɾ���ͻ�ʧ�ܣ�" + e.getMessage(), e);
		}
		
		return result;
	}
	//����
	@RequestMapping("/customer.detail.htm")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv){
		String id ="";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Customer customer = this.customerService.queryById(id);
			mnv.addObject("data", customer);
			mnv.setViewName("/meitong/customer/detail");
		}else{
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
}