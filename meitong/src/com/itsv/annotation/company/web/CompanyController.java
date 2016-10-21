package com.itsv.annotation.company.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.annotation.company.bo.CompanyService;
import com.itsv.annotation.company.bo.ImpaexpService;
import com.itsv.annotation.company.bo.PolyteneService;
import com.itsv.annotation.company.vo.Company;
import com.itsv.annotation.company.vo.Impaexp;
import com.itsv.annotation.company.vo.Polytene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * ˵�������ӣ��޸ģ�ɾ����ҵ���ǰ�˴�����
 * 
 * @author quyf
 * @since 2014-10-23
 * @version 1.0
 */
@Controller

public class CompanyController extends BaseAnnotationController<Company> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(CompanyController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.company";
	@Autowired
	private CompanyService companyService; //�߼������
	
	@Autowired
	private ImpaexpService impaexpService; //�����ڵ��߼������
	  
	@Autowired
	private PolyteneService polyteneService; //����ϩ�����߼������

	public CompanyController(){

		super.setDefaultCheckToken(true);
		super.setAddView("company/add");
		super.setIndexView("company/index");
		super.setEditView("company/edit");
		super.setShowView("company/showdetail");

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
		Company company = null;

		//����Ǳ����dispatch���б�ҳ�棬�򲻴������Ĳ���
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			company = param2Object(request);

			//����ѯ�������ظ�ҳ��
			mnv.addObject("condition", company);
		} else {
			company = new Company();
		}

		this.companyService.queryByVO(records, company);
	}
	
	/**
	 * ת����ѯҳ��
	 * @return ModelAndView ת����ҳ�����
	 */
	@RequestMapping("/company.showSearch.htm")
	public ModelAndView showSearch(HttpServletRequest request, HttpServletResponse response){
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		ModelAndView mnv = new ModelAndView();
		Company company = new Company();
		List<Company> companylist=null;
		if("1".equals(leixin)){//1��ҵ����
			mnv.setViewName("company/showsearch");
		} else if("2".equals(leixin)){//2����ϩ���ܱ�
			company.setType("1");
			companylist=this.companyService.queryByVO(company);
			mnv.setViewName("company/polytene/showsearch");
		} else if("3".equals(leixin)){//3������
			company.setType("2");
			companylist=this.companyService.queryByVO(company);
			mnv.setViewName("company/impaexp/showsearch");
		}
		mnv.addObject("companylist", companylist);
		return mnv;
		
	}
	
	/**
	    * ��ת����ʾ��ҵ��Ϣ����
	    * @param request
	    * @param response
	    * @param mnv 
	    * @return Ҫ��ת��ҳ��·��
	    * @throws AppException
	    */
		@RequestMapping("/company.company.htm")
	  	public ModelAndView toAppControl(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) throws AppException {
			String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String type_view="";
			mnv = new ModelAndView();
			if("1".equals(leixin)){//1��ҵ����
				type_view=this.getIndexView();				
			} else if("2".equals(leixin)){//2����ϩ���ܱ�
				type_view="company/polytene/index";
			} else if("3".equals(leixin)){//3������
				type_view="company/impaexp/index";
			}	
			mnv.setViewName(type_view);
			mnv.addObject("code", code);
			return mnv;
	  	}

		@SuppressWarnings("unchecked")
		@RequestMapping("/company.companyData.htm")
		@ResponseBody
		public Map<String, Object> indexAppControl(HttpServletRequest request, HttpServletResponse response) throws AppException {
		  Map<String, Object> map = new HashMap<String, Object>();
		  String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		  Company company = new Company();	
		  CachePagedList records = PagedListTool.getEuiPagedList(request, "polytene");	
		  if("1".equals(leixin)){//1��ҵ����
			  String search_companyname=null;
			  try {
				search_companyname = java.net.URLDecoder.decode(ServletRequestUtils.getStringParameter(request, "search_companyname", ""),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  String search_type = ServletRequestUtils.getStringParameter(request, "search_type", "");
			  if(search_companyname !=null && search_companyname.length()>0){
				  company.setCompanyname(search_companyname);
			  }
			  if(search_type !=null && search_type.length()>0){
				  company.setType(search_type);
			  }
			  this.companyService.queryByVO(records, company);
		  	  map.put("total", records.getTotalNum());
		  	  map.put("rows", records.getSource());
		  	  return ResponseUtils.sendMap(map);
		  }else if("2".equals(leixin)){//2����ϩ���ܱ�
			  String search_companyid = ServletRequestUtils.getStringParameter(request, "search_companyid", "");
			  String search_polytenetype = ServletRequestUtils.getStringParameter(request, "search_polytenetype", "");
			  String search_ptime = ServletRequestUtils.getStringParameter(request, "search_ptime", "");
			  Polytene polytene=new Polytene();
			  if(search_companyid !=null && search_companyid.length()>0){
				  polytene.setCompanyid(search_companyid);
			  }
			  if(search_polytenetype !=null && search_polytenetype.length()>0){
				  polytene.setType(search_polytenetype);
			  }
			  if(search_ptime !=null && search_ptime.length()>0){
				  polytene.setPtime(search_ptime);
			  }
			  this.polyteneService.queryByVO(records, polytene);
			  for(Polytene polytenenew :(List<Polytene>)records.getSource()){
				  polytenenew.setCompanyid(this.companyService.queryById(polytenenew.getCompanyid()).getCompanyname());
			  }
			  map.put("total", records.getTotalNum());
		  	  map.put("rows", records.getSource());
		  	  return ResponseUtils.sendMap(map);
		  } else if("3".equals(leixin)){//3������
			  String search_companyid = ServletRequestUtils.getStringParameter(request, "search_companyid", "");
			  String search_impaexptype = ServletRequestUtils.getStringParameter(request, "search_impaexptype", "");
			  String search_htime = ServletRequestUtils.getStringParameter(request, "search_htime", "");
			  Impaexp  impaexp=new Impaexp();
			  impaexp.setCompanyid(search_companyid);
			  impaexp.setType(search_impaexptype);
			  impaexp.setHtime(search_htime);
			  impaexpService.queryByVO(records, impaexp);
			  for(Impaexp impaexpnew :(List<Impaexp>)records.getSource()){
				  impaexpnew.setCompanyid(this.companyService.queryById(impaexpnew.getCompanyid()).getCompanyname());
			  }
			  map.put("total", records.getTotalNum());
		  	  map.put("rows", records.getSource());
		  	  return ResponseUtils.sendMap(map);
		  }	
	  	  return ResponseUtils.sendMap(map);
	  	    
		}
	//��ʾ������ҵ��ҳ��ǰ��׼���������

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}
	
	/**
	 * ��ת�����ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/company.toAdd.htm")
  	public ModelAndView beforeAdd(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv=new ModelAndView();  
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String code = ServletRequestUtils.getStringParameter(request, "code", "");
		Company company = new Company();
		List<Company> companylist=null;
		if("1".equals(leixin)){//1��ҵ����
			mnv.setViewName(this.getAddView());//���÷��ص��ļ���  
		} else if("2".equals(leixin)){//2����ϩ���ܱ�
			company.setType("1");
			companylist=this.companyService.queryByVO(company);
			mnv.addObject("companylist", companylist);
			mnv.setViewName("company/polytene/add");
		} else if("3".equals(leixin)){//3������
			company.setType("2");
			companylist=this.companyService.queryByVO(company);
			mnv.addObject("companylist", companylist);
			mnv.setViewName("company/impaexp/add");
		}	
		mnv.addObject("code", code);
        return mnv;
  	}
	//��ʾ�޸���ҵ��ҳ��ǰ��׼������

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		
		Company company = this.companyService.queryById(id);
		if (null == company) {
			showMessage(request, "δ�ҵ���Ӧ����ҵ���¼��������");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, company);
		}
	}
	
	/**
	 * ��ת���޸�ҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/company.toEdit.htm")
	protected ModelAndView beforeEdit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv=new ModelAndView();
		String id = ServletRequestUtils.getStringParameter(request, "appId", "");
		String types = ServletRequestUtils.getStringParameter(request, "types", "");
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		Company company = this.companyService.queryById(id);
		Polytene polytene=null;
		Impaexp impaexp=null;
		if("1".equals(leixin)){//1��ҵ����
			if (null == company) {
				showMessage(request, "δ�ҵ���Ӧ����ҵ���¼��������");
				//mnv = query(request, response);
				mnv.setViewName(this.getIndexView());
			} else {
				/*
				if("1".equals(company.getType())){
					polytene=this.polyteneService.findUniqueBy("companyid",company.getId());
				}
				if("2".equals(company.getType())){
					impaexp=this.impaexpService.findUniqueBy("companyid",company.getId());
				}
				*/
				mnv.addObject(WebConfig.DATA_NAME, company);
			//	mnv.addObject("data_polytene", polytene);
			//	mnv.addObject("data_impaexp", impaexp);
				if("toedit".equals(types)){
					mnv.setViewName(this.getEditView());
				}
				if("showdetail".equals(types)){
					mnv.setViewName(this.getShowView());
				}
				
			}
		} else if("2".equals(leixin)){//2����ϩ���ܱ�
			Company company2 = new Company();
			company2.setType("1");
			List<Company> companylist=this.companyService.queryByVO(company2);
			polytene=this.polyteneService.queryById(id);
			mnv.addObject("companylist", companylist);
			mnv.addObject("data_polytene", polytene);
			mnv.setViewName("company/polytene/edit");
		} else if("3".equals(leixin)){//3������
			Company company3 = new Company();
			company3.setType("2");
			List<Company> companylist=this.companyService.queryByVO(company3);
			impaexp=this.impaexpService.queryById(id);
			mnv.addObject("companylist", companylist);
			mnv.addObject("data_impaexp", impaexp);
			mnv.setViewName("company/impaexp/edit");
		}
		
		return mnv;
	}

	/**
	 * ����������ҵ��
	 */
	@RequestMapping("/company.save.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv=new ModelAndView();
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String code = ServletRequestUtils.getStringParameter(request, "code", "");
		String companyid = ServletRequestUtils.getStringParameter(request, "companyid", "");//������ҵ/����,ID
		Company company = null;
		Polytene polytene=null;
		Impaexp impaexp=null;
		try {
			if("1".equals(leixin)){//1��ҵ����
				company = param2Object(request);
				/*
				if("1".equals(company.getType())){//1 ��ҵ ����ϩ���ܱ���Ϣ
					company.setTemp_capacity_exportsl(capacity);
					company.setTemp_production_exportse(production);
					company.setTemp_ptime_htime(ptime);
				}
				if("2".equals(company.getType())){ //2 ���� ��������Ϣ
					company.setTemp_importsL(importsl);
					company.setTemp_importsE(importse);
					company.setTemp_capacity_exportsl(exportsl);
					company.setTemp_production_exportse(exportse);
					company.setTemp_ptime_htime(htime);
				}
				*/
				Company ca = new Company();
				boolean flag =false;
				List<Company> list = this.companyService.queryByVO(ca);
				for(int i=0;i<list.size();i++){
					Company cp = list.get(i);
					if(company.getCompanyname().equals(cp.getCompanyname())){
						flag = true;
						break;
					}
				}
				if(flag){
					if(company.getType().equals("1")){
						showMessage(request, "\""+company.getCompanyname()+"\"��ҵ�Ѵ���!");
					}
					if(company.getType().equals("2")){
						showMessage(request, "\""+company.getCompanyname()+"\"�����Ѵ���!");
					}
					mnv.addObject(WebConfig.DATA_NAME, company);
					mnv.setViewName(this.getAddView());
				}else{
					this.companyService.add(company);
					showMessage(request, "�����ɹ�!");
					mnv.setViewName("admin/message");
				}
			} else if("2".equals(leixin)){//2����ϩ���ܱ�
				//��ҵ�� ����ϩ���ܱ���Ϣ
				String capacity = ServletRequestUtils.getStringParameter(request, "capacity", "");//����
				String production = ServletRequestUtils.getStringParameter(request, "production", "");//����
				String ptime = ServletRequestUtils.getStringParameter(request, "ptime", "");//ʱ��
				String polytenetype = ServletRequestUtils.getStringParameter(request, "polytenetype", "");//����ϩ��������
				polytene=new Polytene();
				polytene.setCompanyid(companyid);
				polytene.setCapacity(capacity);
				polytene.setProduction(production);
				polytene.setPtime(ptime);
				polytene.setType(polytenetype);
				polyteneService.add(polytene);
				showMessage(request, "�����ɹ�!");
				mnv.setViewName("admin/message");
			} else if("3".equals(leixin)){//3������
				//��ҵ�� ��������Ϣ
				String importsl = ServletRequestUtils.getStringParameter(request, "importsl", "");//������
				String importse = ServletRequestUtils.getStringParameter(request, "importse", "");//���ڶ�
				String exportsl = ServletRequestUtils.getStringParameter(request, "exportsl", "");//������
				String exportse = ServletRequestUtils.getStringParameter(request, "exportse", "");//���ڶ�
				String htime = ServletRequestUtils.getStringParameter(request, "htime", "");//���
				String impaexptype = ServletRequestUtils.getStringParameter(request, "impaexptype", "");//����
				impaexp=new Impaexp();
				impaexp.setCompanyid(companyid);
				impaexp.setImportsl(importsl);
				impaexp.setImportse(importse);
				impaexp.setExportsl(exportsl);
				impaexp.setExportse(exportse);
				impaexp.setHtime(htime);
				impaexp.setType(impaexptype);
				impaexpService.add(impaexp);
				showMessage(request, "�����ɹ�!");
				mnv.setViewName("admin/message");
			}	
			

			
		} catch (AppException e) {
			logger.error("����[" + company + "]ʧ��", e);
			showMessage(request, "����ʧ�ܣ�" + e.getMessage(), e);
			//����ʧ�ܺ�Ӧ������д������������ʾ����ҵ��
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, company);
		}

		return mnv;
	}

	/**
	 * �����޸ĵ���ҵ��
	 */
	@RequestMapping("/company.saveedit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		String id = ServletRequestUtils.getStringParameter(request, "appID", "");//ID
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String companyid = ServletRequestUtils.getStringParameter(request, "companyid", "");//������ҵ/����,ID
		Company company = null;
		Polytene polytene=null;
		Impaexp impaexp=null;
		try {
			if("1".equals(leixin)){//1��ҵ����
				company = param2Object(request);
				company.setId(id);
				/*
				if("1".equals(company.getType())){//1 ��ҵ ����ϩ���ܱ���Ϣ
					company.setTemp_capacity_exportsl(capacity);
					company.setTemp_production_exportse(production);
					company.setTemp_ptime_htime(ptime);
				}
				if("2".equals(company.getType())){ //2 ���� ��������Ϣ
					company.setTemp_importsL(importsl);
					company.setTemp_importsE(importse);
					company.setTemp_capacity_exportsl(exportsl);
					company.setTemp_production_exportse(exportse);
					company.setTemp_ptime_htime(htime);
				}
				*/
				this.companyService.update(company);
			} else if("2".equals(leixin)){//2����ϩ���ܱ�
				//��ҵ�� ����ϩ���ܱ���Ϣ
				String capacity = ServletRequestUtils.getStringParameter(request, "capacity", "");//����
				String production = ServletRequestUtils.getStringParameter(request, "production", "");//����
				String ptime = ServletRequestUtils.getStringParameter(request, "ptime", "");//ʱ��
				String polytenetype = ServletRequestUtils.getStringParameter(request, "polytenetype", "");//����ϩ��������
				polytene=new Polytene();
				polytene.setId(id);
				polytene.setCompanyid(companyid);
				polytene.setCapacity(capacity);
				polytene.setProduction(production);
				polytene.setPtime(ptime);
				polytene.setType(polytenetype);
				this.polyteneService.update(polytene);
			} else if("3".equals(leixin)){//3������
				//��ҵ�� ��������Ϣ
				String importsl = ServletRequestUtils.getStringParameter(request, "importsl", "");//������
				String importse = ServletRequestUtils.getStringParameter(request, "importse", "");//���ڶ�
				String exportsl = ServletRequestUtils.getStringParameter(request, "exportsl", "");//������
				String exportse = ServletRequestUtils.getStringParameter(request, "exportse", "");//���ڶ�
				String htime = ServletRequestUtils.getStringParameter(request, "htime", "");//���
				String impaexptype = ServletRequestUtils.getStringParameter(request, "impaexptype", "");//����
				impaexp=new Impaexp();
				impaexp.setId(id);
				impaexp.setCompanyid(companyid);
				impaexp.setImportsl(importsl);
				impaexp.setImportse(importse);
				impaexp.setExportsl(exportsl);
				impaexp.setExportse(exportse);
				impaexp.setHtime(htime);
				impaexp.setType(impaexptype);
				impaexpService.update(impaexp);
			}
			showMessage(request, "�޸ĳɹ�!");
		} catch (AppException e) {
			logger.error("�޸�[" + company + "]ʧ��", e);
			showMessage(request, "�޸�ʧ�ܣ�" + e.getMessage(), e);
			//�޸�ʧ�ܺ�������ʾ�޸�ҳ��
			return edit(request, response);
		}

		return new ModelAndView("admin/message");
	}

	/**
	 * ɾ��ѡ�е���ҵ��
	 */
	@RequestMapping("/company.delete.htm")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String app_id = ServletRequestUtils.getStringParameter(request, "app_id", "");//�� ';' ����ƴ�ӵ�ID
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String[] app_ids = app_id.split(";");
		//String[] companys = ServletRequestUtils.getStringParameters(request, "app_id");
		//������ɾ���ɹ�
		try {
			if("1".equals(leixin)){//1��ҵ����
				for (String id : app_ids) {
					this.companyService.delete(id);
				}
			} else if("2".equals(leixin)){//2����ϩ���ܱ�
				for (String id : app_ids) {
					this.polyteneService.delete(id);
				}
			} else if("3".equals(leixin)){//3������
				for (String id : app_ids) {
					this.impaexpService.delete(id);
				}
			}
			showMessage(request, "ɾ���ɹ�");
		} catch (AppException e) {
			logger.error("����ɾ��ʱʧ��", e);
			showMessage(request, "ɾ��ʧ�ܣ�" + e.getMessage(), e);
		}

		return query(request, response);
	}
	/**
	 * ͼ��
	 */
	@RequestMapping("/company.barShow.htm")
	public ModelAndView barShow(HttpServletRequest request, HttpServletResponse response) {
		 String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		 ModelAndView mnv = new ModelAndView();
		 Company company = new Company();	
		 List<Company> companylist=null;
		 if("1".equals(leixin)){//1��ҵ����
		
		 }else if("2".equals(leixin)){//2����ϩ���ܱ�
			company.setType("1");
			String search_polytenetype = ServletRequestUtils.getStringParameter(request, "search_polytenetype", "");
			String search_ptime = ServletRequestUtils.getStringParameter(request, "search_ptime", "");
			Polytene polytene=new Polytene();
			if(search_polytenetype !=null && search_polytenetype.length()>0){
				polytene.setType(search_polytenetype);
			}else{
				polytene.setType("LDPE");
			}
			List<String> timeList = this.getPolyteneTime(polytene);
			if(search_ptime !=null && search_ptime.length()>0){
				polytene.setPtime(search_ptime);
			}else{
				if(timeList.size()>0)
				polytene.setPtime(timeList.get(0));
			}
			List<Polytene> polytenelist= this.polyteneService.queryByVO(polytene); 
			for(Polytene polytenenew :polytenelist){
				  polytenenew.setCompanyid(this.companyService.queryById(polytenenew.getCompanyid()).getCompanyname());
			}
			List<String> companyname=new ArrayList<String>();
			List<String> capacity=new ArrayList<String>();
			List<String> production=new ArrayList<String>();
			for(Polytene po:polytenelist){
				if (!po.getCompanyid().equals("ȫ��")) {
					companyname.add("'"+po.getCompanyid()+"'");
					capacity.add((po.getCapacity() == null)?"0":po.getCapacity());
					production.add((po.getProduction() == null)?"0":po.getProduction());
				}
				
			}
			mnv.addObject("ptime", polytene.getPtime());
			mnv.addObject("ptimeList", timeList);
			mnv.addObject("polytenetype", polytene.getType());
			mnv.addObject("companyname", companyname);
			mnv.addObject("capacity", capacity);
			mnv.addObject("production", production);
			mnv.setViewName("company/polytene/bar");
		 }else if("3".equals(leixin)){//3������
			 company.setType("2");
			 String search_impaexptype = ServletRequestUtils.getStringParameter(request, "search_impaexptype", "");
			 String search_htime = ServletRequestUtils.getStringParameter(request, "search_htime", "");
			 Impaexp impaexp=new Impaexp();
			 if(search_impaexptype !=null && search_impaexptype.length()>0){
				 impaexp.setType(search_impaexptype);
			 }else{
				 impaexp.setType("LDPE");
			 }
			 List<String> timeList = this.getImpaexpTime(impaexp);
			 if(search_htime !=null && search_htime.length()>0){
				 impaexp.setHtime(search_htime);
			 }else{
				 if(timeList.size()>0)
				 impaexp.setHtime(timeList.get(0));
			 }
			 
			 List<Impaexp> impaexplist=this.impaexpService.queryByVO(impaexp);
			 for(Impaexp impaexpnew : impaexplist){
				  impaexpnew.setCompanyid(this.companyService.queryById(impaexpnew.getCompanyid()).getCompanyname());
			 }
			 List<String> importsl=new ArrayList<String>();
			 List<String> exportsl=new ArrayList<String>();
			 List<String> comanyname=new ArrayList<String>();
			 for(Impaexp im:impaexplist){
				 if(!(im.getCompanyid().equals("ȫ��"))){
				 importsl.add((im.getImportsl()==null)?"0":im.getImportsl());
				 exportsl.add((im.getExportsl()==null)?"0":im.getExportsl());
				 comanyname.add("'"+im.getCompanyid()+"'");
				 }
			 }
			 mnv.addObject("htime", impaexp.getHtime());
			 mnv.addObject("htimeList", timeList);
			 mnv.addObject("impaexptype", impaexp.getType());
			 mnv.addObject("importsl",importsl );
			 mnv.addObject("exportsl",exportsl);
			 mnv.addObject("comanyname",comanyname);
			 mnv.setViewName("company/impaexp/bar");
		 }
		return mnv;
	}
	public List<String> getImpaexpTime(Impaexp impaexp){
		List<String> timeList = new ArrayList<String>();
		List<Impaexp> impaexplist= this.impaexpService.queryByVO(impaexp);
		for(int i=0;i<impaexplist.size();i++){
			Impaexp im = impaexplist.get(i);
			boolean flag = true;
			for(int j=0;j<timeList.size();j++){
				if(timeList.get(j).equals(im.getHtime())){
					flag = false;
				}
			}
			if(flag){
				timeList.add(im.getHtime());
			}
		}
		return timeList;
		
	}
	public List<String> getPolyteneTime(Polytene polytene){
		List<String> timeList = new ArrayList<String>();
		List<Polytene> polytenelist= this.polyteneService.queryByVO(polytene);
		for(int i=0;i<polytenelist.size();i++){
			Polytene p = polytenelist.get(i);
			boolean flag = true;
			for(int j=0;j<timeList.size();j++){
				if(timeList.get(j).equals(p.getPtime())){
					flag = false;
				}
			}
			if(flag){
				timeList.add(p.getPtime());
			}
		}
		return timeList;
		
	}
	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}