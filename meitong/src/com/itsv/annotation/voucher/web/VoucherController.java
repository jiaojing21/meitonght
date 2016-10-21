package com.itsv.annotation.voucher.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.customer.bo.CustomerService;
import com.itsv.annotation.customer.vo.Customer;
import com.itsv.annotation.goods.vo.Goods;
import com.itsv.annotation.spec.vo.Spec;
import com.itsv.annotation.util.UTILCN;
import com.itsv.annotation.voucher.bo.VoucherService;
import com.itsv.annotation.voucher.vo.Voucher;
import com.itsv.annotation.voucheruser.bo.VoucherUserService;
import com.itsv.annotation.voucheruser.vo.VoucherUser;
import com.itsv.annotation.voucherwith.bo.VoucherWithService;
import com.itsv.annotation.voucherwith.vo.VoucherWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
import com.mysql.fabric.xmlrpc.base.Data;
/**
 * ˵�������ӣ��޸ģ�ɾ������ȯ��ǰ�˴�����
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
@Controller
public class VoucherController extends BaseAnnotationController<Voucher> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(VoucherController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.voucher";
	@Autowired
	private VoucherService voucherService; //�߼������
	@Autowired
	private VoucherWithService voucherWithService; //�߼������
	@Autowired
	private VoucherUserService voucherUserService;
	@Autowired
	private CustomerService customerService;

	public VoucherController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/voucher/add");
		super.setIndexView("meitong/voucher/index");
		super.setEditView("meitong/voucher/edit");

	}

	//���Ǹ��෽����Ĭ��ִ��query()����ҳ��ʾ����

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	@RequestMapping("/voucher.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexbeforedata(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		Voucher voucher = new Voucher();
		CachePagedList records = PagedListTool
				.getEuiPagedList(request, "voucher");
		this.voucherService.queryByVO(records, voucher);
		for (Voucher vou : (List<Voucher>) records.getSource()) {
			VoucherWith voucherWith = new VoucherWith();
			voucherWith.setVoucherId(vou.getId());
			List<VoucherWith> with = this.voucherWithService.queryByVO(voucherWith);
			vou.setAmount(with.size()+"");
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	
	@RequestMapping("/voucher.index.htm")
	public ModelAndView indexAppControl(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv){
		mnv.setViewName(this.getIndexView());
		return mnv;
	}

	//��ʾ���Ӵ���ȯҳ��ǰ��׼���������
	@RequestMapping("/voucher.beforeAdd.htm")
	public ModelAndView beforeAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv){
		mnv.setViewName(this.getAddView());
		return mnv;
	}
	//��ʾ���Ӵ���ȯ������ǰ��׼���������
	@RequestMapping("/voucher.beforeAddAmount.htm")
	public ModelAndView beforeAddAmount(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,@RequestParam String appid){
		Voucher voucher = this.voucherService.queryById(appid);
		mnv.setViewName("meitong/voucher/addamount");
		mnv.addObject("voucher", voucher);
		return mnv;
	}
	/**
	 * ������������ȯ����
	 */
	@RequestMapping("/voucher.saveaddAmount.htm")
	public ModelAndView saveAddAmount(HttpServletRequest request, HttpServletResponse response) {
		Voucher voucher = null;
		try {
			voucher = param2Object(request);
			String amount = voucher.getAmount();
			int shuliang = Integer.parseInt(amount);
			Voucher vc = this.voucherService.queryById(voucher.getId());
			for(int i=0;i<shuliang;i++){
				String code = UTILCN.sj();
				VoucherWith voucherWith = new VoucherWith();
				voucherWith.setCode(code);
				List<VoucherWith> vw = this.voucherWithService.queryByVO(voucherWith);
				while(vw.size()>0){
					code = UTILCN.sj();
					VoucherWith with = new VoucherWith();
					with.setCode(code);
					vw = this.voucherWithService.queryByVO(with);
				}
				voucherWith.setType("0");
				voucherWith.setCreatetime(new Date());
				voucherWith.setCreateuser(vc.getCreateuser());
				voucherWith.setVoucherId(vc.getId());
				this.voucherWithService.add(voucherWith);
			}
			showMessage(request, "��������ȯ�ɹ�");
		} catch (AppException e) {
			logger.error("��������ȯ[" + voucher + "]ʧ��", e);
			showMessage(request, "��������ȯʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ������ȯ
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucher);
		}

		return new ModelAndView("admin/message1");
	}
	

	//��ʾ�޸Ĵ���ȯҳ��ǰ��׼������
	
	@RequestMapping("/voucher.editData.htm")
	protected ModelAndView beforeeditData(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "id", "");
		Voucher voucher = this.voucherService.queryById(id);
		if (null == voucher) {
			showMessage(request, "δ�ҵ���Ӧ�Ĵ���ȯ��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, voucher);
		}
		mnv.setViewName(this.getEditView());
		return mnv;
	}

	/**
	 * ������������ȯ
	 */
	@RequestMapping("/voucher.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Voucher voucher = null;
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
  		User user = adapter.getRealUser();
		try {
			voucher = param2Object(request);
			voucher.setCreatetime(new Date());
			voucher.setCreateuser(user.getId());
			this.voucherService.add(voucher);
			showMessage(request, "��������ȯ�ɹ�");
		} catch (AppException e) {
			logger.error("��������ȯ[" + voucher + "]ʧ��", e);
			showMessage(request, "��������ȯʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ������ȯ
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, voucher);
		}

		return new ModelAndView("admin/message1");
	}

	/**
	 * �����޸ĵĴ���ȯ
	 */
	@RequestMapping("/voucher.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Voucher voucher = null;
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
  		User user = adapter.getRealUser();
		try {
			voucher = param2Object(request);
			voucher.setCreatetime(new Date());
			voucher.setCreateuser(user.getId());
			this.voucherService.update(voucher);
			showMessage(request, "�޸Ĵ���ȯ�ɹ�");
		} catch (AppException e) {
			logger.error("�޸Ĵ���ȯ[" + voucher + "]ʧ��", e);
			showMessage(request, "�޸Ĵ���ȯʧ�ܣ�" + e.getMessage(), e);
			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, voucher);
		}
		return new ModelAndView("admin/message1");
	}

	/**
	 * ɾ��ѡ�еĴ���ȯ
	 */
	@RequestMapping("/voucher.delete.htm")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response, @RequestParam String app_id) {
		String result = "success";
		try {
			if (!"".equals(app_id)) {
				String[] goods = app_id.split(",");
				for (String id : goods) {
					if (!"".equals(id)) {
						this.voucherService.delete(id);
					}
				}
			}
		} catch (AppException e) {
			result = "error";
		}
		return result;
	}
	
	/**
	 * ����ȯ��ϸ
	 */
	@RequestMapping("/voucher.detail.htm")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		mnv.setViewName("meitong/voucher/detail");
		return mnv;
	}
	
	@RequestMapping("/voucher.BeforeDetailData.htm")
	@ResponseBody
	public Map<String, Object> detailbeforedata(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		VoucherWith voucherWith = new VoucherWith();
		CachePagedList records = PagedListTool
				.getEuiPagedList(request, "voucherwith");
		this.voucherWithService.queryByVO(records, voucherWith);
		for (VoucherWith vou : (List<VoucherWith>) records.getSource()) {
			Voucher vc = this.voucherService.queryById(vou.getVoucherId());
			String type = vou.getType();
			if(type.equals("0")){
				type = "δʹ��";
			}else if(type.equals("1")){
				type = "��ʹ��";
			}else{
				type = "����";
			}
			vou.setType(type);
			vou.setWorth(vc.getWorth());//����ȯ��ֵ
			VoucherUser vu = new VoucherUser();
			vu.setVoucherWithId(vou.getId());
			List<VoucherUser> vulist = this.voucherUserService.queryByVO(vu);
			String issueduser = "";//�����û�
			String access = "";// ��ȡ;��
			String fetchTime = "";// ��ȡʱ��
			String failureTime = "";// ����ʱ��
			String status = "";// ��ȡ״̬
			if(vulist.size()>0){
				Customer cus = this.customerService.queryById(vulist.get(0).getCusId());
				issueduser = cus.getNickname();
				access = vulist.get(0).getAccess();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				fetchTime = formatter.format(vulist.get(0).getFetchTime());
				failureTime = formatter.format(vulist.get(0).getFailureTime());
				status = vulist.get(0).getType();
				if(status.equals("0")){
					status = "δ��ȡ";
				}else if(status.equals("1")){
					status = "����ȡ";
				}else{
					status = "����";
				}
			}
			vou.setAccess(access);
			vou.setIssueduser(issueduser);
			vou.setFetchTime(fetchTime);
			vou.setFailureTime(failureTime);
			vou.setStatus(status);
			
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	
	/**
	 * ����ѡ�еĴ���ȯ
	 */
	@RequestMapping("/voucher.fenpei.htm")
	public ModelAndView fenpei(HttpServletRequest request, HttpServletResponse response,ModelAndView mnv ,@RequestParam String app_id) {
		mnv.setViewName("meitong/voucher/fenpei");
		mnv.addObject("app_id", app_id);
		return mnv;
	}
	
	/**
	 * ����ѡ�еĴ���ȯ
	 */
	@RequestMapping("/voucher.cusfenpei.htm")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response, @RequestParam String cusid,@RequestParam String appID) {
		String result = "success";
//		String cusid = request.getParameter("cusid");
//		String appID = request.getParameter("appID");
		try {
			if (!"".equals(cusid)&&!"".equals(appID)) {
				String[] cus = cusid.split(",");
				String[] vou = appID.split(",");
				for (int i=0;i<cus.length;i++) {
					if (!"".equals(cus[i])) {
						VoucherUser vu = new VoucherUser();
						vu.setAccess("��̨����");
						VoucherWith voucherWith = this.voucherWithService.queryById(vou[i]);
						Voucher voucher = this.voucherService.queryById(voucherWith.getVoucherId());
						String term = voucher.getTerm();
						int qixian = Integer.parseInt(term);
						vu.setCode(voucherWith.getCode());
						vu.setCusId(cus[i]);
						vu.setFetchTime(new Date());
						
						Calendar calendar = Calendar.getInstance(); //�õ�����
						calendar.setTime(new Date());//�ѵ�ǰʱ�丳������
						calendar.add(Calendar.DAY_OF_MONTH, qixian);  //
						Date dBefore = calendar.getTime();   //
						
						vu.setFailureTime(dBefore);
						vu.setType("1");
						vu.setVoucherWithId(vou[i]);
						this.voucherUserService.save(vu,appID);
					}
				}
			}
		} catch (AppException e) {
			result = "error";
		}
		return result;
	}
	

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
}