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
 * 说明：增加，修改，删除brand的前端处理类
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
@Controller
public class BrandController extends BaseAnnotationController<Brand> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(BrandController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.brand";
	@Autowired
	private BrandService brandService; //逻辑层对象
	
	@Autowired
	private Itsv_dictionaryService itsv_dictionaryService;//逻辑层对象
	
	public BrandController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/brand/add");
		super.setIndexView("meitong/brand/index");
		super.setEditView("meitong/brand/edit");

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
		Brand brand = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			brand = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", brand);
		} else {
			brand = new Brand();
		}

		this.brandService.queryByVO(records, brand);
	}

	//显示增加brand页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改brand页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Brand brand = this.brandService.queryById(id);
		if (null == brand) {
			showMessage(request, "未找到对应的brand记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, brand);
		}
	}
	public static String lpad(int length, int number) {
	      String f = "%0" + length + "d";
	      return String.format(f, number);
	  }	
	//跳转新增页面
	@RequestMapping("/brand.beforeadd.htm")
	public ModelAndView beforeadd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService
				.queryNextListByName("品类");
		mnv.addObject("tplist", tplist);
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * 保存新增brand
	 */
	@RequestMapping("/brand.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv  = new ModelAndView();
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService.queryNextListByName("品类");
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
			showMessage(request, "新增品牌成功");
		} catch (AppException e) {
			logger.error("新增品牌[" + brand + "]失败", e);
			showMessage(request, "新增品牌失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给brand
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, brand);
		}
		return mnv;
	}
	
	//查询品牌列表
	@RequestMapping("/brand.onready.htm")
	@ResponseBody
	public JSONArray onreadyBrand(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		JSONArray ja = new JSONArray();
		this.brandService.queryAll();
		return ja;
	}
	
	
	//查询数据准备
	@RequestMapping("/brand.query.htm")
	public ModelAndView queryBrand(HttpServletRequest request,HttpServletResponse response, ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//查询列表
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
	//编辑前数据
	@RequestMapping("/brand.EditData.htm")
	public ModelAndView editData(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService
				.queryNextListByName("品类");
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
	 * 保存修改的brand
	 */
	@RequestMapping("brand.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Brand brand = null;
		try {
			brand = param2Object(request);
			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, brand)) {
			//	return edit(request, response);
			//}
			brand.setFlag("1");
			this.brandService.update(brand);
			showMessage(request, "修改品牌成功");
		} catch (AppException e) {
			logger.error("修改品牌[" + brand + "]失败", e);
			showMessage(request, "修改品牌失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, brand);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, brand);
  	    
	}
	
	@RequestMapping("brand.showSearch.htm")
	public ModelAndView showSearch(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName("/meitong/brand/showsearch");
		return mnv;
	}
	//详情
	@RequestMapping("/brand.detail.htm")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv){
		String id ="";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Brand brand = this.brandService.queryById(id);
			mnv.addObject("data", brand);
			mnv.setViewName("/meitong/brand/detail");
		}else{
			showMessage(request, "没有查到该数据");			
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
			showMessage(request, "操作成功");		
			mnv.setViewName("admin/message1");
		}else{
			showMessage(request, "没有查到该数据");			
			mnv.setViewName("admin/message1");
		}
		return mnv;
	}
	/**
	 * 删除选中的brand
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] brands = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : brands) {
				this.brandService.delete(id);
			}
			showMessage(request, "删除brand成功");
		} catch (AppException e) {
			logger.error("批量删除brand时失败", e);
			showMessage(request, "删除brand失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
}