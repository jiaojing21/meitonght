package com.itsv.gbp.core.web.springmvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.GenericsUtils;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.WebException;
import com.itsv.platform.BizFile;

public class BaseAnnotationController <V> extends BaseController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseCURDController.class);

	private static final String DEFAULT_QUERY_NAME = "query.default";

	private String indexView; //对象列表显示页面

	private String addView; //对象新增页面

	private String editView; //对象修改页面

	private String showView; //详细信息显示页面

	protected Class<V> voClass;

	public BaseAnnotationController() {
		voClass = GenericsUtils.getGenericClass(getClass());	
	}

	/**
	 * 生成一个command对象，如果需要将传来的参数自动绑定到VO对象，就需要该方法
	 * 
	 * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController.newCommandObject()
	 * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController.invokeNamedMethod()
	 */
	protected Object newCommandObject(V vo) throws WebException {

		try {
			return BeanUtils.instantiateClass(getVoClass());
		} catch (Exception e) {
			logger.error("实例化类[" + getVoClass().getName() + "]时出错", e);
			throw new WebException("实例化类[" + getVoClass().getName() + "]时出错", e);
		}
	}

	//显示增加对象页面
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getAddView());
		mnv.addObject("TYPE", "vsf");
		beforeShowAdd(request, response, mnv);
		return mnv;
	}

	//显示修改页面
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getEditView());
		mnv.addObject("TYPE", "vsf");
		beforeShowEdit(request, response, mnv);
		return mnv;
	}

	//列表显示对象。不分页情况
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getIndexView());
		mnv.addObject("TYPE", "vsf");
		beforeShowList(request, response, mnv);
		return mnv;
	}


	//分页查询方法
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getIndexView());
		//下面的语句跟首次查询和翻页有关
		CachePagedList records = PagedListTool.getPagedList(request, getQueryName());
		if (null == records) {
			showMessage(request, "请首先设置查询参数");
		
			return mnv;
		}
		if (!records.exists()) {
			doQuery(request, response, mnv, records);
		}
		mnv.addObject("TYPE", "htm");
		mnv.addObject(WebConfig.DATA_NAME, records);
		return mnv;
	}

	//查询在session中的名称，为了区分不同的查询对象。建议子类重写，以便区分
	protected String getQueryName() {
		return DEFAULT_QUERY_NAME;
	}

	//进行实际的分页查询。需要子类重写
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
	}

	//显示增加页面的前处理方法。一般会填充一些辅助数据
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改页面的前处理方法。一般会填充待修改的对象和其他辅助数据
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
	}

	//显示列表页面的前处理方法。查询出所有列表数据，并将其设置进ModelAndView对象
	protected void beforeShowList(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
	}

	//调用newCommandObject()方法实例化参数对象，并填充值
	protected V param2Object(ServletRequest request) throws WebException {
		V command = null;
		try {
			command = (V) newCommandObject(getVoClass());
		} catch (Exception e) {
			logger.error("实例化command类[" + (command == null ? "null" : command.getClass().getName()) + "]时出错",
					e);
			throw new WebException("实例化command[" + (command == null ? "null" : command.getClass().getName())
					+ "]时出错", e);
		}

		param2Object(request, command);
		return command;
	}

	public Object param2ObjectByObj(ServletRequest request,Object obj) throws WebException {
		param2Object(request, obj);
		return obj;
	}
	
	/**
	 * 根据设置的validator，进行数据校验。如果校验失败，将出错信息显示给客户端。
	 * 
	 * @param request
	 * @param obj
	 * @return 数据校验是否成功
	 */
	public boolean validate(HttpServletRequest request, Object obj) {
		BindingResult errors = new BeanPropertyBindingResult(obj, DEFAULT_COMMAND_NAME);
		Validator[] validators = getValidators();
		//校验
		if (validators != null) {
			for (Validator validator : validators) {
				if (validator.supports(obj.getClass())) {
					ValidationUtils.invokeValidator(validator, obj, errors);
				}
			}
		}

		//如果有错误信息，提示给客户端
		if (errors.hasErrors()) {
			StringBuffer msg = new StringBuffer("数据校验失败:\n ");
			for (Object error : errors.getAllErrors()) {
				msg.append(
						getApplicationContext().getMessage((MessageSourceResolvable) error,
								Locale.getDefault())).append("\n ");
			}
			showMessage(request, msg.toString());
		}

		return !errors.hasErrors();
	}

	/** 以下为简单的get,set方法 */
	protected Class getVoClass() {
		return voClass;
	}

	public String getAddView() {
		return addView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public String getEditView() {
		return editView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public String getShowView() {
		return showView;
	}

	public void setShowView(String showView) {
		this.showView = showView;
	}
	
	public BizFile doSingleFileUpload(HttpServletRequest request,
			String fileFieldName ,String dist) throws Exception {
		MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile obj = (CommonsMultipartFile) multipartrequest
				.getFile(fileFieldName);
		if(obj==null||(obj!=null&&obj.getFileItem().getName().trim().equals(""))){
			return null;
		}
		
		BizFile objFile = null;
		String originFullName = obj.getFileItem().getName();
		String name = extractFileName(originFullName);
		String postfix = extractPostfix(originFullName);
		String objName = (new Date()).getTime() + "" + postfix;
		File filePath = new File(request.getSession().getServletContext()
				.getRealPath(dist));
		if(!filePath.exists()){
			filePath.mkdir();
		}
		
		File file = new File(filePath+File.separator + objName);
		
		byte[] bytes = obj.getBytes();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		objFile = new BizFile(originFullName, name, postfix, objName);
		return objFile;
	}	
	
	/**
	 * 测试是否连接
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView testCont(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.getWriter().write("<script language='JavaScript'  type='text/javascript'  >document.write('ok')</script>");
		
		return null;
	}
	
	public BizFile doSingleFileUpload(HttpServletRequest request,
			String fileFieldName) throws Exception {
		MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile obj = (CommonsMultipartFile) multipartrequest
				.getFile(fileFieldName);
		if(obj==null||(obj!=null&&obj.getFileItem().getName().trim().equals(""))){
			return null;
		}
		
		BizFile objFile = null;
		String originFullName = obj.getFileItem().getName();
		String name = extractFileName(originFullName);
		String postfix = extractPostfix(originFullName);
		String objName = (new Date()).getTime() + "" + postfix;
		File filePath = new File(request.getSession().getServletContext()
				.getRealPath("\\"));
		if(!filePath.exists()){
			filePath.mkdir();
		}
		
		File file = new File(filePath+File.separator + objName);
		
		byte[] bytes = obj.getBytes();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		objFile = new BizFile(originFullName, name, postfix, objName);
		return objFile;
	}	
	
	public String extractPostfix(String name) {
		int index = name.lastIndexOf(".");
		String ret = name.substring(index, name.length());
		return ret;
	}

	public String extractFileName(String name) {
		int index = name.lastIndexOf("\\");
		String ret = name.substring(index+1, name.length());
		return ret;
	}	
	
	//判断登陆者是否为管理员
	public  String  panduan(ModelAndView mnv){
		String str = "0";
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = (adapter == null ? null : adapter.getRealUser());
		if(user!=null){
			List<Role> list = user.getRoles();
			for(int i=0;i<list.size();i++){
				Role role = list.get(i);
				if("系统超级管理员".equals(role.getName())){
				str = "1";
				}
			
			}
		}
		String userName = user.getUserName();
		mnv.addObject("Mobtel", userName);
		mnv.addObject("pandun", str);
		return userName;
	}

}
