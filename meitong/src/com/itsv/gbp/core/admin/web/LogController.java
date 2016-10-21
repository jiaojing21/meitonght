package com.itsv.gbp.core.admin.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.LogService;
import com.itsv.gbp.core.admin.vo.AppLog;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

/**
 * 说明：日志前端处理类。仅处理对日志的分页查询。<br>
 * 
 * 该类主要演示如下功能：
 * <ol>
 * <li>对于不能自动转换的类型，通过registerEditor()方法注册自定义的类型转换器。<li>
 * <li>通过重写doQuery(),getQueryName()方法实现带缓存的分页查询.</li>
 * </ol>
 * 
 * @author admin 2005-2-4
 */
public class LogController extends BaseCURDController<AppLog> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(LogController.class);

	private static final String QUERY_NAME = "query.log"; //查询结果在session里的存储名称

	private LogService logService; //逻辑层对象

	/**
	 * 注册自定义类型转换类，将查询的起始日期和截至日期字符串转换为Date对象。
	 */
	protected void registerEditor(DataBinder binder) {
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
	}

	//覆盖父类方法，默认执行query()
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		AppLog log = param2Object(request);
		this.logService.queryByVO(records, log);

		//将查询参数返回给页面
		mnv.addObject("log", log);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}