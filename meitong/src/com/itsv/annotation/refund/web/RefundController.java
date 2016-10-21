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
 * ˵�������ӣ��޸ģ�ɾ��refund��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller
public class RefundController extends BaseAnnotationController<Refund> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(RefundController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.refund";
	@Autowired
	private RefundService refundService; //�߼������

	public RefundController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/refund/add");
		super.setIndexView("meitong/refund/index");
		super.setEditView("meitong/refund/edit");

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
		Refund refund = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			refund = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", refund);
		} else {
			refund = new Refund();
		}

		this.refundService.queryByVO(records, refund);
	}

	//��ʾ����refundҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�refundҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Refund refund = this.refundService.queryById(id);
		if (null == refund) {
			showMessage(request, "δ�ҵ���Ӧ��refund��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, refund);
		}
	}
	//��ѯǰ����
	@RequestMapping("/refund.query.htm")
	public ModelAndView queryRefund(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//��ѯ�б�
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
	//���ǰ����׼��
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
	 * ��������refund
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

			showMessage(request, "�����˻����ɹ�");
		} catch (AppException e) {
			logger.error("�����˻���[" + refund + "]ʧ��", e);
			showMessage(request, "�����˻���ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��refund
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, refund);
		}

		return mnv;
	}

	/**
	 * �����޸ĵ�refund
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Refund refund = null;
		try {
			refund = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, refund)) {
			//	return edit(request, response);
			//}

			this.refundService.update(refund);
			showMessage(request, "�޸�refund�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�refund[" + refund + "]ʧ��", e);
			showMessage(request, "�޸�refundʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�refund
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] refunds = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : refunds) {
				this.refundService.delete(id);
			}
			showMessage(request, "ɾ��refund�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��refundʱʧ��", e);
			showMessage(request, "ɾ��refundʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}
	
	//��������
	@RequestMapping("/refund.opt.htm")
	@ResponseBody
	public String opt(HttpServletRequest request,HttpServletResponse response,String id){
		String res = "";
		Refund refund = this.refundService.queryById(id);
		res = refund.getFlag();
		return res;
	}
	
	//�˻�����״̬�޸�
	@RequestMapping("/refund.save.htm")
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response,String id){
		String res = "";
		try{
			Refund refund = this.refundService.queryById(id);
			refund.setFlag("1");
			this.refundService.update(refund);
			res = "���ĳɹ�";
		}catch (Exception e) {
			// TODO: handle exception
			res = "����ʧ��";
		}
		return res;
	}
	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}
}