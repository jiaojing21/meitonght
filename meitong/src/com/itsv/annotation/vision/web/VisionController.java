package com.itsv.annotation.vision.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.customer.bo.CustomerService;
import com.itsv.annotation.customer.vo.Customer;
import com.itsv.annotation.region.bo.RegionService;
import com.itsv.annotation.region.vo.Region;
import com.itsv.annotation.vision.bo.VisionService;
import com.itsv.annotation.vision.vo.Vision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ��vision��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-05-04
 * @version 1.0
 */
@Controller
public class VisionController extends BaseAnnotationController<Vision> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(VisionController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.vision";
	@Autowired
	private VisionService visionService; //�߼������

	@Autowired
	private UserService userService; //�߼������
	
	@Autowired
	private RegionService regionService; //�߼������
	
	@Autowired
	private CustomerService customerService; //�߼������
	
	public VisionController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/vision/add");
		super.setIndexView("meitong/vision/index");
		super.setEditView("meitong/vision/edit");

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
		Vision vision = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			vision = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", vision);
		} else {
			vision = new Vision();
		}

		this.visionService.queryByVO(records, vision);
	}  
	//��ѯǰ����׼��
	@RequestMapping("/vision.sh.htm")
	public ModelAndView queryVison (HttpServletRequest request ,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//��ѯ�б�
	@RequestMapping("/vision.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexControl(HttpServletRequest request,HttpServletResponse response){
		Map<String ,Object> map = new HashMap<String ,Object>();
		String shmc = "";
		try {
			shmc = java.net.URLDecoder
					.decode(ServletRequestUtils.getStringParameter(request,
							"shmc", ""), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vision vision = new Vision();
		vision.setMood(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"vision");
		this.visionService.queryByVO(records, vision);
		for(Vision v : (List<Vision>)records.getSource() ){
			Customer customer = this.customerService.queryById(v.getCusId());
			List<String> list = new ArrayList<String>();
			Region region = new Region();
			region.setRegionCode(v.getSeatof());
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
			v.setSeatof(sb.toString());
			v.setCusId(customer.getNickname());
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
		}
	@RequestMapping("/vision.detail.htm")
	public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Vision vision = this.visionService.queryById(id);
			Customer customer = this.customerService.queryById(vision.getCusId());
			List<String> list = new ArrayList<String>();
			Region region = new Region();
			region.setRegionCode(vision.getSeatof());
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
			User user = this.userService.queryById(vision.getUserId());
			if("0".equals(vision.getAuditStatus())){
				vision.setZt("δ���");
			}else if("1".equals(vision.getAuditStatus())){
				vision.setZt("���ͨ��");
			}else if("2".equals(vision.getAuditStatus())){
				vision.setZt("�Ѿܾ�");
			}
			vision.setUserId(user.getRealName());
			vision.setCusId(customer.getNickname());
			vision.setSeatof(sb.toString());
			mnv.addObject("data", vision);
			mnv.setViewName("meitong/vision/detail");
		}else{
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	@RequestMapping("/vision.save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = adapter.getRealUser();
		String id = "";
		String type = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
			type = ServletRequestUtils.getStringParameter(request, "type");
		} catch (ServletRequestBindingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Vision vision = this.visionService.queryById(id);
			vision.setUserId(user.getId());
			vision.setAuditTime(new Date());
			vision.setAuditStatus(type);
			this.visionService.update(vision);
			showMessage(request, "�����ɹ�");		
			mnv.setViewName("admin/message1");
		}else{
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message1");
		}
		return mnv;
	}
	//��ʾ����visionҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�visionҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Vision vision = this.visionService.queryById(id);
		if (null == vision) {
			showMessage(request, "δ�ҵ���Ӧ��vision��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, vision);
		}
	}

	/**
	 * ��������vision
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Vision vision = null;
		try {
			vision = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//ace8 2006.9.10
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, vision)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, vision);
			//}

			this.visionService.add(vision);

			showMessage(request, "����vision�ɹ�");
		} catch (AppException e) {
			logger.error("����vision[" + vision + "]ʧ��", e);
			showMessage(request, "����visionʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��vision
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, vision);
		}

		return query(request, response);
	}

	/**
	 * �����޸ĵ�vision
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Vision vision = null;
		try {
			vision = param2Object(request);

			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, vision)) {
			//	return edit(request, response);
			//}

			this.visionService.update(vision);
			showMessage(request, "�޸�vision�ɹ�");
		} catch (AppException e) {
			logger.error("�޸�vision[" + vision + "]ʧ��", e);
			showMessage(request, "�޸�visionʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * ɾ��ѡ�е�vision
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] visions = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : visions) {
				this.visionService.delete(id);
			}
			showMessage(request, "ɾ��vision�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��visionʱʧ��", e);
			showMessage(request, "ɾ��visionʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setVisionService(VisionService visionService) {
		this.visionService = visionService;
	}
}