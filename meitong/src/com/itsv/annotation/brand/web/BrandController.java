package com.itsv.annotation.brand.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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


import com.itsv.annotation.brand.bo.BrandService;
import com.itsv.annotation.brand.vo.Brand;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
/**
 * ˵�������ӣ��޸ģ�ɾ��brand��ǰ�˴�����
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
@Controller
public class BrandController extends BaseAnnotationController<Brand> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(BrandController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.brand";
	@Autowired
	private BrandService brandService; //�߼������
	
	@Autowired
	private Itsv_dictionaryService itsv_dictionaryService;//�߼������
	
	public BrandController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/brand/add");
		super.setIndexView("meitong/brand/index");
		super.setEditView("meitong/brand/edit");

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
		Brand brand = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			brand = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", brand);
		} else {
			brand = new Brand();
		}

		this.brandService.queryByVO(records, brand);
	}

	//��ʾ����brandҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//��ʾ�޸�brandҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Brand brand = this.brandService.queryById(id);
		if (null == brand) {
			showMessage(request, "δ�ҵ���Ӧ��brand��¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, brand);
		}
	}
	public static String lpad(int length, int number) {
	      String f = "%0" + length + "d";
	      return String.format(f, number);
	  }	
	//��ת����ҳ��
	@RequestMapping("/brand.beforeadd.htm")
	public ModelAndView beforeadd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService
				.queryNextListByName("Ʒ��");
		mnv.addObject("tplist", tplist);
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * ��������brand
	 */
	@RequestMapping("/brand.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv  = new ModelAndView();
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService.queryNextListByName("Ʒ��");
		mnv.addObject("tplist", tplist);
		mnv.setViewName(getAddView());
		Brand brand = null;
		try {
			int ornum = 1;
			brand = param2Object(request);
			String no = lpad(8, ornum+1);
			String brandcode = "MTMO"+new Date().getTime()+no;
			brand.setBrandcode(brandcode);
			brand.setFlag("1");
			this.brandService.add(brand);
			showMessage(request, "����Ʒ�Ƴɹ�");
		} catch (AppException e) {
			logger.error("����Ʒ��[" + brand + "]ʧ��", e);
			showMessage(request, "����Ʒ��ʧ�ܣ�" + e.getMessage(), e);

			//����ʧ�ܺ�Ӧ������д������������ʾ��brand
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, brand);
		}
		return mnv;
	}
	
	//��ѯƷ���б�
	@RequestMapping("/brand.onready.htm")
	@ResponseBody
	public JSONArray onreadyBrand(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		JSONArray ja = new JSONArray();
		this.brandService.queryAll();
		return ja;
	}
	
	
	//��ѯ����׼��
	@RequestMapping("/brand.query.htm")
	public ModelAndView queryBrand(HttpServletRequest request,HttpServletResponse response, ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//��ѯ�б�
	@RequestMapping("/brand.BeforeData.htm")
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
		Brand brand = new Brand();
		brand.setName(shmc);
		
		
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"brand");
		this.brandService.queryByVO(records, brand);
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	//�༭ǰ����
	@RequestMapping("/brand.EditData.htm")
	public ModelAndView editData(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService
				.queryNextListByName("Ʒ��");
		mnv.addObject("tplist", tplist);
		mnv.setViewName(getEditView());
		String brandid = "";
		try {
			brandid = ServletRequestUtils.getStringParameter(request, "brandid");
			Brand brand = brandService.queryById(brandid);
			mnv.addObject(brand);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mnv;
	}
	/**
	 * �����޸ĵ�brand
	 */
	@RequestMapping("brand.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Brand brand = null;
		try {
			brand = param2Object(request);
			//��������У��������⣬�ݲ����÷���˵�����У�鹦��
			//����У�飬��ʧ��ֱ�ӷ���
			//if (!validate(request, brand)) {
			//	return edit(request, response);
			//}
			brand.setFlag("1");
			this.brandService.update(brand);
			showMessage(request, "�޸�Ʒ�Ƴɹ�");
		} catch (AppException e) {
			logger.error("�޸�Ʒ��[" + brand + "]ʧ��", e);
			showMessage(request, "�޸�Ʒ��ʧ�ܣ�" + e.getMessage(), e);

			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, brand);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, brand);
  	    
	}
	
	@RequestMapping("brand.showSearch.htm")
	public ModelAndView showSearch(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName("/meitong/brand/showsearch");
		return mnv;
	}
	//����
	@RequestMapping("/brand.detail.htm")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv){
		String id ="";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Brand brand = this.brandService.queryById(id);
			mnv.addObject("data", brand);
			mnv.setViewName("/meitong/brand/detail");
		}else{
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	@RequestMapping("/brand.detailupt.htm")
	public ModelAndView detailupt(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String id = "";
		String flag = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
			flag = ServletRequestUtils.getStringParameter(request, "flag");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Brand brand  = this.brandService.queryById(id);
			if(flag.equals("0")){
				brand.setFlag("1");
			}else{
				brand.setFlag("0");
			}
			this.brandService.update(brand);
			showMessage(request, "�����ɹ�");		
			mnv.setViewName("admin/message1");
		}else{
			showMessage(request, "û�в鵽������");			
			mnv.setViewName("admin/message1");
		}
		return mnv;
	}
	/**
	 * ɾ��ѡ�е�brand
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] brands = ServletRequestUtils.getStringParameters(request, "p_id");
		//������ɾ���ɹ�
		try {
			for (String id : brands) {
				this.brandService.delete(id);
			}
			showMessage(request, "ɾ��brand�ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��brandʱʧ��", e);
			showMessage(request, "ɾ��brandʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
}