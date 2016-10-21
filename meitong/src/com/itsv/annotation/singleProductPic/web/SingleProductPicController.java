package com.itsv.annotation.singleProductPic.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.singleProductPic.bo.SingleProductPicService;
import com.itsv.annotation.singleProductPic.vo.SingleProductPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除single_product_pic的前端处理类
 * 
 * @author swk
 * @since 2016-04-11
 * @version 1.0
 */
@Controller
public class SingleProductPicController extends BaseAnnotationController<SingleProductPic> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(SingleProductPicController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.singleProductPic";
	@Autowired
	private SingleProductPicService singleProductPicService; //逻辑层对象

	@Autowired
	private FileUploadService fileUploadService;//逻辑层对象
	
	public SingleProductPicController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/singleProductPic/add");
		super.setIndexView("meitong/singleProductPic/index");
		super.setEditView("meitong/singleProductPic/edit");

	}

  /**
   * 注册自定义类型转换类，用来转换日期对象
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
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
		SingleProductPic singleProductPic = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			singleProductPic = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", singleProductPic);
		} else {
			singleProductPic = new SingleProductPic();
		}

		this.singleProductPicService.queryByVO(records, singleProductPic);
	}
	@RequestMapping("/single.beforeadd.htm")
	public ModelAndView addp(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv,String code){
		mnv.addObject("code", code);
		mnv.setViewName("/meitong/goods/addpic");
		return mnv;
	}
	@RequestMapping("/single.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName(getAddView());
		SingleProductPic singleProductPic = null;
		singleProductPic = param2Object(request);
		singleProductPic.setCreatetime(new Date());
		String manypic = request.getParameter("manypic");
		try{
			if(null!=manypic && !"".equals(manypic)){
				String [] manyid = manypic.split(",");
				for(String id : manyid){
					this.singleProductPicService.add(singleProductPic);
					FileUpload fileUpload = this.fileUploadService.queryById(id);
					fileUpload.setFId(singleProductPic.getId());
					this.fileUploadService.update(fileUpload);
				}
			}
		showMessage(request, "新增成功");
		}catch (AppException e) {
			logger.error("新增[" + singleProductPic + "]失败", e);
			showMessage(request, "新增失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给goods

			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, singleProductPic);
		}
		mnv.setViewName("/meitong/goods/addpic");
		return mnv;
	}
	//显示增加single_product_pic页面前，准备相关数据
	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改single_product_pic页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		SingleProductPic singleProductPic = this.singleProductPicService.queryById(id);
		if (null == singleProductPic) {
			showMessage(request, "未找到对应的single_product_pic记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, singleProductPic);
		}
	}

	/**
	 * 保存新增single_product_pic
	 */

	public ModelAndView saveAdd1(HttpServletRequest request, HttpServletResponse response) {
		SingleProductPic singleProductPic = null;
		try {
			singleProductPic = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, singleProductPic)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, singleProductPic);
			//}

			this.singleProductPicService.add(singleProductPic);

			showMessage(request, "新增single_product_pic成功");
		} catch (AppException e) {
			logger.error("新增single_product_pic[" + singleProductPic + "]失败", e);
			showMessage(request, "新增single_product_pic失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给single_product_pic
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, singleProductPic);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的single_product_pic
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		SingleProductPic singleProductPic = null;
		try {
			singleProductPic = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, singleProductPic)) {
			//	return edit(request, response);
			//}

			this.singleProductPicService.update(singleProductPic);
			showMessage(request, "修改single_product_pic成功");
		} catch (AppException e) {
			logger.error("修改single_product_pic[" + singleProductPic + "]失败", e);
			showMessage(request, "修改single_product_pic失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的single_product_pic
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] singleProductPics = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : singleProductPics) {
				this.singleProductPicService.delete(id);
			}
			showMessage(request, "删除single_product_pic成功");
		} catch (AppException e) {
			logger.error("批量删除single_product_pic时失败", e);
			showMessage(request, "删除single_product_pic失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setSingleProductPicService(SingleProductPicService singleProductPicService) {
		this.singleProductPicService = singleProductPicService;
	}
}