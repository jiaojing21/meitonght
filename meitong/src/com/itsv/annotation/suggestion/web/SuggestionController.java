package com.itsv.annotation.suggestion.web;

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
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.suggestion.bo.SuggestionService;
import com.itsv.annotation.suggestion.vo.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除suggestion的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller

public class SuggestionController extends BaseAnnotationController<Suggestion> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(SuggestionController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.suggestion";
	@Autowired
	private SuggestionService suggestionService; //逻辑层对象

	public SuggestionController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/suggestion/add");
		super.setIndexView("meitong/suggestion/index");
		super.setEditView("meitong/suggestion/edit");

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
		Suggestion suggestion = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			suggestion = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", suggestion);
		} else {
			suggestion = new Suggestion();
		}

		this.suggestionService.queryByVO(records, suggestion);
	}

	//显示增加suggestion页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改suggestion页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Suggestion suggestion = this.suggestionService.queryById(id);
		if (null == suggestion) {
			showMessage(request, "未找到对应的suggestion记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, suggestion);
		}
	}
		// 查询数据准备
		@RequestMapping("/suggestion.query.htm")
		public ModelAndView querySuggestion(HttpServletRequest request,
				HttpServletResponse response, ModelAndView mnv) {
			mnv.setViewName(getIndexView());
			return mnv;
		}

		// 查询列表
		@RequestMapping("/suggestion.BeforeData.htm")
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
			Suggestion suggestion = new Suggestion();
			suggestion.setContent(shmc);
			CachePagedList records = PagedListTool.getEuiPagedList(request,
					"suggestion");
			this.suggestionService.queryByVO(records, suggestion);
			map.put("total", records.getTotalNum());
			map.put("rows", records.getSource());
			return ResponseUtils.sendMap(map);
		}

	// 详情
	@RequestMapping("/suggestion.detail.htm")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (id != "" && id != null) {
			Suggestion suggestion = this.suggestionService.queryById(id);
			mnv.addObject("data", suggestion);
			mnv.setViewName("/meitong/suggestion/detail");
		} else {
			showMessage(request, "没有查到该数据");
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	//回复
	@RequestMapping("/suggestion.detailupt.htm")
	public ModelAndView detailupt(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String id = "";
		String answer = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
			answer = request.getParameter("p_Suggestion_answer");
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Suggestion suggestion = this.suggestionService.queryById(id);
			suggestion.setFlag("1");
			suggestion.setAnswer(answer);
			this.suggestionService.update(suggestion);
			showMessage(request, "操作成功");		
			mnv.setViewName("admin/message1");
		}else{
			showMessage(request, "没有查到该数据");			
			mnv.setViewName("admin/message1");
		}
		return mnv;
	}
	/**
	 * 保存新增suggestion
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Suggestion suggestion = null;
		try {
			suggestion = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, suggestion)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, suggestion);
			//}

			this.suggestionService.add(suggestion);

			showMessage(request, "新增suggestion成功");
		} catch (AppException e) {
			logger.error("新增suggestion[" + suggestion + "]失败", e);
			showMessage(request, "新增suggestion失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给suggestion
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, suggestion);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的suggestion
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Suggestion suggestion = null;
		try {
			suggestion = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, suggestion)) {
			//	return edit(request, response);
			//}

			this.suggestionService.update(suggestion);
			showMessage(request, "修改suggestion成功");
		} catch (AppException e) {
			logger.error("修改suggestion[" + suggestion + "]失败", e);
			showMessage(request, "修改suggestion失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的suggestion
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] suggestions = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : suggestions) {
				this.suggestionService.delete(id);
			}
			showMessage(request, "删除suggestion成功");
		} catch (AppException e) {
			logger.error("批量删除suggestion时失败", e);
			showMessage(request, "删除suggestion失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}
}