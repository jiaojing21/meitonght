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
 * 说明：增加，修改，删除企业表的前端处理类
 * 
 * @author quyf
 * @since 2014-10-23
 * @version 1.0
 */
@Controller

public class CompanyController extends BaseAnnotationController<Company> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(CompanyController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.company";
	@Autowired
	private CompanyService companyService; //逻辑层对象
	
	@Autowired
	private ImpaexpService impaexpService; //进出口的逻辑层对象
	  
	@Autowired
	private PolyteneService polyteneService; //聚乙烯产能逻辑层对象

	public CompanyController(){

		super.setDefaultCheckToken(true);
		super.setAddView("company/add");
		super.setIndexView("company/index");
		super.setEditView("company/edit");
		super.setShowView("company/showdetail");

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
		Company company = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			company = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", company);
		} else {
			company = new Company();
		}

		this.companyService.queryByVO(records, company);
	}
	
	/**
	 * 转到查询页面
	 * @return ModelAndView 转到的页面对象
	 */
	@RequestMapping("/company.showSearch.htm")
	public ModelAndView showSearch(HttpServletRequest request, HttpServletResponse response){
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		ModelAndView mnv = new ModelAndView();
		Company company = new Company();
		List<Company> companylist=null;
		if("1".equals(leixin)){//1企业海关
			mnv.setViewName("company/showsearch");
		} else if("2".equals(leixin)){//2聚乙烯产能表
			company.setType("1");
			companylist=this.companyService.queryByVO(company);
			mnv.setViewName("company/polytene/showsearch");
		} else if("3".equals(leixin)){//3进出口
			company.setType("2");
			companylist=this.companyService.queryByVO(company);
			mnv.setViewName("company/impaexp/showsearch");
		}
		mnv.addObject("companylist", companylist);
		return mnv;
		
	}
	
	/**
	    * 跳转到显示企业信息界面
	    * @param request
	    * @param response
	    * @param mnv 
	    * @return 要跳转的页面路径
	    * @throws AppException
	    */
		@RequestMapping("/company.company.htm")
	  	public ModelAndView toAppControl(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) throws AppException {
			String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
			String code = ServletRequestUtils.getStringParameter(request, "code", "");
			String type_view="";
			mnv = new ModelAndView();
			if("1".equals(leixin)){//1企业海关
				type_view=this.getIndexView();				
			} else if("2".equals(leixin)){//2聚乙烯产能表
				type_view="company/polytene/index";
			} else if("3".equals(leixin)){//3进出口
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
		  if("1".equals(leixin)){//1企业海关
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
		  }else if("2".equals(leixin)){//2聚乙烯产能表
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
		  } else if("3".equals(leixin)){//3进出口
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
	//显示增加企业表页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}
	
	/**
	 * 跳转到添加页面
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
		if("1".equals(leixin)){//1企业海关
			mnv.setViewName(this.getAddView());//设置返回的文件名  
		} else if("2".equals(leixin)){//2聚乙烯产能表
			company.setType("1");
			companylist=this.companyService.queryByVO(company);
			mnv.addObject("companylist", companylist);
			mnv.setViewName("company/polytene/add");
		} else if("3".equals(leixin)){//3进出口
			company.setType("2");
			companylist=this.companyService.queryByVO(company);
			mnv.addObject("companylist", companylist);
			mnv.setViewName("company/impaexp/add");
		}	
		mnv.addObject("code", code);
        return mnv;
  	}
	//显示修改企业表页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		
		Company company = this.companyService.queryById(id);
		if (null == company) {
			showMessage(request, "未找到对应的企业表记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, company);
		}
	}
	
	/**
	 * 跳转到修改页面
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
		if("1".equals(leixin)){//1企业海关
			if (null == company) {
				showMessage(request, "未找到对应的企业表记录。请重试");
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
		} else if("2".equals(leixin)){//2聚乙烯产能表
			Company company2 = new Company();
			company2.setType("1");
			List<Company> companylist=this.companyService.queryByVO(company2);
			polytene=this.polyteneService.queryById(id);
			mnv.addObject("companylist", companylist);
			mnv.addObject("data_polytene", polytene);
			mnv.setViewName("company/polytene/edit");
		} else if("3".equals(leixin)){//3进出口
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
	 * 保存新增企业表
	 */
	@RequestMapping("/company.save.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv=new ModelAndView();
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String code = ServletRequestUtils.getStringParameter(request, "code", "");
		String companyid = ServletRequestUtils.getStringParameter(request, "companyid", "");//共用企业/海关,ID
		Company company = null;
		Polytene polytene=null;
		Impaexp impaexp=null;
		try {
			if("1".equals(leixin)){//1企业海关
				company = param2Object(request);
				/*
				if("1".equals(company.getType())){//1 企业 聚乙烯产能表信息
					company.setTemp_capacity_exportsl(capacity);
					company.setTemp_production_exportse(production);
					company.setTemp_ptime_htime(ptime);
				}
				if("2".equals(company.getType())){ //2 海关 进出口信息
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
						showMessage(request, "\""+company.getCompanyname()+"\"企业已存在!");
					}
					if(company.getType().equals("2")){
						showMessage(request, "\""+company.getCompanyname()+"\"海关已存在!");
					}
					mnv.addObject(WebConfig.DATA_NAME, company);
					mnv.setViewName(this.getAddView());
				}else{
					this.companyService.add(company);
					showMessage(request, "新增成功!");
					mnv.setViewName("admin/message");
				}
			} else if("2".equals(leixin)){//2聚乙烯产能表
				//企业的 聚乙烯产能表信息
				String capacity = ServletRequestUtils.getStringParameter(request, "capacity", "");//产能
				String production = ServletRequestUtils.getStringParameter(request, "production", "");//产量
				String ptime = ServletRequestUtils.getStringParameter(request, "ptime", "");//时间
				String polytenetype = ServletRequestUtils.getStringParameter(request, "polytenetype", "");//聚乙烯产能类型
				polytene=new Polytene();
				polytene.setCompanyid(companyid);
				polytene.setCapacity(capacity);
				polytene.setProduction(production);
				polytene.setPtime(ptime);
				polytene.setType(polytenetype);
				polyteneService.add(polytene);
				showMessage(request, "新增成功!");
				mnv.setViewName("admin/message");
			} else if("3".equals(leixin)){//3进出口
				//企业的 进出口信息
				String importsl = ServletRequestUtils.getStringParameter(request, "importsl", "");//进口量
				String importse = ServletRequestUtils.getStringParameter(request, "importse", "");//进口额
				String exportsl = ServletRequestUtils.getStringParameter(request, "exportsl", "");//出口量
				String exportse = ServletRequestUtils.getStringParameter(request, "exportse", "");//出口额
				String htime = ServletRequestUtils.getStringParameter(request, "htime", "");//年份
				String impaexptype = ServletRequestUtils.getStringParameter(request, "impaexptype", "");//类型
				impaexp=new Impaexp();
				impaexp.setCompanyid(companyid);
				impaexp.setImportsl(importsl);
				impaexp.setImportse(importse);
				impaexp.setExportsl(exportsl);
				impaexp.setExportse(exportse);
				impaexp.setHtime(htime);
				impaexp.setType(impaexptype);
				impaexpService.add(impaexp);
				showMessage(request, "新增成功!");
				mnv.setViewName("admin/message");
			}	
			

			
		} catch (AppException e) {
			logger.error("新增[" + company + "]失败", e);
			showMessage(request, "新增失败：" + e.getMessage(), e);
			//增加失败后，应将已填写的内容重新显示给企业表
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, company);
		}

		return mnv;
	}

	/**
	 * 保存修改的企业表
	 */
	@RequestMapping("/company.saveedit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		String id = ServletRequestUtils.getStringParameter(request, "appID", "");//ID
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String companyid = ServletRequestUtils.getStringParameter(request, "companyid", "");//共用企业/海关,ID
		Company company = null;
		Polytene polytene=null;
		Impaexp impaexp=null;
		try {
			if("1".equals(leixin)){//1企业海关
				company = param2Object(request);
				company.setId(id);
				/*
				if("1".equals(company.getType())){//1 企业 聚乙烯产能表信息
					company.setTemp_capacity_exportsl(capacity);
					company.setTemp_production_exportse(production);
					company.setTemp_ptime_htime(ptime);
				}
				if("2".equals(company.getType())){ //2 海关 进出口信息
					company.setTemp_importsL(importsl);
					company.setTemp_importsE(importse);
					company.setTemp_capacity_exportsl(exportsl);
					company.setTemp_production_exportse(exportse);
					company.setTemp_ptime_htime(htime);
				}
				*/
				this.companyService.update(company);
			} else if("2".equals(leixin)){//2聚乙烯产能表
				//企业的 聚乙烯产能表信息
				String capacity = ServletRequestUtils.getStringParameter(request, "capacity", "");//产能
				String production = ServletRequestUtils.getStringParameter(request, "production", "");//产量
				String ptime = ServletRequestUtils.getStringParameter(request, "ptime", "");//时间
				String polytenetype = ServletRequestUtils.getStringParameter(request, "polytenetype", "");//聚乙烯产能类型
				polytene=new Polytene();
				polytene.setId(id);
				polytene.setCompanyid(companyid);
				polytene.setCapacity(capacity);
				polytene.setProduction(production);
				polytene.setPtime(ptime);
				polytene.setType(polytenetype);
				this.polyteneService.update(polytene);
			} else if("3".equals(leixin)){//3进出口
				//企业的 进出口信息
				String importsl = ServletRequestUtils.getStringParameter(request, "importsl", "");//进口量
				String importse = ServletRequestUtils.getStringParameter(request, "importse", "");//进口额
				String exportsl = ServletRequestUtils.getStringParameter(request, "exportsl", "");//出口量
				String exportse = ServletRequestUtils.getStringParameter(request, "exportse", "");//出口额
				String htime = ServletRequestUtils.getStringParameter(request, "htime", "");//年份
				String impaexptype = ServletRequestUtils.getStringParameter(request, "impaexptype", "");//类型
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
			showMessage(request, "修改成功!");
		} catch (AppException e) {
			logger.error("修改[" + company + "]失败", e);
			showMessage(request, "修改失败：" + e.getMessage(), e);
			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return new ModelAndView("admin/message");
	}

	/**
	 * 删除选中的企业表
	 */
	@RequestMapping("/company.delete.htm")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		String app_id = ServletRequestUtils.getStringParameter(request, "app_id", "");//以 ';' 符号拼接的ID
		String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		String[] app_ids = app_id.split(";");
		//String[] companys = ServletRequestUtils.getStringParameters(request, "app_id");
		//允许部分删除成功
		try {
			if("1".equals(leixin)){//1企业海关
				for (String id : app_ids) {
					this.companyService.delete(id);
				}
			} else if("2".equals(leixin)){//2聚乙烯产能表
				for (String id : app_ids) {
					this.polyteneService.delete(id);
				}
			} else if("3".equals(leixin)){//3进出口
				for (String id : app_ids) {
					this.impaexpService.delete(id);
				}
			}
			showMessage(request, "删除成功");
		} catch (AppException e) {
			logger.error("批量删除时失败", e);
			showMessage(request, "删除失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}
	/**
	 * 图表
	 */
	@RequestMapping("/company.barShow.htm")
	public ModelAndView barShow(HttpServletRequest request, HttpServletResponse response) {
		 String leixin = ServletRequestUtils.getStringParameter(request, "leixin", "");
		 ModelAndView mnv = new ModelAndView();
		 Company company = new Company();	
		 List<Company> companylist=null;
		 if("1".equals(leixin)){//1企业海关
		
		 }else if("2".equals(leixin)){//2聚乙烯产能表
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
				if (!po.getCompanyid().equals("全国")) {
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
		 }else if("3".equals(leixin)){//3进出口
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
				 if(!(im.getCompanyid().equals("全国"))){
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
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}