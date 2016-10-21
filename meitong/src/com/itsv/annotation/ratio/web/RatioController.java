package com.itsv.annotation.ratio.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.annotation.company.vo.Company;
import com.itsv.annotation.company.vo.Impaexp;
import com.itsv.annotation.company.vo.Polytene;
import com.itsv.annotation.ratio.bo.RatioService;
import com.itsv.annotation.ratio.bo.RatioSubService;
import com.itsv.annotation.ratio.vo.Ratio;
import com.itsv.annotation.ratio.vo.RatioSub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
import com.itsv.platform.BizFile;
/**
 * 说明：增加，修改，删除比例表的前端处理类
 * 
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */
@Controller
public class RatioController extends BaseAnnotationController<Ratio> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(RatioController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.ratio";
	@Autowired
	private RatioService ratioService; //逻辑层对象
	@Autowired
	private RatioSubService ratioSubService; //逻辑层对象

	public RatioController(){

		super.setDefaultCheckToken(true);
		super.setAddView("ratio/ratio/add");
		super.setIndexView("ratio/ratio/index");
		super.setEditView("ratio/ratio/edit");

	}

	//覆盖父类方法，默认执行query()，分页显示数据

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}
	/**
	 * 
	 */
	@RequestMapping("/zsfx.ratio.htm")
  	public ModelAndView ratioControl(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) throws AppException {
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String type_view="ratio/index";
		mnv.addObject("type", type);
		mnv.setViewName(type_view);
		return mnv;
  	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/zsfx.ratioData.htm")
	@ResponseBody
	public Map<String, Object> indexAppControl(HttpServletRequest request, HttpServletResponse response) throws AppException {
	  Map<String, Object> map = new HashMap<String, Object>();
	  String type = ServletRequestUtils.getStringParameter(request, "type", "");
	  Ratio ratio = new Ratio();
	  ratio.setType(type);
	  CachePagedList records = PagedListTool.getEuiPagedList(request, "ratio");	
	  this.ratioService.queryByVO(records, ratio);
	  map.put("total", records.getTotalNum());
	  map.put("rows", records.getSource());
  	  return ResponseUtils.sendMap(map);
  	    
	}
	@RequestMapping("/zsfx.ratioadd.htm")
  	public ModelAndView ratioAddControl(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) throws AppException {
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String type_view="ratio/add";
		mnv.addObject("type", type);
		mnv.setViewName(type_view);
		return mnv;
  	}
	@RequestMapping("/zsfx.getRatio.htm")
  	public ModelAndView getRatio(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) throws AppException {
		String type = ServletRequestUtils.getStringParameter(request, "type", "");
		String type_view="ratio/index";
		mnv.addObject("type", type);
		mnv.setViewName(type_view);
		return mnv;
  	}
	@SuppressWarnings("deprecation")
	public String[] upLoadFile(HttpServletRequest request, String fileName) {
		String [] res = new String[2];
		String fileName1 = "";
		String path = "";
		String url = "";
		try {
			MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
			MultipartFile partFile = mrequest.getFile(fileName);
			if (partFile != null && partFile.getSize() > 0) {
				fileName1 = partFile.getOriginalFilename();
				String fname =fileName1.substring(fileName1.lastIndexOf("."));
				long time = new Date().getTime();
				if (fileName1 != null && fileName1.length() > 0) {
					path = request.getRealPath("/") + "File\\"//这里的qwpkFile\\跟批示文件存储路径不同
					+ time+fname;//保存时保存为数字的文件名
					url = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + "/"
					+ request.getContextPath() + "/File/"
					+ time+fname;
					res[0] = path;
					res[1] = url;
					File file = new File(path);
					file.getParentFile().mkdirs();
					try {
						partFile.transferTo(file);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						showMessage(request, "文件上传失败：" + e.getMessage(), e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						showMessage(request, "没有找到上传路径：" + e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res[0] = "";
			res[1] = "";
			return res;
		}
		return res;
	}
	/**
	 * 上传图标文件
	 * @param request
	 * @param response
	 * @param mnv
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ajaxUploadIconFile(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws Exception {
		response.setHeader("Content-Type", "text/html");  
		mnv = new ModelAndView(getAddView());
		String field = request.getParameter("field");
		BizFile file = doSingleFileUpload(request, field,"/images/ratio/");
//		request.setAttribute("data", JSONObject.fromObject(file));
		//request.setAttribute(WebConfig.DATA_NAME, file);
		String fileName = file.getNewFileName();
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		if(fileType.equals("jpg") ||fileType.equals("jpeg") ||fileType.equals("png")){
			response.getWriter().print(JSONObject.fromObject(file).toString());
		}
		
		return null;
	}
	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Ratio ratio = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			ratio = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", ratio);
		} else {
			ratio = new Ratio();
		}

		this.ratioService.queryByVO(records, ratio);
	}

	//显示增加比例表页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改比例表页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Ratio ratio = this.ratioService.queryById(id);
		if (null == ratio) {
			showMessage(request, "未找到对应的比例表记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, ratio);
		}
	}

	/**
	 * 保存新增比例表
	 */
	@RequestMapping("/zsfx.ratioSaveAdd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv=new ModelAndView();
		Ratio ratio = null;
		try {
			ratio = param2Object(request);
			String []pic = this.upLoadFile(request, "picfileurl");
			String []con = this.upLoadFile(request, "confileurl");
			if(pic.length>1){
				ratio.setPicurl(pic[1]);
			}
			
			//解析excel放入子表中
			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, ratio)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ratio);
			//}

			this.ratioService.add(ratio);
			if(con.length>1){
				if(con[0].length()>0){
					try {
						List<List<String>> list = ReadExcel.readExcel(con[0]);
						if(ratio.getType().equals("1")){
							for(int i=1;i<list.size();i++){
								RatioSub rs = new RatioSub();
								List<String> slist = list.get(i);
								rs.setRname(slist.get(1));
								rs.setDataone(slist.get(2));
								rs.setRatioid(ratio.getId());
								rs.setPx(i+0L);
								this.ratioSubService.add(rs);
							}
						}
						if(ratio.getType().equals("2")){
							for(int i=1;i<list.size();i++){
								RatioSub rs = new RatioSub();
								List<String> slist = list.get(i);
								rs.setRname(slist.get(1));
								rs.setDataone(slist.get(2));
								rs.setRatioid(ratio.getId());
								rs.setPx(i+0L);
								rs.setDatatwo(slist.get(3));
								this.ratioSubService.add(rs);
							}
						}
					} catch (InvalidFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			showMessage(request, "新增比例表成功");
			mnv.setViewName("admin/message");
		} catch (AppException e) {
			logger.error("新增比例表[" + ratio + "]失败", e);
			showMessage(request, "新增比例表失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给比例表
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, ratio);
		}

		return mnv;
	}

	/**
	 * 保存修改的比例表
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Ratio ratio = null;
		try {
			ratio = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, ratio)) {
			//	return edit(request, response);
			//}

			this.ratioService.update(ratio);
			showMessage(request, "修改比例表成功");
		} catch (AppException e) {
			logger.error("修改比例表[" + ratio + "]失败", e);
			showMessage(request, "修改比例表失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的比例表
	 */
	@RequestMapping("/zsfx.ratioDelete.htm")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mnv=new ModelAndView();
		String app_id = ServletRequestUtils.getStringParameter(request, "app_id", "");//以 ';' 符号拼接的ID
		String[] app_ids = app_id.split(";");
		//允许部分删除成功
		try {
			for (String id : app_ids) {
				this.ratioService.delete(id);
				RatioSub rs = new RatioSub();
				rs.setRatioid(id);
				List<RatioSub> list =this.ratioSubService.queryByVO(rs);
				for(RatioSub ra : list){
					this.ratioSubService.delete(ra.getId());
				}
			}
			showMessage(request, "删除比例表成功");
			
		} catch (AppException e) {
			logger.error("批量删除比例表时失败", e);
			showMessage(request, "删除比例表失败：" + e.getMessage(), e);
		}
		mnv.setViewName("admin/message");
		return mnv;
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setRatioService(RatioService ratioService) {
		this.ratioService = ratioService;
	}
}